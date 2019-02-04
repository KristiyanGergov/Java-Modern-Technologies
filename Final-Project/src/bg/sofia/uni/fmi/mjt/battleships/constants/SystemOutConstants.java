package bg.sofia.uni.fmi.mjt.battleships.constants;

public class SystemOutConstants {

    // C H A T    S E R V E R
    public static final String SERVER_RUNNING = "server is running on localhost:%s\n";

    public static final String CLIENT_CONNECTED_TO_SERVER = "A client connected to server %s\n";

    public static final String PORT_8080_TAKEN = "maybe another server is running on port 8080\n";

    public static final String PLAYER_CONNECTED = "%s connected\n";


    // C H A T    C L I E N T
    public static final String SUCCESSFULLY_CONNECTED = "connected to server as %s\n";

    public static final String CANNOT_CONNECT_TO_SERVER =
            "cannot connect to server on %s:%s, make sure that the server is started\n";

    public static final String CONNECT_COMMANDS = "To connect to server enter \"connect username\".";

    // C L I E N T    R U N A B L E
    public static final String SOCKET_CLOSET = "client socket is closed, stop waiting for server messages\n";

    public static final String CREATED_GAME = "Created game \"%s\", players 1/2";

    public static final String DELETED_GAME = "Deleted game \"%s\".";

    public static final String UNABLE_DELETE_GAME = "Couldn't find \"%s\".";

    public static final String JOINED_GAME = "Joined game \"%s\".";

    public static final String AVAILABLE_COMMANDS = "\nAvailable commands:\n" +
            "\tcreate-game <game-name>\n" +
            "\tjoin-game [<game-name>]\n" +
            "\tsaved-games\n" +
            "\tload-game <game-name>\n" +
            "\tload-games\n" +
            "\tdelete-game <game-name>\n" +
            "\tlist-games\n";

    public static final String AVAILABLE_COMMANDS_AFTER_JOINING_GAME = "\nAvailable commands:\n" +
            "\tleave-game\n" +
            "\tsave-game\n" +
            "\tbuild [A-J][1-10]-[A-J][1-10] (ship location)\n";

    public static final String MENU = "menu> ";

    public static final String NO_GAMES_AVAILABLE = "No games currently available. Create one.";

    public static final String LIST_GAMES_HEADER = "| NAME         | CREATOR    | STATUS      | PLAYERS |\n" +
            "|--------------+------------+-------------+---------|";

    public static final String LIST_GAMES_BODY = "| %s | %s | %s | %s |";

    public static final String PLAYERS_NUMBER = "%d/2";

    public static final int LIST_GAMES_NAME_LENGTH = 12;
    public static final int LIST_GAMES_CREATOR_LENGTH = 10;
    public static final int LIST_GAMES_STATUS_LENGTH = 11;
    public static final int LIST_GAMES_PLAYERS_LENGTH = 7;

    public static final String PICK_YOUR_SHIPS = "It's almost battle time!\n" +
            "You only need to choose your ships.\n" +
            "\tHere are the rules:\n" +
            "\t  • Ships can be only vertical and horizontal\n" +
            "\t  • You can have maximum of 10 ships where there are different sizes of the ships: \n" +
            "\t     • 1 ship, consisting of 5 cells\n" +
            "\t     • 2 ships, consisting of 4 cells\n" +
            "\t     • 3 ships, consisting of 3 cells\n" +
            "\t     • 4 ships, consisting of 2 cells\n" +
            "To pick ship just enter \"build\" + its \"start coordinates\"-\"end coordinates\"\n" +
            "(e.g. build A4-A6, build J8-J10,build B7-B9 etc.)\n" +
            "Good Luck!\n";

    public static final String NOTIFIED_WHEN_JOIN = "You will get notified when somebody joins the game!";

    public static final String SHIPS_LEFT = "Ship successfully build!\n" +
            "Ships left you can build:\n" +
            "   • %d ship/s, consisting of 5 cells\n" +
            "   • %d ship/s, consisting of 4 cells\n" +
            "   • %d ship/s, consisting of 3 cells\n" +
            "   • %d ship/s, consisting of 2 cells\n";

    public static final String SUCCESSFULLY_BUILD_ALL_SHIPS = "You have successfully build all of your ships!\n" +
            "Your opponent is almost ready...\n";

    public static final String YOUR_OPPONENT_STILL_NOT_READY = "Please wait until your opponent is ready!\n";

    public static final String BOTH_PLAYER_HAS_BUILT_ALL_SHIPS = "You and your opponent has successfully " +
            "built your ships so it's time for battle!\n" +
            "Here are the rules:\n" +
            "   • first start the player that joined the game (new bride new luck)\n" +
            "   • after he pick make the shot he get notified whether he had hit a ship and if yes whether he has destroyed it\n" +
            "   • then the turn goes to the other player\n" +
            "To make a shot just enter \"hit\" + the coordinates (e.g. hit A5)";

    public static final String DESTROYED_SHIP_YOU = "You have successfully destroyed your opponent ship!";

    public static final String DESTROYED_SHIP_OPPONENT = "Your ship have been destroyed.";

    public static final String NOT_HIT_SHIP_YOU = "You didn't hit any of your opponent ships.";

    public static final String NOT_HIT_SHIP_OPPONENT = "Your opponent didn't hit any of your ships.";

    public static final String HIT_SHIP_YOU = "You have successfully hit one of your opponent's ships!";

    public static final String HIT_SHIP_OPPONENT = "Your ship have been hit.";

    public static final String YOUR_TURN = "It's your turn now.";

    public static final String YOUR_OPPONENT_TURN = "It's your opponent turn now.";

    public static final String LEFT_GAME = "You left game \"%s\"";

    public static final String NOT_CURRENTLY_IN_A_GAME = "You are not currently in a game!";

}
