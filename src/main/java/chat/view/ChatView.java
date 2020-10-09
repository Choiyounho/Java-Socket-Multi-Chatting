package chat.view;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.Socket;

public class ChatView {

    public static void sendMessage(String s, DataOutput dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(s);
    }

    public static String getMessage(DataInput dataInputStream) throws IOException {
        return dataInputStream.readUTF();
    }

    public static void isClientInfo(Socket socket) {
        System.out.println(socket.getInetAddress() + ":" + socket.getPort() + "connect!");
    }
    
}
