package bg.sofia.uni.fmi.mjt.battleships.util;

import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongCoordinatesException;
import bg.sofia.uni.fmi.mjt.battleships.models.Hit;
import bg.sofia.uni.fmi.mjt.battleships.models.Ship;

import java.io.Serializable;
import java.util.Set;

import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.DEFAULT_BOARD_FIELD;
import static bg.sofia.uni.fmi.mjt.battleships.constants.ExceptionConstants.ALREADY_SHOT_FIELD;
import static bg.sofia.uni.fmi.mjt.battleships.constants.ExceptionConstants.NO_SHIP_AT_THIS_COORDINATES;
import static bg.sofia.uni.fmi.mjt.battleships.constants.HitConstants.HIT_EMPTY_FIELD;
import static bg.sofia.uni.fmi.mjt.battleships.constants.HitConstants.HIT_SHIP_FIELD;
import static bg.sofia.uni.fmi.mjt.battleships.constants.ShipConstants.SHIP_FIELD;

public class Gun implements Serializable {

    private char[][] board;
    private Set<Ship> ships;

    public Gun(char[][] board, Set<Ship> ships) {
        this.board = board;
        this.ships = ships;
    }

    private Ship findShipToHit(Hit hit) throws WrongCoordinatesException {

        final int row = hit.getRow();
        final int col = hit.getCol();

        for (Ship ship :
                ships) {

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

        throw new WrongCoordinatesException(NO_SHIP_AT_THIS_COORDINATES);
    }

    public Ship hitShip(Hit hit) throws WrongCoordinatesException {

        final int row = hit.getRow();
        final int col = hit.getCol();

        final char current = board[row][col];

        if (current == SHIP_FIELD) {
            Ship ship = findShipToHit(hit);
            ship.hit();
            board[row][col] = HIT_SHIP_FIELD;
            return ship;
        } else if (current == DEFAULT_BOARD_FIELD) {
            board[row][col] = HIT_EMPTY_FIELD;
            return null;
        }

        throw new WrongCoordinatesException(ALREADY_SHOT_FIELD);
    }

}
