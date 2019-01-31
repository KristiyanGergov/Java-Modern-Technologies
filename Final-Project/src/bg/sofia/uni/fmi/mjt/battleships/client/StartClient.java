package bg.sofia.uni.fmi.mjt.battleships.client;

import bg.sofia.uni.fmi.mjt.battleships.IO.InputHandler;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.NotConnectedException;

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
            //todo
        }
    }
}
