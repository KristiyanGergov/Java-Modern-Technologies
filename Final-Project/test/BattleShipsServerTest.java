import bg.sofia.uni.fmi.mjt.battleships.client.GameClient;
import bg.sofia.uni.fmi.mjt.battleships.models.Player;
import bg.sofia.uni.fmi.mjt.battleships.server.GameServer;
import bg.sofia.uni.fmi.mjt.battleships.util.CommandExecutor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BattleShipsServerTest {

    private GameServer gameServer;

    @Before
    public void init() {
        gameServer = new GameServer();
        new Thread(gameServer).start();
    }

    @Test
    public synchronized void testAddingPlayerOnConnection() {

        GameClient.connect("Gosho");
        assertEquals(gameServer.getLastJoinedPlayer().getUsername(), "Gosho");

        GameClient.connect("Pesho");
        assertEquals(gameServer.getLastJoinedPlayer().getUsername(), "Pesho");

    }

    @Test
    public synchronized void testCreateGame() throws FileNotFoundException {

        CommandExecutor commandExecutor = new CommandExecutor(
                new PrintWriter("file"),
                gameServer);


        GameClient.connect("Gosho");
        commandExecutor.create("game", new Player("Gosho", new Socket()));
        //todo
        assertEquals(gameServer.getGames().size(), 1);

    }

    @Test
    @Ignore
    public void testGetAllActiveGames() {
        fail();
    }

    @Test
    @Ignore
    public void testJoinSpecificGame() {

        GameClient.connect("Gosho");


        GameClient.connect("Pesho");
        GameClient.connect("Tosho");
        GameClient.connect("Mosho");


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
