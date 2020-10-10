package chat.multi.client;

import chat.domain.Stream;
import chat.view.ChatView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSender extends Thread {

    private Socket socket;
    private DataOutputStream dataOutputStream;
    private String name;

    public ClientSender(Socket socket, String name) throws IOException {
        this.socket = socket;
        this.name = name;
        dataOutputStream = Stream.newInstance4DataOutputStream(socket);
    }

    public void run() {
        try {
            if (dataOutputStream != null) {
                dataOutputStream.writeUTF(name);
            }
            while (dataOutputStream != null) {
                String message = ChatView.inputChatting();
                if (message.equals("quit")) {
                    break;
                }
                dataOutputStream.writeUTF("[" + name + "]" + message);
            }
            if (dataOutputStream != null) {
                dataOutputStream.close();
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
    }

}


