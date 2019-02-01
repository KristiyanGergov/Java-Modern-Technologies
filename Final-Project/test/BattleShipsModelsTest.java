import bg.sofia.uni.fmi.mjt.battleships.exceptions.GameFullException;
import bg.sofia.uni.fmi.mjt.battleships.models.Game;
import bg.sofia.uni.fmi.mjt.battleships.models.Player;
import bg.sofia.uni.fmi.mjt.battleships.util.BoardCreator;
import org.junit.Before;
import org.junit.Test;

import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BattleShipsModelsTest {

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


    @Test
    public void testJoiningGameThrowsException() {
        Game game = new Game(new Player("gosho", null));

        try {
            game.join(new Player("tosho", null));
            assertEquals(game.getPlayer2().getUsername(), "tosho");
            game.join(new Player("pesho", null));
            fail();
        } catch (GameFullException ignore) {
            //success
        }

    }


}
