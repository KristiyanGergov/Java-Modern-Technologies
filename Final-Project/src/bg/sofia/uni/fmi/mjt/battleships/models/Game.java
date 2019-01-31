package bg.sofia.uni.fmi.mjt.battleships.models;

import bg.sofia.uni.fmi.mjt.battleships.exceptions.GameFullException;

import static bg.sofia.uni.fmi.mjt.battleships.constants.ExceptionConstants.FULL_GAME;

public class Game {

    private String name;

    private Player player1;
    private Player player2;

    public Game(String name, Player player1) {
        this.name = name;
        this.player1 = player1;
    }

    public void join(Player player) throws GameFullException {
        if (this.player2 == null)
            this.player2 = player;

        throw new GameFullException(FULL_GAME);
    }
}
