package bg.sofia.uni.fmi.mjt.battleships.models;

import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.COLUMNS_CELLS;
import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.ROWS_CELLS;

public class ShipCoordinates {

    private int startRow;
    private int endRow;
    private int startCol;
    private int endCol;

    public int getStartRow() {
        return startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getEndCol() {
        return endCol;
    }

    public int getCellsNumber() {

        if (startCol == endCol)
            return endRow - startRow + 1;
        else
            return endCol - startCol + 1;
    }

    public ShipCoordinates(char startRow, char endRow, int startCol, int endCol) {

        this.startRow = ROWS_CELLS.get(Character.toUpperCase(startRow));
        this.endRow = ROWS_CELLS.get(Character.toUpperCase(endRow));
        this.startCol = COLUMNS_CELLS.get(startCol);
        this.endCol = COLUMNS_CELLS.get(endCol);

    }

}
