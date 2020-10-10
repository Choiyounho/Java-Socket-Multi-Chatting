package chat.multi.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static utils.CommonsConstant.PORT;

public class MultiChatServer{

    private Map<String, DataOutputStream> clients;

    public MultiChatServer() {
        this.clients = new HashMap<>();
        Collections.synchronizedMap(clients);
    }

    public void start() {
        ServerSocket serverSocket;
        Socket socket;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("start server...");
            while (true) {
                socket = serverSocket.accept(); // 클라 정보를 알고 있는 소켓
                ServerReceiver thread = new ServerReceiver(socket, clients);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("IOException e " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
    }

}