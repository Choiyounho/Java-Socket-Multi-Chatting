package chat.multi.client;

import chat.multi.client.controller.ClientController;

public class MultiClientApplication {

    public static void main(String[] args) {
        new ClientController().run();
    }

}
