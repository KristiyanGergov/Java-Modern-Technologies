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


    //

    public static final String CREATED_GAME = "Created game \"%s\", players 1/2";

    public static final String DELETED_GAME = "Deleted game \"%s\".";

    public static final String JOINED_GAME = "Joined game \"%s\".";

    public static final String AVAILABLE_COMMANDS = "\nAvailable commands:\n" +
            "\tcreate-game <game-name>\n" +
            "\tjoin-game [<game-name>]\n" +
            "\tsaved-games\n" +
            "\tload-game <game-name>\n" +
            "\tdelete-game\n";

    public static final String MENU = "menu> ";

    public static final String NO_GAMES_AVAILABLE = "No games currently available. Create one.";

}
