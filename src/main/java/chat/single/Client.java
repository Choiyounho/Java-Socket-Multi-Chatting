package chat.single;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static utils.CommonsConstant.LOCAL_HOST;
import static utils.CommonsConstant.PORT;

public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(LOCAL_HOST, PORT);
            System.out.println("Connection Success!");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                String message = scanner.nextLine();
                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream); // 한글이 깨지는 것을 방지
                dataOutputStream.writeUTF(message);
                InputStream inputStream = socket.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                System.out.println("Receive : " + dataInputStream.readUTF());

                dataInputStream.close();
                dataOutputStream.close();
                socket.close();
            }
            
        } catch (IOException e) {
            System.out.println("IOException e " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
    }

}

