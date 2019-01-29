import bg.sofia.uni.fmi.mjt.battleships.exceptions.ExceededNumberOfShipsException;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongHitCoordinatesException;
import bg.sofia.uni.fmi.mjt.battleships.models.Hit;
import bg.sofia.uni.fmi.mjt.battleships.models.Ship;
import bg.sofia.uni.fmi.mjt.battleships.util.BoardCreator;
import bg.sofia.uni.fmi.mjt.battleships.util.Gun;
import bg.sofia.uni.fmi.mjt.battleships.util.ShipBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.*;
import static bg.sofia.uni.fmi.mjt.battleships.constants.HitConstants.HIT_EMPTY_FIELD;
import static bg.sofia.uni.fmi.mjt.battleships.constants.HitConstants.HIT_SHIP_FIELD;
import static bg.sofia.uni.fmi.mjt.battleships.constants.ShipConstants.SHIP_FIELD;
import static org.junit.Assert.*;

public class BattleShipsTest {

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
    public void testInitializationOfBoardIsCorrect() {

        char[][] board = boardCreator.getBoard();

        assertEquals(board.length, ROWS);
        assertEquals(board[0].length, COLUMNS);

        for (char[] row : board) {
            for (char col : row) {
                if (col != DEFAULT_BOARD_FIELD)
                    fail();
            }
        }

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
        }

        //T H R E E   C E L L S

        shipBuilder.buildShip(new Ship('A', 'A', 1, 3));
        shipBuilder.buildShip(new Ship('A', 'A', 4, 6));
        shipBuilder.buildShip(new Ship('A', 'A', 7, 9));

        try {
            shipBuilder.buildShip(new Ship('B', 'D', 10, 10));
            fail();

        } catch (ExceededNumberOfShipsException ignore) {
        }

        //F O U R   C E L L S

        shipBuilder.buildShip(new Ship('A', 'A', 1, 4));
        shipBuilder.buildShip(new Ship('A', 'A', 5, 8));

        try {
            shipBuilder.buildShip(new Ship('B', 'E', 9, 9));
            fail();

        } catch (ExceededNumberOfShipsException ignore) {
        }

        //F I V E   C E L L S

        shipBuilder.buildShip(new Ship('A', 'A', 1, 5));

        try {
            shipBuilder.buildShip(new Ship('A', 'A', 6, 10));
            fail();

        } catch (ExceededNumberOfShipsException ignore) {
        }

    }

    @Test
    public void testShotHitShip() throws ExceededNumberOfShipsException, WrongHitCoordinatesException {

        shipBuilder.buildShip(new Ship('A', 'A', 1, 3));

        Hit hit1 = new Hit('A', 1);
        Hit hit2 = new Hit('A', 2);
        Hit hit3 = new Hit('A', 3);

        char[][] board = boardCreator.getBoard();

        assertTrue(gun.hitShip(hit1));
        assertTrue(gun.hitShip(hit2));
        assertTrue(gun.hitShip(hit3));

        assertEquals(board[hit1.getRow()][hit1.getCol()], HIT_SHIP_FIELD);
        assertEquals(board[hit2.getRow()][hit2.getCol()], HIT_SHIP_FIELD);
        assertEquals(board[hit3.getRow()][hit3.getCol()], HIT_SHIP_FIELD);
    }

    @Test
    public void testShotOnAlreadyShotFieldThrowsException() {
        try {
            gun.hitShip(new Hit('a', 1));
            gun.hitShip(new Hit('a', 1));
            fail();
        } catch (WrongHitCoordinatesException ignore) {
        }
    }

    @Test
    public void testShotHitEmptyField() throws WrongHitCoordinatesException {

        Hit hit1 = new Hit('A', 1);
        Hit hit2 = new Hit('A', 2);
        Hit hit3 = new Hit('A', 3);

        char[][] board = boardCreator.getBoard();

        assertFalse(gun.hitShip(hit1));
        assertFalse(gun.hitShip(hit2));
        assertFalse(gun.hitShip(hit3));

        assertEquals(board[hit1.getRow()][hit1.getCol()], HIT_EMPTY_FIELD);
        assertEquals(board[hit2.getRow()][hit2.getCol()], HIT_EMPTY_FIELD);
        assertEquals(board[hit3.getRow()][hit3.getCol()], HIT_EMPTY_FIELD);
    }

    @Test
    public void testShotReturnsCorrectResult() throws WrongHitCoordinatesException, ExceededNumberOfShipsException {
        shipBuilder.buildShip(new Ship('C', 'C', 3, 5));

        assertTrue(gun.hitShip(new Hit('C', 3)));
        assertTrue(gun.hitShip(new Hit('C', 4)));
        assertFalse(((Ship) ShipBuilder.getShips().toArray()[0]).destroyed());
        assertTrue(gun.hitShip(new Hit('C', 5)));

        assertTrue(((Ship) ShipBuilder.getShips().toArray()[0]).destroyed());
    }

    @Test
    @Ignore
    public void testGetAllActiveGames() {
        fail();
    }

    @Test
    @Ignore
    public void testJoinSpecificGame() {
        fail();
    }

    @Test
    @Ignore
    public void testJoinRandomGame() {
        fail();
    }

    @Test
    @Ignore
    public void testPrintAllSavedGames() {
        fail();
    }

    @Test
    @Ignore
    public void testSaveGame() {
        fail();
    }

    @Test
    @Ignore
    public void testLoadGame() {
        fail();
    }

    @Test
    @Ignore
    public void testDeleteGame() {
        fail();
    }
}
