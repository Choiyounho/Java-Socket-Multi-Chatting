package chat.multi.client;

import chat.domain.Stream;
import chat.view.ChatView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSender extends Thread {

    public static final String EXIT_MESSAGE = "quit";
    private static final String MESSAGE = "[%s]%s";

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
            sendName();
            sendMessage();
            closeStream();
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
    }

    private void sendName() throws IOException {
        if (dataOutputStream != null) {
            dataOutputStream.writeUTF(name);
        }
    }

    private void sendMessage() throws IOException {
        while (dataOutputStream != null) {
            String message = ChatView.inputChatting();
            if (message.equalsIgnoreCase(EXIT_MESSAGE)) {
                break;
            }
            dataOutputStream.writeUTF(String.format(MESSAGE, name, message));
        }
    }

    private void closeStream() throws IOException {
        if (dataOutputStream != null) {
            dataOutputStream.close();
            socket.close();
        }
    }

}


