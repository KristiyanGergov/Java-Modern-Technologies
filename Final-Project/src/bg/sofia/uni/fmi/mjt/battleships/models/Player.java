package bg.sofia.uni.fmi.mjt.battleships.models;

import bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongCoordinatesException;
import bg.sofia.uni.fmi.mjt.battleships.util.BoardCreator;
import bg.sofia.uni.fmi.mjt.battleships.util.Gun;
import bg.sofia.uni.fmi.mjt.battleships.util.ShipBuilder;

import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;

import static bg.sofia.uni.fmi.mjt.battleships.constants.SystemOutConstants.SHIPS_LEFT;

public class Player implements Serializable {

    private transient Socket socket;
    private String username;
    private BoardCreator boardCreator;
    private Gun gun;
    private ShipBuilder shipBuilder;
    private transient Game game;
    private boolean onTurn;


    public Player(String username, Socket socket) {
        this.username = username;
        this.socket = socket;
        this.boardCreator = new BoardCreator(BoardConstants.ROWS, BoardConstants.COLUMNS);
        this.shipBuilder = new ShipBuilder(boardCreator.getBoard());
    }

    public boolean isOnTurn() {
        return onTurn;
    }

    void setOnTurn(boolean onTurn) {
        this.onTurn = onTurn;
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

    public boolean buildShip(char startRow, char endRow, int startCol, int endCol) throws WrongCoordinatesException, InvalidCommandException {
        if (hasBuildAllShips())
            return false;

        shipBuilder.buildShip(new Ship(startRow, endRow, startCol, endCol));
        return true;
    }

    public void printNumberOfLeftShips(PrintWriter writer) {
        writer.println(String.format(SHIPS_LEFT,
                shipBuilder.getShipsWith5CellsLeft(),
                shipBuilder.getShipsWith4CellsLeft(),
                shipBuilder.getShipsWith3CellsLeft(),
                shipBuilder.getShipsWith2CellsLeft()));

    }

    public boolean hasBuildAllShips() {
        return shipBuilder.getShipsWith2CellsLeft() == 0 &&
                shipBuilder.getShipsWith3CellsLeft() == 0 &&
                shipBuilder.getShipsWith4CellsLeft() == 0 &&
                shipBuilder.getShipsWith5CellsLeft() == 0;
    }

    void setGun(Gun gun) {
        this.gun = gun;
    }

    public Ship makeShot(String coordinates) throws InvalidCommandException, WrongCoordinatesException {

        if (!onTurn)
            throw new InvalidCommandException("It's your opponent turn!");

        Ship ship = gun.hitShip(new Hit(coordinates.toUpperCase()));
        game.switchTurns();
        return ship;
    }

    public void reset() {
        this.boardCreator = new BoardCreator(BoardConstants.ROWS, BoardConstants.COLUMNS);
        this.shipBuilder = new ShipBuilder(boardCreator.getBoard());
        this.onTurn = false;
        this.game = null;    }

}
