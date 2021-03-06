package chat.view;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Scanner;

public class ChatView {

    public static final String PRINT_NAME = "이름 입력 : ";
    private static final Scanner scanner = new Scanner(System.in);

    private ChatView() {
    }

    public static String inputName() {
        System.out.print(PRINT_NAME);
        return scanner.nextLine();
    }

    public static String inputChatting() {
        return scanner.nextLine();
    }

    public static void sendMessage(String message, DataOutput dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(message);
    }

    public static String getMessage(DataInput dataInputStream) throws IOException {
        return dataInputStream.readUTF();
    }

    public static void printMessage(DataInputStream dataInputStream) throws IOException {
        System.out.println(dataInputStream.readUTF());
    }

}
