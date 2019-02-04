package bg.sofia.uni.fmi.mjt.battleships.server;

import bg.sofia.uni.fmi.mjt.battleships.client.ClientConnectionRunnable;
import bg.sofia.uni.fmi.mjt.battleships.constants.SystemOutConstants;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.UnableToJoinGameException;
import bg.sofia.uni.fmi.mjt.battleships.models.Game;
import bg.sofia.uni.fmi.mjt.battleships.models.Player;
import bg.sofia.uni.fmi.mjt.battleships.util.ThreadExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static bg.sofia.uni.fmi.mjt.battleships.constants.ExceptionConstants.FULL_GAME;
import static bg.sofia.uni.fmi.mjt.battleships.constants.ServerConstants.PORT;
import static bg.sofia.uni.fmi.mjt.battleships.constants.SystemOutConstants.PLAYER_CONNECTED;
import static bg.sofia.uni.fmi.mjt.battleships.constants.SystemOutConstants.PORT_8080_TAKEN;

public class GameServer implements Runnable {

    private Map<String, Game> games;
    private Player currentPlayer;

    public void addGame(String gameName, Game game) {
        games.put(gameName, game);
    }

    public synchronized void removeGame(String gameName) {
        games.remove(gameName);
    }

    public Map<String, Game> getGames() {
        return this.games;
    }

    public Game getGame(String gameName) {
        return games.get(gameName);
    }

    public synchronized void joinGame(String game, Player player) throws UnableToJoinGameException {
        if (!games.get(game).join(player))
            throw new UnableToJoinGameException(FULL_GAME);
    }

    public synchronized Player getLastJoinedPlayer() {
        return currentPlayer;
    }

    public synchronized void setPlayer(Player player) {
        currentPlayer = player;
    }

    public synchronized Socket getSocket() {
        return currentPlayer.getSocket();
    }

    private void processInput(ServerSocket serverSocket) throws IOException {

        while (true) {

            Socket socket = serverSocket.accept();
            System.out.printf(SystemOutConstants.CLIENT_CONNECTED_TO_SERVER, socket.getInetAddress());

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String playerName = reader.readLine();
            Player player = new Player(playerName, socket);
            setPlayer(player);
            System.out.printf(PLAYER_CONNECTED, playerName);

            ClientConnectionRunnable clientRunnable = new ClientConnectionRunnable(this, player);
            ThreadExecutor.execute(clientRunnable);

        }
    }

    private ServerSocket startServer() {

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.printf(SystemOutConstants.SERVER_RUNNING, PORT);
            return serverSocket;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void run() {
        games = new HashMap<>();

        try (ServerSocket serverSocket = startServer()) {

            if (serverSocket != null)
                processInput(serverSocket);
            else
                ThreadExecutor.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(PORT_8080_TAKEN);
        }
    }

    public static void main(String[] args) {
        new GameServer().run();
    }
}