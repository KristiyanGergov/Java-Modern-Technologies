package bg.sofia.uni.fmi.mjt.battleships.util;

import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.DEFAULT_BOARD_FIELD;

public class BoardCreator {

    private int rows;
    private int cols;
    private char[][] board;

    public BoardCreator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.createBoard();
    }

    private void createBoard() {

        board = new char[rows][cols];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                board[i][j] = DEFAULT_BOARD_FIELD;
    }

    public char[][] getBoard() {
        return board;
    }
}