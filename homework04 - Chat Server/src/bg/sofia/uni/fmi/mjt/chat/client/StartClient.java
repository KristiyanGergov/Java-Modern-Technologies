package bg.sofia.uni.fmi.mjt.chat.client;

import bg.sofia.uni.fmi.mjt.chat.IO.InputHandler;
import bg.sofia.uni.fmi.mjt.chat.exceptions.NotConnectedException;

public class StartClient implements Runnable {

    public static void main(String[] args) {
        new StartClient().run();
    }

    @Override
    public void run() {
        try {
            new InputHandler().processClientCommand();
        } catch (NotConnectedException e) {
            e.printStackTrace();
        }
    }
}