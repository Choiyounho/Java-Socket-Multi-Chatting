package chat.multi.client;

import utils.StreamUtils;
import chat.view.ChatView;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientReceiver extends Thread {

    private final Socket socket;
    private DataInputStream dataInputStream;

    public ClientReceiver(Socket socket) throws IOException {
        this.socket = socket;
        dataInputStream = StreamUtils.newInstance4DataInputStream(socket);
    }

    @Override
    public void run() {
        while (dataInputStream != null) {
            try {
                ChatView.printMessage(dataInputStream);
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


