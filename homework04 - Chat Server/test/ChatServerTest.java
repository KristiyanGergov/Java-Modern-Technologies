import bg.sofia.uni.fmi.mjt.chat.IO.InputHandler;
import bg.sofia.uni.fmi.mjt.chat.client.ChatClient;
import bg.sofia.uni.fmi.mjt.chat.client.ClientConnectionRunnable;
import bg.sofia.uni.fmi.mjt.chat.models.User;
import bg.sofia.uni.fmi.mjt.chat.server.ChatServer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ChatServerTest {

    @Mock
    private ClientConnectionRunnable connectionRunnable;

    @Before
    public void init() {

        ChatServer.main(null);

    }

    @Test
    public void testUserHashingWorksProperly() {

        User user1 = new User("gosho");
        User user2 = new User("drug gosho");

        User user3 = new User("gosho");

        assertNotEquals(user1, user2);
        assertEquals(user1, user3);

        Map<User, Integer> hashUsers = new HashMap<>();

        hashUsers.put(user1, 0);
        hashUsers.put(user2, 1);
        hashUsers.put(user3, 2);

        assertEquals(hashUsers.get(user1), Integer.valueOf(2));
        assertEquals(hashUsers.get(user2), Integer.valueOf(1));
        assertEquals(hashUsers.get(user3), Integer.valueOf(2));
    }


    @Test
    public void testConnectWorksForTheSpecificHostAndPort() {

        final int wrongPort = 808;
        final int correctPort = 8080;

        assertFalse(ChatClient.connect("local", correctPort, "Pesho"));
        assertFalse(ChatClient.connect("localhost", wrongPort, "Pesho"));

        assertTrue(ChatClient.connect("localhost", correctPort, "Pesho"));
    }


    @Test
    public void testProcessServerCommands() {

        InputHandler inputHandler = mock(InputHandler.class);

//        doNothing().when(inputHandler)
//                .processServerCommand("send pesho ", null, new User("gosho", new Date()));


    }

}