package bg.sofia.uni.fmi.mjt.battleships.util;

import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongHitCoordinatesException;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongShipCoordinatesException;
import bg.sofia.uni.fmi.mjt.battleships.models.Hit;
import bg.sofia.uni.fmi.mjt.battleships.models.Ship;

import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.DEFAULT_BOARD_FIELD;
import static bg.sofia.uni.fmi.mjt.battleships.constants.ExceptionConstants.ALREADY_SHOT_FIELD;
import static bg.sofia.uni.fmi.mjt.battleships.constants.ExceptionConstants.NO_SHIP_AT_THIS_COORDINATES;
import static bg.sofia.uni.fmi.mjt.battleships.constants.HitConstants.HIT_EMPTY_FIELD;
import static bg.sofia.uni.fmi.mjt.battleships.constants.HitConstants.HIT_SHIP_FIELD;
import static bg.sofia.uni.fmi.mjt.battleships.constants.ShipConstants.SHIP_FIELD;

public class Gun {

    private char[][] board;

    public Gun(char[][] board) {
        this.board = board;
    }

    private Ship findShipToHit(Hit hit) throws WrongShipCoordinatesException {

        final int row = hit.getRow();
        final int col = hit.getCol();

        for (Ship ship :
                ShipBuilder.getShips()) {

            switch (ship.getType()) {
                case Vertical:
                    if (ship.getShipCoordinates().getStartCol() == col &&
                            ship.getShipCoordinates().getStartRow() <= row &&
                            ship.getShipCoordinates().getEndRow() >= row)
                        return ship;
                case Horizontal:
                    if (ship.getShipCoordinates().getStartRow() == row &&
                            ship.getShipCoordinates().getStartCol() <= col &&
                            ship.getShipCoordinates().getEndCol() >= col)
                        return ship;
            }
        }

        throw new WrongShipCoordinatesException(NO_SHIP_AT_THIS_COORDINATES);
    }

    public boolean hitShip(Hit hit) throws WrongHitCoordinatesException, WrongShipCoordinatesException {

        final int row = hit.getRow();
        final int col = hit.getCol();

        final char current = board[row][col];

        if (current == SHIP_FIELD) {
            findShipToHit(hit).hit();
            board[row][col] = HIT_SHIP_FIELD;
            return true;
        } else if (current == DEFAULT_BOARD_FIELD) {
            board[row][col] = HIT_EMPTY_FIELD;
            return false;
        }

        throw new WrongHitCoordinatesException(ALREADY_SHOT_FIELD);
    }

}
