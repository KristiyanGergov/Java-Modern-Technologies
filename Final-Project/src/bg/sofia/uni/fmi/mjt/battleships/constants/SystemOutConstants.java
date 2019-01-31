package bg.sofia.uni.fmi.mjt.battleships.constants;

public class SystemOutConstants {

    // C H A T    S E R V E R
    public static final String SERVER_RUNNING = "server is running on localhost:%s\n";

    public static final String CLIENT_CONNECTED_TO_SERVER = "A client connected to server %s\n";

    public static final String PORT_8080_TAKEN = "maybe another server is running on port 8080\n";

    public static final String PLAYER_CONNECTED = "%s connected\n";


    // C H A T    C L I E N T
    public static final String SUCCESSFULLY_CONNECTED = "=> connected to server as %s\n";

    public static final String CANNOT_CONNECT_TO_SERVER =
            "=> cannot connect to server on %s:%s, make sure that the server is started\n";


    // C L I E N T    R U N A B L E
    public static final String SOCKET_CLOSET = "client socket is closed, stop waiting for server messages\n";
}
