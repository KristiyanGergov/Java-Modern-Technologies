import bg.sofia.uni.fmi.mjt.battleships.exceptions.ExceededNumberOfShipsException;
import bg.sofia.uni.fmi.mjt.battleships.models.Ship;
import bg.sofia.uni.fmi.mjt.battleships.util.BoardCreator;
import bg.sofia.uni.fmi.mjt.battleships.util.ShipCommander;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.COLUMNS;
import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.ROWS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BattleShipsTest {

    private BoardCreator boardCreator;
    private ShipCommander shipCommander;


    @Before
    public void initialize() {
        this.boardCreator = new BoardCreator(ROWS, COLUMNS);
        this.shipCommander = new ShipCommander(boardCreator.getBoard());
    }

    @Test
    public void testInitializationOfBoardIsCorrect() {

        char[][] board = boardCreator.getBoard();

        assertEquals(board.length, ROWS);
        assertEquals(board[0].length, COLUMNS);

        for (char[] row : board) {
            for (char col : row) {
                if (col != '-')
                    fail();
            }
        }

    }

    @Test
    public void testAddVerticalShip() throws ExceededNumberOfShipsException {
        shipCommander.addShip(new Ship('C', 'F', 1, 1));

        assertEquals(boardCreator.getBoard()[2][0], '*');
        assertEquals(boardCreator.getBoard()[3][0], '*');
        assertEquals(boardCreator.getBoard()[4][0], '*');
        assertEquals(boardCreator.getBoard()[5][0], '*');


    }

    @Test
    public void testAddHorizontalShip() throws ExceededNumberOfShipsException {
        shipCommander.addShip(new Ship('A', 'A', 3, 6));

        assertEquals(boardCreator.getBoard()[0][2], '*');
        assertEquals(boardCreator.getBoard()[0][3], '*');
        assertEquals(boardCreator.getBoard()[0][4], '*');
        assertEquals(boardCreator.getBoard()[0][5], '*');
    }


    @Test
    public void testAddingMoreShipsThanAllowedThrowsException() {

        //T W O   C E L L S
        try {
            shipCommander.addShip(new Ship('A', 'A', 9, 10));
            shipCommander.addShip(new Ship('A', 'A', 2, 3));
            shipCommander.addShip(new Ship('A', 'A', 3, 4));
            shipCommander.addShip(new Ship('A', 'A', 5, 6));
            shipCommander.addShip(new Ship('A', 'A', 7, 8));
            fail();

        } catch (ExceededNumberOfShipsException ignore) { }

        //T H R E E   C E L L S
        try {
            shipCommander.addShip(new Ship('A', 'A', 1, 3));
            shipCommander.addShip(new Ship('A', 'A', 4, 6));
            shipCommander.addShip(new Ship('A', 'A', 7, 9));
            shipCommander.addShip(new Ship('B', 'D', 10, 10));
            fail();

        } catch (ExceededNumberOfShipsException ignore) { }

        //F O U R   C E L L S
        try {
            shipCommander.addShip(new Ship('A', 'A', 1, 4));
            shipCommander.addShip(new Ship('A', 'A', 5, 8));
            shipCommander.addShip(new Ship('B', 'E', 9, 9));
            fail();

        } catch (ExceededNumberOfShipsException ignore) { }

        //F I V E   C E L L S
        try {
            shipCommander.addShip(new Ship('A', 'A', 1, 5));
            shipCommander.addShip(new Ship('A', 'A', 6, 10));
            fail();

        } catch (ExceededNumberOfShipsException ignore) { }

    }


    @Test
    @Ignore
    public void testShotHitShip() {
        fail();
    }

    @Test
    @Ignore
    public void testShotAffectsBoard() {
        fail();
    }

    @Test
    @Ignore
    public void testShotReturnsCorrectResult() {
        fail();
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
