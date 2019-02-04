package bg.sofia.uni.fmi.mjt.battleships.models;

import bg.sofia.uni.fmi.mjt.battleships.enums.GameStatus;
import bg.sofia.uni.fmi.mjt.battleships.util.Gun;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

public class Game implements Serializable {

    private String name;
    private Player player1;
    private Player player2;
    private String creator;
    private GameStatus status;

    public Game(Player player1, String name) {
        this.creator = player1.getUsername();
        this.player1 = player1;
        this.player1.setGame(this);
        this.name = name;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public GameStatus getStatus() {
        if (status == GameStatus.Finished)
            return status;
        if (isFull())
            return GameStatus.InProgress;
        return GameStatus.Pending;
    }

    public int getPlayersNumber() {
        if (player2 == null && player1 == null)
            return 0;
        if (player1 != null && player2 != null)
            return 2;
        else return 1;
    }

    public boolean join(Player player) {
        if (player1 == null) {
            player1 = player;
            player1.setGame(this);
            return true;
        } else if (this.player2 == null) {
            this.player2 = player;
            this.player2.setOnTurn(true);
            this.player2.setGame(this);

            this.setGuns();
            return true;
        }
        return false;
    }

    public void printMessage(String message, boolean playerOnTurn) throws IOException {

        if (playerOnTurn) {
            new PrintWriter(getPlayerOnTurn().getSocket().getOutputStream(), true).println(message);
        } else {
            new PrintWriter(getPlayerNotOnTurn().getSocket().getOutputStream(), true).println(message);
        }
    }

    private Player getPlayerOnTurn() {
        return player1.isOnTurn() ? player1 : player2;
    }

    private Player getPlayerNotOnTurn() {
        return !player1.isOnTurn() ? player1 : player2;
    }

    private void setGuns() {
        player1.setGun(new Gun(player2.getBoard(), player2.getShipBuilder().getShips()));
        player2.setGun(new Gun(player1.getBoard(), player1.getShipBuilder().getShips()));
    }

    public boolean isFull() {
        return player1 != null && player2 != null;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public boolean bothPlayersHasBuildTheirShips() {
        return player1.hasBuildAllShips() && player2.hasBuildAllShips();
    }

    public PrintWriter getPlayer1PrintWriter() throws IOException {
        return new PrintWriter(player1.getSocket().getOutputStream(), true);
    }

    public PrintWriter getPlayer2PrintWriter() throws IOException {
        return new PrintWriter(player2.getSocket().getOutputStream(), true);
    }

    public String getName() {
        return name;
    }

    public void leave(Player player) {

        if (player.isOnTurn()) {
            if (player1.isOnTurn()) {
                player1.reset();
                player1 = null;
            } else {
                player2.reset();
                player2 = null;
            }
        } else {
            if (!player1.isOnTurn()) {
                player1.reset();
                player1 = null;
            } else {
                player2.reset();
                player2 = null;
            }
        }

    }

    public void switchTurns() {
        if (player1.isOnTurn()) {
            player1.setOnTurn(false);
            player2.setOnTurn(true);
        } else {
            player1.setOnTurn(true);
            player2.setOnTurn(false);
        }
    }

    @Override
    public boolean equals(Object game) {
        if (game instanceof Game)
            return name.equals(((Game) game).name);
        return false;
    }

}
