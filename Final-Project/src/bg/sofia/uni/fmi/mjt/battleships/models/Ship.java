package bg.sofia.uni.fmi.mjt.battleships.models;

import bg.sofia.uni.fmi.mjt.battleships.enums.ShipOrientationType;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongCoordinatesException;

import static bg.sofia.uni.fmi.mjt.battleships.constants.ExceptionConstants.VERTICAL_OR_HORIZONTAL_SHIP;

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
        try {
            this.type = initializeShipType(startRow, endRow, startCol, endCol);
        } catch (WrongCoordinatesException e) {
            e.printStackTrace();
            //todo
        }
        this.shipCoordinates = new ShipCoordinates(startRow, endRow, startCol, endCol);
        this.lives = shipCoordinates.getCellsNumber();
    }

    public void hit() {
        this.lives--;
    }

    public boolean destroyed() {
        return lives == 0;
    }

    private ShipOrientationType initializeShipType(char startRow, char endRow, int startCol, int endCol) throws WrongCoordinatesException {

        if (startRow == endRow)
            return ShipOrientationType.Horizontal;
        else if (startCol == endCol)
            return ShipOrientationType.Vertical;

        throw new WrongCoordinatesException(VERTICAL_OR_HORIZONTAL_SHIP);

    }

}
