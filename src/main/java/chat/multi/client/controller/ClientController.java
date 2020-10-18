package chat.multi.client.controller;

import chat.domain.Message;
import chat.domain.User;
import chat.multi.client.ClientReceiver;
import chat.multi.client.ClientSender;
import chat.view.ChatView;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import static utils.CommonsConstant.LOCAL_HOST;
import static utils.CommonsConstant.PORT;

public class ClientController {

    public void run() {
        try {
            Socket socket = new Socket(LOCAL_HOST, PORT);
            User user = new User(ChatView.inputName());

            Thread sender = new Thread(new ClientSender(socket, user));
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