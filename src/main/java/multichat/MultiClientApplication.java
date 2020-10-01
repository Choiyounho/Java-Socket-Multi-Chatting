package multichat;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MultiClientApplication {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 9999);
            Scanner scanner = new Scanner(System.in);
            System.out.print("name:");
            String name = scanner.nextLine();
            Thread sender = new Thread(new MultiChatClient.ClientSender(socket, name));
            Thread receiver = new Thread(new MultiChatClient.ClientReceiver(socket));
            sender.start();
            receiver.start();
        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException e " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException e " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
    }

}
