package bg.sofia.uni.fmi.mjt.battleships.models;

import bg.sofia.uni.fmi.mjt.battleships.enums.ShipOrientationType;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongShipCoordinatesException;

import java.util.HashSet;
import java.util.Set;

public class Ship {

    private ShipCoordinates shipCoordinates;
    private Set<Integer> cellsDestroyed;

    private ShipOrientationType type;

    public ShipCoordinates getShipCoordinates() {
        return shipCoordinates;
    }

    public ShipOrientationType getType() {
        return type;
    }

    public Ship(char startRow, char endRow, int startCol, int endCol) {
        type = initializeShipType(startRow, endRow, startCol, endCol);
        this.cellsDestroyed = new HashSet<>();
        this.shipCoordinates = new ShipCoordinates(startRow, endRow, startCol, endCol);
    }

    private ShipOrientationType initializeShipType(char startRow, char endRow, int startCol, int endCol) {

        if (startRow == endRow)
            return ShipOrientationType.Horizontal;
        else if (startCol == endCol)
            return ShipOrientationType.Vertical;

        throw new WrongShipCoordinatesException("Ship must be either horizontal or vertical! Please check the coordinates provided.");

    }

}
