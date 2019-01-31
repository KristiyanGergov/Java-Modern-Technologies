package bg.sofia.uni.fmi.mjt.battleships.models;

import bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants;
import bg.sofia.uni.fmi.mjt.battleships.util.BoardCreator;
import bg.sofia.uni.fmi.mjt.battleships.util.Gun;
import bg.sofia.uni.fmi.mjt.battleships.util.ShipBuilder;

public class Player {

    private String username;
    private BoardCreator boardCreator;
    private Gun gun;
    private ShipBuilder shipBuilder;

    public Player(String username) {
        this.username = username;
        this.boardCreator = new BoardCreator(BoardConstants.ROWS, BoardConstants.COLUMNS);
        this.shipBuilder = new ShipBuilder(boardCreator.getBoard());
    }

    public String getUsername() {
        return username;
    }

}
