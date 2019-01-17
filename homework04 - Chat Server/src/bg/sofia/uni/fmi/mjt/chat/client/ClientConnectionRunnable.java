package bg.sofia.uni.fmi.mjt.chat.client;

import bg.sofia.uni.fmi.mjt.chat.IO.InputHandler;
import bg.sofia.uni.fmi.mjt.chat.models.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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

            while (true) {
                String commandInput = reader.readLine();

                if (commandInput != null) {
                    InputHandler.processCommand(commandInput, writer, user);
                }

            }
        } catch (IOException e) {
            System.out.println("socket is closed");
            System.out.println(e.getMessage());
        }
    }

}