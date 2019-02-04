package bg.sofia.uni.fmi.mjt.battleships.enums;

import java.io.Serializable;

public enum GameStatus implements Serializable {
    Pending("pending"),
    InProgress("in progress"),
    Finished("finished");

    private String name;

    GameStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
