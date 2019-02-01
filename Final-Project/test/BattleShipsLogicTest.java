import bg.sofia.uni.fmi.mjt.battleships.exceptions.ExceededNumberOfShipsException;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongHitCoordinatesException;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongShipCoordinatesException;
import bg.sofia.uni.fmi.mjt.battleships.models.Hit;
import bg.sofia.uni.fmi.mjt.battleships.models.Ship;
import bg.sofia.uni.fmi.mjt.battleships.util.BoardCreator;
import bg.sofia.uni.fmi.mjt.battleships.util.Gun;
import bg.sofia.uni.fmi.mjt.battleships.util.ShipBuilder;
import org.junit.Before;
import org.junit.Test;

import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.COLUMNS;
import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.ROWS;
import static bg.sofia.uni.fmi.mjt.battleships.constants.HitConstants.HIT_EMPTY_FIELD;
import static bg.sofia.uni.fmi.mjt.battleships.constants.HitConstants.HIT_SHIP_FIELD;
import static bg.sofia.uni.fmi.mjt.battleships.constants.ShipConstants.SHIP_FIELD;
import static org.junit.Assert.*;

public class BattleShipsLogicTest {

    private BoardCreator boardCreator;
    private ShipBuilder shipBuilder;
    private Gun gun;

    @Before
    public void initialize() {
        this.boardCreator = new BoardCreator(ROWS, COLUMNS);
        this.shipBuilder = new ShipBuilder(boardCreator.getBoard());
        this.gun = new Gun(boardCreator.getBoard());
    }

    @Test
    public void testAddVerticalShip() throws ExceededNumberOfShipsException {
        shipBuilder.buildShip(new Ship('C', 'F', 1, 1));

        assertEquals(boardCreator.getBoard()[2][0], SHIP_FIELD);
        assertEquals(boardCreator.getBoard()[3][0], SHIP_FIELD);
        assertEquals(boardCreator.getBoard()[4][0], SHIP_FIELD);
        assertEquals(boardCreator.getBoard()[5][0], SHIP_FIELD);


    }

    @Test
    public void testAddHorizontalShip() throws ExceededNumberOfShipsException {
        shipBuilder.buildShip(new Ship('A', 'A', 3, 6));

        assertEquals(boardCreator.getBoard()[0][2], SHIP_FIELD);
        assertEquals(boardCreator.getBoard()[0][3], SHIP_FIELD);
        assertEquals(boardCreator.getBoard()[0][4], SHIP_FIELD);
        assertEquals(boardCreator.getBoard()[0][5], SHIP_FIELD);
    }

    @Test
    public void testAddingMoreShipsThanAllowedThrowsException() throws ExceededNumberOfShipsException {

        //T W O   C E L L S
        shipBuilder.buildShip(new Ship('A', 'A', 9, 10));
        shipBuilder.buildShip(new Ship('A', 'A', 2, 3));
        shipBuilder.buildShip(new Ship('A', 'A', 3, 4));
        shipBuilder.buildShip(new Ship('A', 'A', 5, 6));

        try {
            shipBuilder.buildShip(new Ship('A', 'A', 7, 8));
            fail();
        } catch (ExceededNumberOfShipsException ignore) {
            //success
        }

        //T H R E E   C E L L S

        shipBuilder.buildShip(new Ship('A', 'A', 1, 3));
        shipBuilder.buildShip(new Ship('A', 'A', 4, 6));
        shipBuilder.buildShip(new Ship('A', 'A', 7, 9));

        try {
            shipBuilder.buildShip(new Ship('B', 'D', 10, 10));
            fail();

        } catch (ExceededNumberOfShipsException ignore) {
            //success
        }

        //F O U R   C E L L S

        shipBuilder.buildShip(new Ship('A', 'A', 1, 4));
        shipBuilder.buildShip(new Ship('A', 'A', 5, 8));

        try {
            shipBuilder.buildShip(new Ship('B', 'E', 9, 9));
            fail();

        } catch (ExceededNumberOfShipsException ignore) {
            //success
        }

        //F I V E   C E L L S

        shipBuilder.buildShip(new Ship('A', 'A', 1, 5));

        try {
            shipBuilder.buildShip(new Ship('A', 'A', 6, 10));
            fail();

        } catch (ExceededNumberOfShipsException ignore) {
            //success
        }

    }

    @Test
    public void testShotHitShip() throws ExceededNumberOfShipsException, WrongHitCoordinatesException, WrongShipCoordinatesException {

        shipBuilder.buildShip(new Ship('A', 'A', 1, 3));

        Hit hit1 = new Hit("A1");
        Hit hit2 = new Hit("A2");
        Hit hit3 = new Hit("A3");

        char[][] board = boardCreator.getBoard();

        assertTrue(gun.hitShip(hit1));
        assertTrue(gun.hitShip(hit2));
        assertTrue(gun.hitShip(hit3));

        assertEquals(board[hit1.getRow()][hit1.getCol()], HIT_SHIP_FIELD);
        assertEquals(board[hit2.getRow()][hit2.getCol()], HIT_SHIP_FIELD);
        assertEquals(board[hit3.getRow()][hit3.getCol()], HIT_SHIP_FIELD);
    }

    @Test
    public void testShotOnAlreadyShotFieldThrowsException() throws WrongShipCoordinatesException {
        try {
            gun.hitShip(new Hit("a1"));
            gun.hitShip(new Hit("a1"));
            fail();
        } catch (WrongHitCoordinatesException ignore) {
        }
    }

    @Test
    public void testShotHitEmptyField() throws WrongHitCoordinatesException, WrongShipCoordinatesException {

        Hit hit1 = new Hit("A1");
        Hit hit2 = new Hit("A2");
        Hit hit3 = new Hit("A3");

        char[][] board = boardCreator.getBoard();

        assertFalse(gun.hitShip(hit1));
        assertFalse(gun.hitShip(hit2));
        assertFalse(gun.hitShip(hit3));

        assertEquals(board[hit1.getRow()][hit1.getCol()], HIT_EMPTY_FIELD);
        assertEquals(board[hit2.getRow()][hit2.getCol()], HIT_EMPTY_FIELD);
        assertEquals(board[hit3.getRow()][hit3.getCol()], HIT_EMPTY_FIELD);
    }

    @Test
    public void testShotReturnsCorrectResult() throws WrongHitCoordinatesException, ExceededNumberOfShipsException, WrongShipCoordinatesException {
        shipBuilder.buildShip(new Ship('C', 'C', 3, 5));

        assertTrue(gun.hitShip(new Hit("C3")));
        assertTrue(gun.hitShip(new Hit("C4")));
        assertFalse(((Ship) ShipBuilder.getShips().toArray()[0]).destroyed());
        assertTrue(gun.hitShip(new Hit("C5")));

        assertTrue(((Ship) ShipBuilder.getShips().toArray()[0]).destroyed());
    }

}