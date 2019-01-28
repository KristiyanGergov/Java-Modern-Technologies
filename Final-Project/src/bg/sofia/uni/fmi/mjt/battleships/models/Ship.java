package bg.sofia.uni.fmi.mjt.battleships.models;

import bg.sofia.uni.fmi.mjt.battleships.enums.ShipOrientationType;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongShipCoordinatesException;

import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.COLUMNS_CELLS;
import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.ROWS_CELLS;

public class Ship {

    private int startRow;
    private int endRow;
    private int startCol;
    private int endCol;

    private int lives;
    private boolean destroyed;

    private ShipOrientationType type;

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

    public ShipOrientationType getType() {
        return type;
    }

    public int getCellsNumber() {

        if (startCol == endCol)
            return endRow - startRow + 1;
        else
            return endCol - startCol + 1;
    }

    public Ship(char startRow, char endRow, int startCol, int endCol) {
        type = initializeShipType(startRow, endRow, startCol, endCol);
        this.destroyed = false;
        this.startRow = ROWS_CELLS.get(Character.toUpperCase(startRow));
        this.endRow = ROWS_CELLS.get(Character.toUpperCase(endRow));
        this.startCol = COLUMNS_CELLS.get(startCol);
        this.endCol = COLUMNS_CELLS.get(endCol);
    }

    private ShipOrientationType initializeShipType(char startRow, char endRow, int startCol, int endCol) {

        if (startRow == endRow)
            return ShipOrientationType.Horizontal;
        else if (startCol == endCol)
            return ShipOrientationType.Vertical;

        throw new WrongShipCoordinatesException("Ship must be either horizontal or vertical! Please check the coordinates provided.");

    }

}
