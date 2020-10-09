package chat.multi.server;

import chat.domain.IOApi;
import chat.view.ChatView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static utils.CommonsConstant.PORT;

public class MultiChatServer{

    public Map<String, DataOutputStream> clients;


    public MultiChatServer() {
        clients = new HashMap();
        Collections.synchronizedMap(clients);
    }


    public void start() {
        ServerSocket serverSocket;
        Socket socket;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("start server...");
            while (true) {
                socket = serverSocket.accept(); // 클라 정보를 알고 있는 소켓
                ChatView.isClientInfo(socket); // 접속한 클라의 아이피주소와 소켓포트 번호
                ServerReceiver thread = new ServerReceiver(socket);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("IOException e " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
    }


    public void sendToAll(String message) { //브로드 캐스팅 기능

        Iterator iterator = clients.keySet().iterator();

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

    class ServerReceiver extends Thread {

        private Socket socket;
        private DataInputStream dataInputStream;
        private DataOutputStream dataOutputStream;

        public ServerReceiver(Socket socket) {
            this.socket = socket;
            try {
                dataInputStream = IOApi.newInstance4DataInputStream(socket);
                dataOutputStream = IOApi.newInstance4DataOutputStream(socket);
            } catch (IOException e) {
                System.out.println("IOException e " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Exception e " + e.getMessage());
            }
        }

        public void run() {
            String name = "";
            try {
                name = ChatView.getMessage(dataInputStream);
                if (clients.get(name) != null) { // 같은 이름 사용자 존재
                    ChatView.sendMessage("#Aleady exist name : " + name, dataOutputStream);
                    ChatView.sendMessage("#Please reconnect by other name", dataOutputStream);
                    ChatView.isClientInfo(socket);
                    IOApi.closeIOStream(socket, dataInputStream, dataOutputStream);
                    socket = null;
                } else {
                    sendToAll("[" + name + "]님께서 채팅방에 입장했습니다!!");
                    clients.put(name, dataOutputStream);
                    while (dataInputStream != null) {
                        sendToAll(ChatView.getMessage(dataInputStream));
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException e " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Exception e " + e.getMessage());
            } finally {
                if (socket != null) {
                    sendToAll("[" + name + "]님이 채팅방에서 나갔습니다.");
                    clients.remove(name);
                    System.out.println(socket.getInetAddress() + ":" + socket.getPort() + "disconnect!");
                }
            }
        }
    }

}