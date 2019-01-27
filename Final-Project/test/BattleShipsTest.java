import bg.sofia.uni.fmi.mjt.battleships.models.Ship;
import bg.sofia.uni.fmi.mjt.battleships.util.BoardCreator;
import bg.sofia.uni.fmi.mjt.battleships.util.ShipMaker;
import org.junit.Before;
import org.junit.Test;

import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.COLUMNS;
import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.ROWS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BattleShipsTest {

    private BoardCreator boardCreator;
    private ShipMaker shipMaker;


    @Before
    public void initialize() {
        this.boardCreator = new BoardCreator(ROWS, COLUMNS);
        this.shipMaker = new ShipMaker(boardCreator.getBoard());
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
    public void testAddVerticalShip() {
        shipMaker.addShip(new Ship(3, 6, 1, 1));

        assertEquals(boardCreator.getBoard()[2][0], '*');
        assertEquals(boardCreator.getBoard()[3][0], '*');
        assertEquals(boardCreator.getBoard()[4][0], '*');
        assertEquals(boardCreator.getBoard()[5][0], '*');


    }

    @Test
    public void testAddHorizontalShip() {
        shipMaker.addShip(new Ship(1, 1, 3, 6));

        assertEquals(boardCreator.getBoard()[0][2], '*');
        assertEquals(boardCreator.getBoard()[0][3], '*');
        assertEquals(boardCreator.getBoard()[0][4], '*');
        assertEquals(boardCreator.getBoard()[0][5], '*');
    }

    @Test
    public void testShotAffectsBoard() {
        fail();
    }

    @Test
    public void testShotReturnsCorrectResult() {
        fail();
    }

    @Test
    public void testGetAllActiveGames() {
        fail();
    }

    @Test
    public void testJoinSpecificGame() {
        fail();
    }

    @Test
    public void testJoinRandomGame() {
        fail();
    }

    @Test
    public void testPrintAllSavedGames() {
        fail();
    }

    @Test
    public void testSaveGame() {
        fail();
    }

    @Test
    public void testLoadGame() {
        fail();
    }

    @Test
    public void testDeleteGame() {
        fail();
    }
}
