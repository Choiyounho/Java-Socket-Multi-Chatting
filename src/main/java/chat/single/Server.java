package chat.single;

import chat.domain.IOApi;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static utils.CommonsConstant.PORT;

public class Server {
    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("chat.Server ready ...");

        } catch (IOException e) {
            System.out.println("IOException e " + e.getMessage());
        }

        while (true) {
            try {
                try {
                    Socket socket = serverSocket.accept(); // 클라이언트 정보를 알고 있는 소켓
                    System.out.println("client connect success!");

                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                    String message = dataInputStream.readUTF();

                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeUTF("[ECHO]" + message + "(from chat.Server!)");

                    IOApi.closeIOStream(socket, dataInputStream, dataOutputStream);
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
