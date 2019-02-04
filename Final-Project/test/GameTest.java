import bg.sofia.uni.fmi.mjt.battleships.models.Game;
import bg.sofia.uni.fmi.mjt.battleships.models.Player;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.*;

public class GameTest {

    private Game game;

    @Before
    public void init() {
        this.game = new Game(new Player("gosho", new Socket()), "game");
    }


    @Test
    public void testGameIsFullAfterTwoPlayersJoin() {

        assertFalse(game.isFull());
        game.join(new Player("pesho", new Socket()));

        assertTrue(game.isFull());

        assertFalse(game.join(new Player("pesho", new Socket())));
    }

    @Test
    public void testTwoGamesEqualsWhenWithSameName() {
        Game game2 = new Game(new Player("asdfag", new Socket()), "game");

        assertEquals(game2, game2);

        Game game3 = new Game(new Player("gosho", new Socket()), "gg");

        assertNotEquals(game, game3);
    }


    @Test
    public void testJoiningGameSetsTheTurnToPlayer2() {
        game.join(new Player("pesho", new Socket()));

        assertTrue(game.getPlayer2().isOnTurn());
        assertFalse(game.getPlayer1().isOnTurn());
    }

    @Test
    public void testLeaveGame() {
        game.leave(game.getPlayer1());

        assertNull(game.getPlayer1());
        assertNull(game.getPlayer2());

        game.join(new Player("gosho", new Socket()));
        assertNotNull(game.getPlayer1());
        game.join(new Player("pesho", new Socket()));
        assertTrue(game.isFull());
    }

}
