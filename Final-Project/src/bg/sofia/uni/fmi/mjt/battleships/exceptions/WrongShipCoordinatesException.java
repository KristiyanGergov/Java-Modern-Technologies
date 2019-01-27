package bg.sofia.uni.fmi.mjt.battleships.exceptions;

public class WrongShipCoordinatesException extends RuntimeException {

    public WrongShipCoordinatesException(String message) {
        super(message);
    }

}