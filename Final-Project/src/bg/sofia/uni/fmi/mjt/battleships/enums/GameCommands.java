package bg.sofia.uni.fmi.mjt.battleships.enums;

public enum GameCommands {

    CREATE("create-game"),
    JOIN("join-game"),
    SAVE("save-game"),
    SAVED("saved-games"),
    LOAD("load-game"),
    DELETE("delete-game"),
    LEAVE("leave-game"),
    LIST("list-games");

    private String command;

    GameCommands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
