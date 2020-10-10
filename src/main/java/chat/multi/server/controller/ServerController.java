package chat.multi.server.controller;

import chat.multi.server.ServerReceiver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static utils.CommonsConstant.PORT;

public class ServerController {

    public void start() {
        ServerReceiver clients = ServerReceiver.initClients();
        ServerSocket serverSocket;
        Socket socket;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("start server...");
            while (true) {
                socket = serverSocket.accept(); // 클라 정보를 알고 있는 소켓
                ServerReceiver thread = ServerReceiver.initThread(socket, clients.getClients());
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("IOException e " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
    }

}
