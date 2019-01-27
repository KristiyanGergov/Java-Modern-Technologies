package bg.sofia.uni.fmi.mjt.battleships.models;

import bg.sofia.uni.fmi.mjt.battleships.enums.ShipType;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongShipCoordinatesException;

public class Ship {

    private int startRow;
    private int endRow;
    private int startCol;
    private int endCol;

    private ShipType type;

    public int getStartRow() {
        return startRow - 1;
    }

    public int getEndRow() {
        return endRow - 1;
    }

    public int getStartCol() {
        return startCol - 1;
    }

    public int getEndCol() {
        return endCol - 1;
    }

    public ShipType getType() {
        return type;
    }

    public Ship(int startRow, int endRow, int startCol, int endCol) {
        type = initializeShipType(startRow, endRow, startCol, endCol);
        this.startRow = startRow;
        this.startCol = startCol;
        this.endCol = endCol;
        this.endRow = endRow;
    }

    private ShipType initializeShipType(int startRow, int endRow, int startCol, int endCol) {

        if (startRow == endRow)
            return ShipType.Horizontal;
        else if (startCol == endCol)
            return ShipType.Vertical;

        throw new WrongShipCoordinatesException("Ship must be either horizontal or vertical! Please check the coordinates provided.");

    }

}
