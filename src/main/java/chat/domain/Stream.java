package chat.domain;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Stream {

    public static DataOutputStream newInstance4DataOutputStream(Socket socket) throws IOException {
        return new DataOutputStream(socket.getOutputStream());
    }

    public static DataInputStream newInstance4DataInputStream(Socket socket) throws IOException {
        return new DataInputStream(socket.getInputStream());
    }

    public static void closeIOStream(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.close();
        dataInputStream.close();
        socket.close();
    }

}
