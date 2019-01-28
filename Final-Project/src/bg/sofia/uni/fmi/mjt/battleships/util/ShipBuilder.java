package bg.sofia.uni.fmi.mjt.battleships.util;

import bg.sofia.uni.fmi.mjt.battleships.exceptions.ExceededNumberOfShipsException;
import bg.sofia.uni.fmi.mjt.battleships.models.Ship;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static bg.sofia.uni.fmi.mjt.battleships.constants.ShipConstants.*;

public class ShipBuilder {

    private char[][] board;

    private Map<Ship, Integer> ships;

    public ShipBuilder(char[][] board) {
        this.board = board;
        this.ships = new HashMap<>();
    }

    private boolean areMaximumShipsReached(Collection<Integer> cellsNumber, int maximumShips, int shipCell) {

        int counter = 0;
        for (int cell : cellsNumber) {

            if (cell == shipCell)
                counter++;

            if (counter >= maximumShips)
                return false;
        }
        return true;
    }

    private boolean checkIfShipCanBeAdded(int shipCells) {

        Collection<Integer> cellsNumber = ships.values();

        switch (shipCells) {

            case ONE_SHIP_CELLS:
                return areMaximumShipsReached(cellsNumber, 1, ONE_SHIP_CELLS);
            case TWO_SHIPS_CELLS:
                return areMaximumShipsReached(cellsNumber, 2, TWO_SHIPS_CELLS);
            case THREE_SHIPS_CELLS:
                return areMaximumShipsReached(cellsNumber, 3, THREE_SHIPS_CELLS);
            case FOUR_SHIPS_CELLS:
                return areMaximumShipsReached(cellsNumber, 4, FOUR_SHIPS_CELLS);
        }

        return true;
    }

    public void buildShip(Ship ship) throws ExceededNumberOfShipsException {

        int shipCells = ship.getShipCoordinates().getCellsNumber();

        if (!checkIfShipCanBeAdded(shipCells))
            throw new ExceededNumberOfShipsException(
                    String.format("Reached the maximum ships with %d cells!", shipCells));

        ships.put(ship, ship.getShipCoordinates().getCellsNumber());

        int startRow = ship.getShipCoordinates().getStartRow();
        int endRow = ship.getShipCoordinates().getEndRow();
        int startCol = ship.getShipCoordinates().getStartCol();
        int endCol = ship.getShipCoordinates().getEndCol();

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

}
