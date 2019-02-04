import bg.sofia.uni.fmi.mjt.battleships.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongCoordinatesException;
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
import static org.junit.Assert.*;

public class GunTest {
    private BoardCreator boardCreator;
    private ShipBuilder shipBuilder;
    private Gun gun;

    @Before
    public void initialize() {
        this.boardCreator = new BoardCreator(ROWS, COLUMNS);
        this.shipBuilder = new ShipBuilder(boardCreator.getBoard());
        this.gun = new Gun(boardCreator.getBoard(), shipBuilder.getShips());
    }

    @Test
    public void testShotHitShip() throws InvalidCommandException, WrongCoordinatesException {

        shipBuilder.buildShip(new Ship('A', 'A', 1, 3));

        Hit hit1 = new Hit("A1");
        Hit hit2 = new Hit("A2");
        Hit hit3 = new Hit("A3");

        char[][] board = boardCreator.getBoard();

        assertNotNull(gun.hitShip(hit1));
        assertNotNull(gun.hitShip(hit2));
        assertNotNull(gun.hitShip(hit3));

        assertEquals(board[hit1.getRow()][hit1.getCol()], HIT_SHIP_FIELD);
        assertEquals(board[hit2.getRow()][hit2.getCol()], HIT_SHIP_FIELD);
        assertEquals(board[hit3.getRow()][hit3.getCol()], HIT_SHIP_FIELD);
    }

    @Test
    public void testShotOnAlreadyShotFieldThrowsException() {
        try {
            gun.hitShip(new Hit("a1"));
            gun.hitShip(new Hit("a1"));
            fail();
        } catch (WrongCoordinatesException ignore) {
        }
    }

    @Test
    public void testShotHitEmptyField() throws WrongCoordinatesException {

        Hit hit1 = new Hit("A1");
        Hit hit2 = new Hit("A2");
        Hit hit3 = new Hit("A3");

        char[][] board = boardCreator.getBoard();

        assertNull(gun.hitShip(hit1));
        assertNull(gun.hitShip(hit2));
        assertNull(gun.hitShip(hit3));

        assertEquals(board[hit1.getRow()][hit1.getCol()], HIT_EMPTY_FIELD);
        assertEquals(board[hit2.getRow()][hit2.getCol()], HIT_EMPTY_FIELD);
        assertEquals(board[hit3.getRow()][hit3.getCol()], HIT_EMPTY_FIELD);
    }

    @Test
    public void testShotReturnsCorrectResult() throws InvalidCommandException, WrongCoordinatesException {
        shipBuilder.buildShip(new Ship('C', 'C', 3, 5));

        assertNotNull(gun.hitShip(new Hit("C3")));
        assertNotNull(gun.hitShip(new Hit("C4")));
        assertFalse(((Ship) shipBuilder.getShips().toArray()[0]).destroyed());
        assertNotNull(gun.hitShip(new Hit("C5")));

        assertTrue(((Ship) shipBuilder.getShips().toArray()[0]).destroyed());
    }

}
