package chat.multi.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientSender extends Thread {

    private Socket socket;
    private DataOutputStream dataOutputStream;
    private String name;

    public ClientSender(Socket socket, String name) throws IOException {
        this.socket = socket;
        this.name = name;
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }


    public void run() {
        Scanner scanner = new Scanner(System.in);
        try {
            if (dataOutputStream != null) {
                dataOutputStream.writeUTF(name);
            }

            while (dataOutputStream != null) {
                String message = scanner.nextLine();
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


