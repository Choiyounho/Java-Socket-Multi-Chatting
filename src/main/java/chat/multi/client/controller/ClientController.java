package chat.multi.client.controller;

import chat.multi.client.ClientReceiver;
import chat.multi.client.ClientSender;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import static utils.CommonsConstant.LOCAL_HOST;
import static utils.CommonsConstant.PORT;

public class ClientController {

    private static final String MY_NAME = "이름 설정 : ";

    public void run() {
        try {
            Socket socket = new Socket(LOCAL_HOST, PORT);
            Scanner scanner = new Scanner(System.in);
            System.out.print(MY_NAME);
            String name = scanner.nextLine();

            Thread sender = new Thread(new ClientSender(socket, name));
            Thread receiver = new Thread(new ClientReceiver(socket));

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