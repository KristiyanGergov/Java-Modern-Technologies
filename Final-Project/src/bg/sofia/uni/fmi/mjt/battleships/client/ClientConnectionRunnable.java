package bg.sofia.uni.fmi.mjt.battleships.client;

import bg.sofia.uni.fmi.mjt.battleships.IO.InputHandler;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongCoordinatesException;
import bg.sofia.uni.fmi.mjt.battleships.models.Player;
import bg.sofia.uni.fmi.mjt.battleships.server.GameServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static bg.sofia.uni.fmi.mjt.battleships.constants.SystemOutConstants.SOCKET_CLOSET;

public class ClientConnectionRunnable implements Runnable {

    private GameServer server;
    private Player player;

    public ClientConnectionRunnable(GameServer server, Player player) {
        this.server = server;
        this.player = player;
    }


    @Override
    public void run() {

        Socket socket = server.getSocket();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

                while (!socket.isClosed()) {
                    String commandInput = reader.readLine();

                    if (commandInput != null) {
                        try {
                            new InputHandler().processServerCommand(commandInput, writer, server, player);
                        } catch (InvalidCommandException | WrongCoordinatesException e) {
                            writer.println(e.getMessage());
                        }
                    }

                }
            }
        } catch (IOException e) {
            System.out.println(SOCKET_CLOSET);
            System.out.println(e.getMessage());
        }
    }

}