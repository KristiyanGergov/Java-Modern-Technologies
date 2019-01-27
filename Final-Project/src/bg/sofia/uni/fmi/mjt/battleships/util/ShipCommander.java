package bg.sofia.uni.fmi.mjt.battleships.util;

import bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants;
import bg.sofia.uni.fmi.mjt.battleships.constants.ShipConstants;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.ExceededNumberOfShipsException;
import bg.sofia.uni.fmi.mjt.battleships.models.Ship;

import java.util.*;

import static bg.sofia.uni.fmi.mjt.battleships.constants.ShipConstants.*;

public class ShipCommander {

    private char[][] board;

    private Map<Ship, Integer> ships;

    public ShipCommander(char[][] board) {
        this.board = board;
        this.ships = new HashMap<>();
    }

    private boolean checkIfShipCanBeAdded(int shipCells) {

        Collection<Integer> cellsNumber = ships.values();

        switch (shipCells) {

            case FIVE_CELLS_SHIPS:
                if (cellsNumber.contains(FIVE_CELLS_SHIPS))
                    return false;
                break;
            case ShipConstants.FOUR_CELLS_SHIPS:
                if (cellsNumber.containsAll(List.of(FOUR_CELLS_SHIPS, FOUR_CELLS_SHIPS)))
                    return false;
                break;
            case ShipConstants.THREE_CELLS_SHIPS:
                if (cellsNumber.containsAll(List.of(THREE_CELLS_SHIPS, THREE_CELLS_SHIPS, THREE_CELLS_SHIPS)))
                    return false;
                break;
            case ShipConstants.TWO_CELLS_SHIPS:
                if (cellsNumber.containsAll(List.of(TWO_CELLS_SHIPS, TWO_CELLS_SHIPS, TWO_CELLS_SHIPS, TWO_CELLS_SHIPS)))
                    return false;
                break;
        }

        return true;
    }

    public void addShip(Ship ship) throws ExceededNumberOfShipsException {

        int shipCells = ship.getCellsNumber();

        if (!checkIfShipCanBeAdded(shipCells))
            throw new ExceededNumberOfShipsException(
                    String.format("Reached the maximum ships with %d cells!", shipCells));

        ships.put(ship, ship.getCellsNumber());

        switch (ship.getType()) {
            case Vertical:
                addVerticalShip(ship.getStartRow(), ship.getEndRow(), ship.getStartCol());
            case Horizontal:
                addHorizontalShip(ship.getStartCol(), ship.getEndCol(), ship.getStartRow());
        }
    }

    public void hitShip(int x, int y) {
        board[x][y] = BoardConstants.HIT_SHIP_FIELD;
    }

    private void addVerticalShip(int startRow, int endRow, int col) {
        for (int row = startRow; row <= endRow; row++) {
            board[row][col] = '*';
        }
    }

    private void addHorizontalShip(int startCol, int endCol, int row) {
        for (int col = startCol; col <= endCol; col++) {
            board[row][col] = '*';
        }
    }

}
