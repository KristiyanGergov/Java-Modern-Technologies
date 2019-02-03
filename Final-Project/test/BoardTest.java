import bg.sofia.uni.fmi.mjt.battleships.util.BoardCreator;
import org.junit.Before;
import org.junit.Test;

import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BoardTest {
    private BoardCreator boardCreator;

    @Before
    public void initialize() {
        this.boardCreator = new BoardCreator(ROWS, COLUMNS);
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

}
