package bg.sofia.uni.fmi.mjt.battleships.util;

import bg.sofia.uni.fmi.mjt.battleships.models.Ship;

public class ShipMaker {

    private char[][] board;

    public ShipMaker(char[][] board) {
        this.board = board;
    }

    public void addShip(Ship ship) {

        switch (ship.getType()) {
            case Vertical:
                addVerticalShip(ship.getStartRow(), ship.getEndRow(), ship.getStartCol());
            case Horizontal:
                addHorizontalShip(ship.getStartCol(), ship.getEndCol(), ship.getStartRow());
        }
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
