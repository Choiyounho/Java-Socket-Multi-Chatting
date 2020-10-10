package chat.multi.client;

import chat.domain.Stream;
import chat.view.ChatView;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientReceiver extends Thread {

    private Socket socket;
    private DataInputStream dataInputStream;

    public ClientReceiver(Socket socket) throws IOException {
        this.socket = socket;
        dataInputStream = Stream.newInstance4DataInputStream(socket);
    }

    @Override
    public void run() {
        while (dataInputStream != null) {
            try {
                System.out.println(ChatView.getMessage(dataInputStream));
            } catch (Exception e) {
                System.out.println("Exception e " + e.getMessage());
                break;
            }
        }
        try {
            dataInputStream.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
    }

}


