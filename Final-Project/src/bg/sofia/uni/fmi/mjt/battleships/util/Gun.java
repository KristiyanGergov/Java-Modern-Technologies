package bg.sofia.uni.fmi.mjt.battleships.util;

import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongHitCoordinatesException;
import bg.sofia.uni.fmi.mjt.battleships.models.Hit;
import bg.sofia.uni.fmi.mjt.battleships.models.Ship;

import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.DEFAULT_BOARD_FIELD;
import static bg.sofia.uni.fmi.mjt.battleships.constants.HitConstants.HIT_EMPTY_FIELD;
import static bg.sofia.uni.fmi.mjt.battleships.constants.HitConstants.HIT_SHIP_FIELD;
import static bg.sofia.uni.fmi.mjt.battleships.constants.ShipConstants.SHIP_FIELD;

public class Gun {

    private char[][] board;

    public Gun(char[][] board) {
        this.board = board;
    }

    private boolean shipDestoryed(Hit hit) {

        final int row = hit.getRow();
        final int col = hit.getCol();

        for (Ship ship :
                ShipBuilder.getShips()) {

            switch (ship.getType()) {
                case Vertical:
                    if (ship.getShipCoordinates().getStartCol() == col &&
                            ship.getShipCoordinates().getStartRow() <= row &&
                            ship.getShipCoordinates().getEndRow() >= row)
                        return ship.destroyedAfterHit();
                case Horizontal:
                    if (ship.getShipCoordinates().getStartRow() == row &&
                            ship.getShipCoordinates().getStartCol() <= col &&
                            ship.getShipCoordinates().getEndCol() >= col)
                        return ship.destroyedAfterHit();
            }
        }

        return false;
    }

    public boolean hitShip(Hit hit) throws WrongHitCoordinatesException {

        final int row = hit.getRow();
        final int col = hit.getCol();

        final char current = board[row][col];

        if (current == SHIP_FIELD) {
            board[row][col] = HIT_SHIP_FIELD;
            return true;
        } else if (current == DEFAULT_BOARD_FIELD) {
            board[row][col] = HIT_EMPTY_FIELD;
            return false;
        }

        throw new WrongHitCoordinatesException("You have already shot that field!");
    }

}
