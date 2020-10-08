package chat.multi.client;

import chat.domain.IOApi;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientReceiver extends Thread {

    private Socket socket;
    private DataInputStream dataInputStream;

    public ClientReceiver(Socket socket) {
        this.socket = socket;
        try {
            dataInputStream = IOApi.newInstance4DataInputStream(socket);
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage()
            );
        }
    }

    @Override
    public void run() {
        while (dataInputStream != null) {
            try {
                System.out.println(receiveMessage());
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        try {
            dataInputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String receiveMessage() throws IOException {
        return dataInputStream.readUTF();
    }

}


