import bg.sofia.uni.fmi.mjt.battleships.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongCoordinatesException;
import bg.sofia.uni.fmi.mjt.battleships.models.Ship;
import bg.sofia.uni.fmi.mjt.battleships.util.BoardCreator;
import bg.sofia.uni.fmi.mjt.battleships.util.ShipBuilder;
import org.junit.Before;
import org.junit.Test;

import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.COLUMNS;
import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.ROWS;
import static bg.sofia.uni.fmi.mjt.battleships.constants.ShipConstants.SHIP_FIELD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ShipTest {

    private BoardCreator boardCreator;
    private ShipBuilder shipBuilder;

    @Before
    public void initialize() {
        this.boardCreator = new BoardCreator(ROWS, COLUMNS);
        this.shipBuilder = new ShipBuilder(boardCreator.getBoard());
    }

    @Test
    public void testAddVerticalShip() throws InvalidCommandException, WrongCoordinatesException {
        shipBuilder.buildShip(new Ship('C', 'F', 1, 1));

        assertEquals(boardCreator.getBoard()[2][0], SHIP_FIELD);
        assertEquals(boardCreator.getBoard()[3][0], SHIP_FIELD);
        assertEquals(boardCreator.getBoard()[4][0], SHIP_FIELD);
        assertEquals(boardCreator.getBoard()[5][0], SHIP_FIELD);
    }

    @Test
    public void testAddHorizontalShip() throws InvalidCommandException, WrongCoordinatesException {
        shipBuilder.buildShip(new Ship('A', 'A', 3, 6));

        assertEquals(boardCreator.getBoard()[0][2], SHIP_FIELD);
        assertEquals(boardCreator.getBoard()[0][3], SHIP_FIELD);
        assertEquals(boardCreator.getBoard()[0][4], SHIP_FIELD);
        assertEquals(boardCreator.getBoard()[0][5], SHIP_FIELD);
    }

    @Test
    public void testAddingMoreShipsThanAllowedThrowsException() throws InvalidCommandException, WrongCoordinatesException {

        //T W O   C E L L S
        shipBuilder.buildShip(new Ship('A', 'A', 9, 10));
        shipBuilder.buildShip(new Ship('A', 'A', 2, 3));
        shipBuilder.buildShip(new Ship('A', 'A', 4, 5));
        shipBuilder.buildShip(new Ship('A', 'A', 6, 7));

        try {
            shipBuilder.buildShip(new Ship('B', 'B', 7, 8));
            fail();
        } catch (InvalidCommandException ignore) {
            //success
        }


        //T H R E E   C E L L S

        shipBuilder.buildShip(new Ship('C', 'C', 1, 3));
        shipBuilder.buildShip(new Ship('C', 'C', 4, 6));
        shipBuilder.buildShip(new Ship('C', 'C', 7, 9));

        try {
            shipBuilder.buildShip(new Ship('B', 'D', 10, 10));
            fail();

        } catch (InvalidCommandException ignore) {
            //success
        }

        //F O U R   C E L L S

        shipBuilder.buildShip(new Ship('E', 'E', 1, 4));
        shipBuilder.buildShip(new Ship('E', 'E', 5, 8));

        try {
            shipBuilder.buildShip(new Ship('F', 'F', 4, 7));
            fail();

        } catch (InvalidCommandException ignore) {
            //success
        }

        //F I V E   C E L L S

        shipBuilder.buildShip(new Ship('J', 'J', 1, 5));

        try {
            shipBuilder.buildShip(new Ship('J', 'J', 6, 10));
            fail();

        } catch (InvalidCommandException ignore) {
            //success
        }

    }

}
