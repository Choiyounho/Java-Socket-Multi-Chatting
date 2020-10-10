package chat.single;

import chat.domain.Stream;
import chat.view.ChatView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static utils.CommonsConstant.LOCAL_HOST;
import static utils.CommonsConstant.PORT;

public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(LOCAL_HOST, PORT);
            System.out.println("Connection Success!");

            while (true) {
                String message = ChatView.inputChatting();

                DataOutputStream messageOutputStream = Stream.newInstance4DataOutputStream(socket);
                ChatView.sendMessage(message, messageOutputStream);

                DataInputStream messageInputStream = Stream.newInstance4DataInputStream(socket);

                System.out.println("Receive : " + ChatView.getMessage(messageInputStream));
//                ChatView.getMessage(messageInputStream);

                Stream.closeIOStream(socket, messageInputStream, messageOutputStream);
            }
        } catch (IOException e) {
            System.out.println("IOException e " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
    }

}

