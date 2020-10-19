package chat.multi.client;

import utils.StreamUtils;
import chat.domain.User;
import chat.view.ChatView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSender extends Thread {

    public static final String EXIT_MESSAGE = "quit";
    private static final String MESSAGE = "[%s]%s";

    private final Socket socket;
    private DataOutputStream dataOutputStream;
    private final User user;

    public ClientSender(Socket socket, User user) throws IOException {
        this.socket = socket;
        this.user = user;
        dataOutputStream = StreamUtils.newInstance4DataOutputStream(socket);
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
            dataOutputStream.writeUTF(user.getName());
        }
    }

    private void sendMessage() throws IOException {
        while (dataOutputStream != null) {
            String description = ChatView.inputChatting();
            if (description.equalsIgnoreCase(EXIT_MESSAGE)) {
                break;
            }
            dataOutputStream.writeUTF(String.format(MESSAGE, user.getName(), description));
        }
    }

    private void closeStream() throws IOException {
        if (dataOutputStream != null) {
            dataOutputStream.close();
            socket.close();
        }
    }

}


