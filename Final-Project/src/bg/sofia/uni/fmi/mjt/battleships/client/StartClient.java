package bg.sofia.uni.fmi.mjt.battleships.client;

import bg.sofia.uni.fmi.mjt.battleships.IO.InputHandler;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.NotConnectedException;

import static bg.sofia.uni.fmi.mjt.battleships.constants.ExceptionConstants.NOT_CONNECTED_TO_SERVER;

public class StartClient implements Runnable {

    public static void main(String[] args) {
        new StartClient().run();
    }

    @Override
    public void run() {
        try {
            new InputHandler().processClientCommand();
        } catch (NotConnectedException e) {
            System.out.println(NOT_CONNECTED_TO_SERVER);
        }
    }
}
