package chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("chat.Server ready ...");

        } catch (IOException e) {
            System.out.println("IOException e " + e.getMessage());
        }

        while (true) {
            try {
                try {
                    Socket socket = serverSocket.accept(); // 클라이언트 정보를 알고 있는 소켓
                    System.out.println("client connect success!");

                    InputStream inputStream = socket.getInputStream();
                    DataInputStream dataInputStream = new DataInputStream(inputStream);
                    String message = dataInputStream.readUTF();

                    OutputStream outputStream = socket.getOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                    dataOutputStream.writeUTF("[ECHO]" + message + "(from chat.Server!)");

                    dataOutputStream.close();
                    dataInputStream.close();
                    socket.close();
                    System.out.println("client Socket close...");

                } catch (IOException e) {
                    System.out.println("IOException e " + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Exception e " + e.getMessage());

            }
        }
    }

}
