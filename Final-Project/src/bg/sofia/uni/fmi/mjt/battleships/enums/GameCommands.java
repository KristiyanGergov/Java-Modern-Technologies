package bg.sofia.uni.fmi.mjt.battleships.enums;

public enum GameCommands {

    CREATE("create-game"),
    JOIN("join-game"),
    SAVE("save-game"),
    SAVED("saved-games"),
    LOAD("load-game"),
    DELETE("delete-game");

    private String command;

    GameCommands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
