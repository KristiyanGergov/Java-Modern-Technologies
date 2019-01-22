package bg.sofia.uni.fmi.mjt.chat.client;

import bg.sofia.uni.fmi.mjt.chat.IO.InputHandler;
import bg.sofia.uni.fmi.mjt.chat.models.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static bg.sofia.uni.fmi.mjt.chat.constants.SystemOutConstants.SOCKET_CLOSET;

public class ClientConnectionRunnable implements Runnable {

    private User user;
    private Socket socket;

    public ClientConnectionRunnable(User user, Socket socket) {
        this.user = user;
        this.socket = socket;
    }


    @Override
    public void run() {
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            while (!socket.isClosed()) {
                String commandInput = reader.readLine();

                if (commandInput != null) {
                    new InputHandler().processServerCommand(commandInput, writer, user);
                }

            }

        } catch (IOException e) {
            System.out.println(SOCKET_CLOSET);
            System.out.println(e.getMessage());
        }
    }

}