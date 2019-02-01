package bg.sofia.uni.fmi.mjt.battleships.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import static bg.sofia.uni.fmi.mjt.battleships.constants.SystemOutConstants.SOCKET_CLOSET;

public class ClientRunnable implements Runnable {

    private Socket socket;

    public ClientRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            while (true) {
                if (socket.isClosed()) {
                    System.out.println(SOCKET_CLOSET);
                    return;
                }

                System.out.println(reader.readLine());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}