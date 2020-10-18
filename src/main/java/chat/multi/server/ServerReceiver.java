package chat.multi.server;

import chat.domain.Stream;
import chat.domain.User;
import chat.view.ChatView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class ServerReceiver extends Thread {

    public static final String PRINT_JOINED_USER = "[%s]님께서 채팅방에 입장했습니다!!";
    public static final String PRINT_OUT_USER = "[%s]님께서 채팅방에서 나갔습니다!!";

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Map<String, DataOutputStream> clients;

    private ServerReceiver(Socket socket, Map<String, DataOutputStream> clients) throws IOException {
        this.socket = socket;
        dataInputStream = Stream.newInstance4DataInputStream(socket);
        dataOutputStream = Stream.newInstance4DataOutputStream(socket);
        this.clients = clients;
    }

    private ServerReceiver() {
        this.clients = new HashMap<>();
        Collections.synchronizedMap(clients);
    }

    public static ServerReceiver initClients() {
        return new ServerReceiver();
    }

    public static ServerReceiver initThread(Socket socket, Map<String, DataOutputStream> clients) throws IOException {
        return new ServerReceiver(socket, clients);
    }

    public Map<String, DataOutputStream> getClients() {
        return clients;
    }

    public void run() {
        User user = null;
        try {
            user = new User(ChatView.getMessage(dataInputStream));
            noticeExistUser(user.getName());
            validateName(user.getName(), this.clients);
            sendChatting();
        } catch (IOException e) {
            System.out.println("IOException e " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        } finally {
            goOutToChatting(user.getName());
        }
    }

    private void goOutToChatting(String name) {
        if (socket != null) {
            sendToAll(String.format(PRINT_OUT_USER, name));
            clients.remove(name);
        }
    }

    private void sendChatting() throws IOException {
        while (dataInputStream != null) {
            sendToAll(ChatView.getMessage(dataInputStream));
        }
    }

    private void noticeExistUser(String name) throws IOException {
        if (clients.containsKey(name)) { // 같은 이름 사용자 존재
            ChatView.sendMessage("#Aleady exist name : " + name, dataOutputStream);
            ChatView.sendMessage("#Please reconnect by other name", dataOutputStream);
            Stream.closeIOStream(socket, dataInputStream, dataOutputStream);
            socket = null;
        }
    }

    private Map<String, DataOutputStream> validateName(String name, Map<String, DataOutputStream> clients) {
        if (!clients.containsKey(name)) {
            clients.put(name, dataOutputStream);
            sendToAll(String.format(PRINT_JOINED_USER, name));
            return clients;
        }
        throw new IllegalArgumentException(String.format("#Aleady exist %s", name));
    }

    public void sendToAll(String message) {
        Iterator<String> iterator = clients.keySet().iterator();
        while (iterator.hasNext()) {
            try {
                DataOutputStream dataOutputStream = clients.get(iterator.next());
                dataOutputStream.writeUTF(message);
            } catch (IOException e) {
                System.out.println("IOException e " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Exception e " + e.getMessage());
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerReceiver that = (ServerReceiver) o;
        return Objects.equals(clients, that.clients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clients);
    }

}


