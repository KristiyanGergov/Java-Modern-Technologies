package bg.sofia.uni.fmi.mjt.battleships.client;

import bg.sofia.uni.fmi.mjt.battleships.IO.InputHandler;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.NotConnectedException;

import static bg.sofia.uni.fmi.mjt.battleships.constants.ExceptionConstants.NOT_CONNECTED_TO_SERVER;
import static bg.sofia.uni.fmi.mjt.battleships.constants.SystemOutConstants.CONNECT_COMMANDS;

public class StartClient implements Runnable {

    public static void main(String[] args) {
        new StartClient().run();
    }

    @Override
    public void run() {

        while (true) {
            try {
                System.out.println(CONNECT_COMMANDS);
                new InputHandler().processClientCommand();
            } catch (NotConnectedException e) {
                System.out.println(NOT_CONNECTED_TO_SERVER);
            }
        }
    }
}
