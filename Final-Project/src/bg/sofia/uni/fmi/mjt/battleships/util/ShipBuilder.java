package bg.sofia.uni.fmi.mjt.battleships.util;

import bg.sofia.uni.fmi.mjt.battleships.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongCoordinatesException;
import bg.sofia.uni.fmi.mjt.battleships.models.Ship;
import bg.sofia.uni.fmi.mjt.battleships.models.ShipCoordinates;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static bg.sofia.uni.fmi.mjt.battleships.constants.ShipConstants.*;

public class ShipBuilder implements Serializable {

    private char[][] board;

    private int shipsWith5CellsLeft = 1;
    private int shipsWith4CellsLeft = 2;
    private int shipsWith3CellsLeft = 3;
    private int shipsWith2CellsLeft = 4;

    private Map<Ship, Integer> ships;

    public ShipBuilder(char[][] board) {
        this.board = board;
        ships = new HashMap<>();
    }

    private boolean areMaximumShipsReached(ShipCoordinates coordinates) {

        switch (coordinates.getCellsNumber()) {

            case ONE_SHIP_CELLS:
                if (shipsWith5CellsLeft == 0)
                    return false;
                shipsWith5CellsLeft--;
                break;
            case TWO_SHIPS_CELLS:
                if (shipsWith4CellsLeft == 0)
                    return false;
                shipsWith4CellsLeft--;
                break;
            case THREE_SHIPS_CELLS:
                if (shipsWith3CellsLeft == 0)
                    return false;
                shipsWith3CellsLeft--;
                break;
            case FOUR_SHIPS_CELLS:
                if (shipsWith2CellsLeft == 0)
                    return false;
                shipsWith2CellsLeft--;
                break;
        }

        return true;

    }

    private boolean checkIfShipCanBeAdded(ShipCoordinates coordinates) throws WrongCoordinatesException {

        if (coordinates.getStartRow() == coordinates.getEndRow()) {
            for (int i = coordinates.getStartCol(); i <= coordinates.getEndCol(); i++) {
                if (board[coordinates.getStartRow()][i] == SHIP_FIELD)
                    throw new WrongCoordinatesException("There is already a ship at some of the provided coordinates!");
            }
        } else {
            for (int i = coordinates.getStartRow(); i < coordinates.getEndRow(); i++) {
                if (board[i][coordinates.getStartCol()] == SHIP_FIELD)
                    throw new WrongCoordinatesException("There is already a ship at some of the provided coordinates!");
            }
        }

        int shipCells = coordinates.getCellsNumber();

        switch (shipCells) {

            case ONE_SHIP_CELLS:
                return areMaximumShipsReached(coordinates);
            case TWO_SHIPS_CELLS:
                return areMaximumShipsReached(coordinates);
            case THREE_SHIPS_CELLS:
                return areMaximumShipsReached(coordinates);
            case FOUR_SHIPS_CELLS:
                return areMaximumShipsReached(coordinates);
        }


        return true;
    }

    public void buildShip(Ship ship) throws InvalidCommandException, WrongCoordinatesException {

        final ShipCoordinates coordinates = ship.getShipCoordinates();

        if (!checkIfShipCanBeAdded(coordinates))
            throw new InvalidCommandException(
                    String.format("Reached the maximum ships with %d cells!", coordinates.getCellsNumber()));

        if (coordinates.getCellsNumber() > 5 || coordinates.getCellsNumber() < 2)
            throw new WrongCoordinatesException("Ship cells must be in range 2 - 5!");

        ships.put(ship, coordinates.getCellsNumber());

        int startRow = coordinates.getStartRow();
        int endRow = coordinates.getEndRow();
        int startCol = coordinates.getStartCol();
        int endCol = coordinates.getEndCol();

        switch (ship.getType()) {
            case Vertical:
                buildVerticalShip(startRow, endRow, startCol);
            case Horizontal:
                buildHorizontalShip(startCol, endCol, startRow);
        }
    }

    private void buildVerticalShip(int startRow, int endRow, int col) {
        for (int row = startRow; row <= endRow; row++) {
            board[row][col] = SHIP_FIELD;
        }
    }

    private void buildHorizontalShip(int startCol, int endCol, int row) {
        for (int col = startCol; col <= endCol; col++) {
            board[row][col] = SHIP_FIELD;
        }
    }

    public Set<Ship> getShips(){
        return ships.keySet();
    }

    public int getShipsWith5CellsLeft() {
        return shipsWith5CellsLeft;
    }

    public int getShipsWith4CellsLeft() {
        return shipsWith4CellsLeft;
    }

    public int getShipsWith3CellsLeft() {
        return shipsWith3CellsLeft;
    }

    public int getShipsWith2CellsLeft() {
        return shipsWith2CellsLeft;
    }
}
