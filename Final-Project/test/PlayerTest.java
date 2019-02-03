import bg.sofia.uni.fmi.mjt.battleships.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongCoordinatesException;
import bg.sofia.uni.fmi.mjt.battleships.models.Game;
import bg.sofia.uni.fmi.mjt.battleships.models.Player;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;

import static bg.sofia.uni.fmi.mjt.battleships.constants.HitConstants.HIT_EMPTY_FIELD;
import static bg.sofia.uni.fmi.mjt.battleships.constants.HitConstants.HIT_SHIP_FIELD;
import static junit.framework.TestCase.*;

public class PlayerTest {
    private static Game game;

    @Before
    public void init() {
        game = new Game(new Player("gosho", new Socket()), "game");
    }

    @Test
    public void testPlayerThatJoinedGameIsOnTurn() {

        game.join(new Player("pesho", new Socket()));

        assertTrue(game.getPlayer2().isOnTurn());
        assertFalse(game.getPlayer1().isOnTurn());
    }

    @Test
    public void testPlayersSwitchTurns() {
        game.join(new Player("Pesho", new Socket()));

        assertTrue(game.getPlayer2().isOnTurn());
        assertFalse(game.getPlayer1().isOnTurn());

        game.switchTurns();

        assertFalse(game.getPlayer2().isOnTurn());
        assertTrue(game.getPlayer1().isOnTurn());

    }

    @Test
    public void testBuildingShipsDoesNotExceedMaximumNumber() throws InvalidCommandException, WrongCoordinatesException {

        Player player = new Player("gosho", new Socket());

        assertTrue(player.buildShip('A', 'A', 1, 2));
        assertTrue(player.buildShip('A', 'A', 3, 4));
        assertTrue(player.buildShip('A', 'A', 5, 6));
        assertTrue(player.buildShip('A', 'A', 7, 8));
        assertTrue(player.buildShip('B', 'B', 1, 3));
        assertTrue(player.buildShip('B', 'B', 4, 6));
        assertTrue(player.buildShip('B', 'B', 7, 9));
        assertTrue(player.buildShip('C', 'C', 1, 4));
        assertTrue(player.buildShip('C', 'C', 5, 8));

        //just to avoid copy of code
        assertFalse(player.hasBuildAllShips());

        assertTrue(player.buildShip('D', 'D', 1, 5));

        //just to avoid copy of code
        assertTrue(player.hasBuildAllShips());

        assertFalse(player.buildShip('A', 'A', 1, 2));
    }


    @Test
    public void testShotSwitchTurns() throws WrongCoordinatesException, InvalidCommandException {
        game.join(new Player("pesho", new Socket()));

        game.getPlayer2().makeShot("A1");

        assertTrue(game.getPlayer1().isOnTurn());
        assertFalse(game.getPlayer2().isOnTurn());
    }

    @Test
    public void testShotAffectsEnemyBoard() throws InvalidCommandException, WrongCoordinatesException {
        Player player = new Player("gosho", new Socket());
        game.join(player);

        player.buildShip('A', 'A', 1, 2);
        player.buildShip('A', 'A', 3, 4);
        player.buildShip('A', 'A', 5, 6);
        player.buildShip('A', 'A', 7, 8);
        player.buildShip('B', 'B', 1, 3);
        player.buildShip('B', 'B', 4, 6);
        player.buildShip('B', 'B', 7, 9);
        player.buildShip('C', 'C', 1, 4);
        player.buildShip('C', 'C', 5, 8);

        Player player1 = game.getPlayer1();
        player1.buildShip('A', 'A', 1, 2);
        player1.buildShip('A', 'A', 3, 4);
        player1.buildShip('A', 'A', 5, 6);
        player1.buildShip('A', 'A', 7, 8);
        player1.buildShip('B', 'B', 1, 3);
        player1.buildShip('B', 'B', 4, 6);
        player1.buildShip('B', 'B', 7, 9);
        player1.buildShip('C', 'C', 1, 4);
        player1.buildShip('C', 'C', 5, 8);

        player.makeShot("A1");
        assertEquals(HIT_SHIP_FIELD, player1.getBoard()[0][0]);

        player1.makeShot("B2");
        assertEquals(HIT_SHIP_FIELD, player.getBoard()[1][1]);

        player.makeShot("D2");
        assertEquals(HIT_EMPTY_FIELD, player1.getBoard()[3][1]);
    }
}