package bg.sofia.uni.fmi.mjt.battleships.util;

import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongHitCoordinatesException;
import bg.sofia.uni.fmi.mjt.battleships.models.Hit;

import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.DEFAULT_BOARD_FIELD;
import static bg.sofia.uni.fmi.mjt.battleships.constants.HitConstants.HIT_EMPTY_FIELD;
import static bg.sofia.uni.fmi.mjt.battleships.constants.HitConstants.HIT_SHIP_FIELD;
import static bg.sofia.uni.fmi.mjt.battleships.constants.ShipConstants.SHIP_FIELD;

public class Gun {

    private char[][] board;

    public Gun(char[][] board) {
        this.board = board;
    }

    public boolean makeHit(Hit hit) throws WrongHitCoordinatesException {

        int row = hit.getRow();
        int col = hit.getCol();

        char current = board[row][col];

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
