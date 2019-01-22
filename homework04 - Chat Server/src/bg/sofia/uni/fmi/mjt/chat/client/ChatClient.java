package bg.sofia.uni.fmi.mjt.chat.client;

import bg.sofia.uni.fmi.mjt.chat.IO.InputHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import static bg.sofia.uni.fmi.mjt.chat.constants.SystemOutConstants.*;

public class ChatClient {

    public static boolean connect(String host, int port, String username) {
        try {
            Socket socket = new Socket(host, port);
            InputHandler.writer = new PrintWriter(socket.getOutputStream(), true);

            System.out.printf(SUCCESSFULLY_CONNECTED, host, port, username);
            InputHandler.writer.println(username);

            ClientRunnable clientRunnable = new ClientRunnable(socket);
            new Thread(clientRunnable).start();
            return true;
        } catch (IOException e) {
            System.out.printf(CANNOT_CONNECT_TO_SERVER, host, port);
            System.out.println(e.getMessage());
            return false;
        }
    }

}