package bg.sofia.uni.fmi.mjt.battleships.client;

import java.io.IOException;
import java.net.Socket;

import static bg.sofia.uni.fmi.mjt.battleships.constants.SystemOutConstants.CANNOT_CONNECT_TO_SERVER;
import static bg.sofia.uni.fmi.mjt.battleships.constants.SystemOutConstants.SUCCESSFULLY_CONNECTED;

public class GameCilent {

    public static boolean connect(String host, int port, String username) {

        try {
            Socket socket = new Socket(host, port);

            System.out.printf(SUCCESSFULLY_CONNECTED, host, port, username);



            return true;
        } catch (IOException e) {
            System.out.printf(CANNOT_CONNECT_TO_SERVER, host, port);
            System.out.println(e.getMessage());
            return false;
        }

    }

}
