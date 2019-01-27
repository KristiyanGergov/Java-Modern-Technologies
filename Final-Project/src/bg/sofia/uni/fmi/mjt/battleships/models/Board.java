package bg.sofia.uni.fmi.mjt.battleships.models;

import bg.sofia.uni.fmi.mjt.battleships.util.BoardCreator;

import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.COLUMNS;
import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.ROWS;

public class Board {

    private BoardCreator boardCreator;

    public Board() {
        this.boardCreator = new BoardCreator(ROWS, COLUMNS);
    }



}