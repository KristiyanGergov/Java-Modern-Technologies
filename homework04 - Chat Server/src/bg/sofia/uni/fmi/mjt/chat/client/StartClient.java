package bg.sofia.uni.fmi.mjt.chat.client;

import bg.sofia.uni.fmi.mjt.chat.IO.InputHandler;

public class StartClient implements Runnable {

    public static void main(String[] args) {
        new StartClient().run();
    }

    @Override
    public void run() {
        new InputHandler().processClientCommand();
    }
}