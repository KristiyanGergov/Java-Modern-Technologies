package bg.sofia.uni.fmi.mjt.battleships.client;

import bg.sofia.uni.fmi.mjt.battleships.IO.InputHandler;
import bg.sofia.uni.fmi.mjt.battleships.util.InputValidator;
import bg.sofia.uni.fmi.mjt.battleships.util.ThreadExecutor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import static bg.sofia.uni.fmi.mjt.battleships.constants.ServerConstants.HOST;
import static bg.sofia.uni.fmi.mjt.battleships.constants.ServerConstants.PORT;
import static bg.sofia.uni.fmi.mjt.battleships.constants.SystemOutConstants.CANNOT_CONNECT_TO_SERVER;
import static bg.sofia.uni.fmi.mjt.battleships.constants.SystemOutConstants.SUCCESSFULLY_CONNECTED;

public class GameClient {

    public static boolean connect(String username) {

        try {

            Socket socket = new Socket(HOST, PORT);
            InputHandler.setOutputHandler(new PrintWriter(socket.getOutputStream(), true));
            InputHandler.print(username);

            System.out.printf(SUCCESSFULLY_CONNECTED, username);

            ClientRunnable clientRunnable = new ClientRunnable(socket);
            ThreadExecutor.execute(clientRunnable);

            return true;

        } catch (IOException e) {
            System.out.printf(CANNOT_CONNECT_TO_SERVER, HOST, PORT);
            System.out.println(e.getMessage());
            return false;
        }

    }

}
