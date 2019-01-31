import bg.sofia.uni.fmi.mjt.battleships.client.GameClient;
import bg.sofia.uni.fmi.mjt.battleships.server.GameServer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BattleShipsServerTest {

    private GameServer gameServer;

    @Before
    public void initf() {
        gameServer = new GameServer();
        new Thread(gameServer).start();
    }


    @BeforeClass
    public static void init() {
//        new Thread(new ChatServerRunnable()).start();
    }

    @Test
    public void testAddingPlayer() {

        GameClient.connect("Gosho");
        assertEquals(gameServer.getCurrentPlayer().getUsername(), "Gosho");

        GameClient.connect("Pesho");
        assertEquals(gameServer.getCurrentPlayer().getUsername(), "Pesho");

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
