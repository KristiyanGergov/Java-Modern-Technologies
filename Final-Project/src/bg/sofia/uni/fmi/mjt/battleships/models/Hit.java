package bg.sofia.uni.fmi.mjt.battleships.models;

import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongCoordinatesException;

import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.*;
import static bg.sofia.uni.fmi.mjt.battleships.constants.ExceptionConstants.*;

public class Hit {

    private int row;
    private int col;

    public Hit(String coordinates) throws WrongCoordinatesException {
        validateCoordinates(coordinates);
    }

    private void setCoordinates(char row, int col) {
        this.row = ROWS_CELLS.get(row);
        this.col = COLUMNS_CELLS.get(col);
    }

    private void validateCoordinates(String coordinates) throws WrongCoordinatesException {

        if (coordinates.length() < 2)
            throw new WrongCoordinatesException(PROVIDE_ROW_AND_COLUMN);

        char row = Character.toUpperCase(coordinates.charAt(0));

        if (row < START_ROW || row > END_ROW)
            throw new WrongCoordinatesException(INVALID_ROW_RANGE);

        int col = Integer.parseInt(coordinates.substring(1));

        if (col < START_COL || col > END_COL)
            throw new WrongCoordinatesException(INVALID_COLUMN_RANGE);

        setCoordinates(row, col);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
