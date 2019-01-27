package bg.sofia.uni.fmi.mjt.battleships.util;

import bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants;
import bg.sofia.uni.fmi.mjt.battleships.models.Ship;

public class ShipCommander {

    private char[][] board;

    public ShipCommander(char[][] board) {
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
