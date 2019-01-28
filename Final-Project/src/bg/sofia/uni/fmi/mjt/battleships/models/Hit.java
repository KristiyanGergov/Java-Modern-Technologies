package bg.sofia.uni.fmi.mjt.battleships.models;

import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.COLUMNS_CELLS;
import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.ROWS_CELLS;

public class Hit {

    private int row;
    private int col;

    public Hit(char row, int col) {
        this.row = ROWS_CELLS.get(Character.toUpperCase(row));
        this.col = COLUMNS_CELLS.get(col);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
