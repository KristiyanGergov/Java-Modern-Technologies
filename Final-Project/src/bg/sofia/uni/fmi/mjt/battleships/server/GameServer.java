package bg.sofia.uni.fmi.mjt.battleships.server;

import bg.sofia.uni.fmi.mjt.battleships.constants.SystemOutConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {

    private final static int PORT = 8080;

    private void processInput(ServerSocket serverSocket) throws IOException {

        while (true) {

            Socket socket = serverSocket.accept();
            System.out.printf(SystemOutConstants.CLIENT_CONNECTED_TO_SERVER, socket.getInetAddress());

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));



        }
    }


    private void startServer() {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.printf(SystemOutConstants.SERVER_RUNNING, PORT);
            processInput(serverSocket);
        } catch (IOException e) {

        }

    }

}