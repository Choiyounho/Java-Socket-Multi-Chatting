package multichat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class MultiChatClient {

    static class ClientSender extends Thread {

        Socket socket;
        DataOutputStream dataOutputStream;
        String name;

        ClientSender(Socket socket, String name) {
            this.socket = socket;
            this.name = name;

            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                System.out.println("IOException e " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Exception e " + e.getMessage());
            }
        }

        public void run() {
            Scanner scanner = new Scanner(System.in);
            try {
                if (dataOutputStream != null) dataOutputStream.writeUTF(name);
                while (dataOutputStream != null) {
                    String message = scanner.nextLine();
                    if (message.equals("quit")) break;
                    dataOutputStream.writeUTF("[" + name + "]" + message);
                }
                dataOutputStream.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class ClientReceiver extends Thread {

        Socket socket;
        DataInputStream dataInputStream;

        ClientReceiver(Socket socket) {
            this.socket = socket;
            try {
                dataInputStream = new DataInputStream(socket.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            while (dataInputStream != null) {
                try {
                    System.out.println(dataInputStream.readUTF());
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
    }
}
