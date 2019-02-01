package bg.sofia.uni.fmi.mjt.battleships.models;

import bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants;
import bg.sofia.uni.fmi.mjt.battleships.util.BoardCreator;
import bg.sofia.uni.fmi.mjt.battleships.util.Gun;
import bg.sofia.uni.fmi.mjt.battleships.util.ShipBuilder;

import java.net.Socket;

public class Player {

    private Socket socket;
    private String username;
    private BoardCreator boardCreator;
    private Gun gun;
    private ShipBuilder shipBuilder;
    private Game game;

    public Player(String username, Socket socket) {
        this.username = username;
        this.socket = socket;
        this.boardCreator = new BoardCreator(BoardConstants.ROWS, BoardConstants.COLUMNS);
        this.shipBuilder = new ShipBuilder(boardCreator.getBoard());
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public char[][] getBoard() {
        return boardCreator.getBoard();
    }

    public Socket getSocket() {
        return socket;
    }

    public String getUsername() {
        return username;
    }


}
