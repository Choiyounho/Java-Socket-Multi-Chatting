package chat.multi.server;

import chat.multi.server.controller.ServerController;

public class MultiServerApplication {

    public static void main(String[] args) {
        new ServerController().start();
    }

}
