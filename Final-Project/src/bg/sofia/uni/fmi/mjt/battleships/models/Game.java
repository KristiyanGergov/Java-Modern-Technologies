package bg.sofia.uni.fmi.mjt.battleships.models;

import bg.sofia.uni.fmi.mjt.battleships.enums.GameStatus;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.GameFullException;

import static bg.sofia.uni.fmi.mjt.battleships.constants.ExceptionConstants.FULL_GAME;

public class Game {

    private Player player1;
    private Player player2;
    private boolean full;
    private GameStatus status;

    public Game(Player player1) {
        this.player1 = player1;
        this.status = GameStatus.Pending;
    }

    public void join(Player player) throws GameFullException {
        if (this.player2 == null) {
            this.player2 = player;
            this.full = true;
        } else
            throw new GameFullException(FULL_GAME);
    }

    public boolean isFull() {
        return full;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public Player getPlayer2() {
        return player2;
    }

}
