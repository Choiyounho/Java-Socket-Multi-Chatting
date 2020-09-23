package multichat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class MultiChatServer {

    HashMap clients;

    MultiChatServer() {
        clients = new HashMap();
        Collections.synchronizedMap(clients);
    }

    public void start() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("start server...");
            while (true) {
                socket = serverSocket.accept(); // 클라 정보를 알고 있는 소켓
                System.out.println(socket.getInetAddress() + ":" + socket.getPort() + "connect!"); // 접속한 클라의 아이피주소와 소켓포트 번호
                ServerReceiver thread = new ServerReceiver(socket);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("IOException e " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
    }

    void sendToAll(String message) { //브로드 캐스팅 기능

        Iterator iterator = clients.keySet().iterator();

        while (iterator.hasNext()) {
            try {
                DataOutputStream dataOutputStream = (DataOutputStream) clients.get(iterator.next());
                dataOutputStream.writeUTF(message);
            } catch (IOException e) {
                System.out.println("IOException e " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Exception e " + e.getMessage());
            }
        }
    }

    class ServerReceiver extends Thread {

        Socket socket;
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;

        ServerReceiver(Socket socket) {
            this.socket = socket;
            try {
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                System.out.println("IOException e " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Exception e " + e.getMessage());
            }
        }

        public void run() {
            String name = "";
            try {
                name = dataInputStream.readUTF();
                if (clients.get(name) != null) { // 같은 이름 사용자 존재
                    dataOutputStream.writeUTF("#Aleady exist name : " + name);
                    dataOutputStream.writeUTF("#Please reconnext by other name");
                    System.out.println(socket.getInetAddress() + ":" + socket.getPort() + "disconnect!");
                    dataInputStream.close();
                    dataOutputStream.close();
                    socket.close();
                } else {
                    sendToAll("#" + name + " join!");
                    clients.put(name, dataOutputStream);
                    while (dataInputStream != null) {
                        sendToAll(dataInputStream.readUTF());
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException e " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Exception e " + e.getMessage());
            } finally {
                if (socket != null) {
                    sendToAll("#" + name + " exit!");
                    clients.remove(name);
                    System.out.println(socket.getInetAddress() + ":" + socket.getPort() + "disconnect!");
                }
            }
        }
    }


}





