package bg.sofia.uni.fmi.mjt.chat;

import bg.sofia.uni.fmi.mjt.chat.client.ChatClient;
import bg.sofia.uni.fmi.mjt.chat.models.User;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class ChatServerTest {

    @BeforeClass
    public static void init() {
        new Thread(new ChatServerRunnable()).start();
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
}