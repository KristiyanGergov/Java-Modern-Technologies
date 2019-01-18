package bg.sofia.uni.fmi.mjt.chat.client;

import bg.sofia.uni.fmi.mjt.chat.IO.InputHandler;

public class StartClient {

    public static void main(String[] args) {
        new InputHandler().processClientCommand();
    }

}