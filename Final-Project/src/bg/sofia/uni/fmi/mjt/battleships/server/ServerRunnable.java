package bg.sofia.uni.fmi.mjt.battleships.server;

import bg.sofia.uni.fmi.mjt.battleships.models.Player;

import java.io.IOException;

import static bg.sofia.uni.fmi.mjt.battleships.constants.SystemOutConstants.PLAYER_CONNECTED;

public class ServerRunnable implements Runnable {

    private final GameServer server;

    public ServerRunnable(GameServer server) {
        this.server = server;
    }

    @Override
    public void run() {

        try {
            String playerName = server.getReader().readLine();

            Player player = new Player(playerName, server.getSocket());
            server.setPlayer(player);
            System.out.printf(PLAYER_CONNECTED, playerName);
        } catch (IOException e) {
            e.printStackTrace();
            //todo
        }

    }
}
