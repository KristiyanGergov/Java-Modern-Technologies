package bg.sofia.uni.fmi.mjt.battleships.models;

import bg.sofia.uni.fmi.mjt.battleships.enums.ShipOrientationType;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongShipCoordinatesException;

import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.COLUMNS_CELLS;
import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.ROWS_CELLS;

public class Ship {

    private ShipCoordinates shipCoordinates;
    private ShipOrientationType type;
    private int lives;

    public ShipCoordinates getShipCoordinates() {
        return shipCoordinates;
    }

    public ShipOrientationType getType() {
        return type;
    }

    public Ship(char startRow, char endRow, int startCol, int endCol) {
        this.type = initializeShipType(startRow, endRow, startCol, endCol);
        this.shipCoordinates = new ShipCoordinates(startRow, endRow, startCol, endCol);
        this.lives = shipCoordinates.getCellsNumber();
    }

    public void hit() {
        this.lives--;
    }

    public boolean destroyed() {
        return lives == 0;
    }

    private ShipOrientationType initializeShipType(char startRow, char endRow, int startCol, int endCol) {

        if (startRow == endRow)
            return ShipOrientationType.Horizontal;
        else if (startCol == endCol)
            return ShipOrientationType.Vertical;

        throw new WrongShipCoordinatesException("Ship must be either horizontal or vertical! Please check the coordinates provided.");

    }


    class ShipCoordinates {

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

        ShipCoordinates(char startRow, char endRow, int startCol, int endCol) {

            this.startRow = ROWS_CELLS.get(Character.toUpperCase(startRow));
            this.endRow = ROWS_CELLS.get(Character.toUpperCase(endRow));
            this.startCol = COLUMNS_CELLS.get(startCol);
            this.endCol = COLUMNS_CELLS.get(endCol);

        }

    }


}
