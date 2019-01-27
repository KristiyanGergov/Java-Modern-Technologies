package bg.sofia.uni.fmi.mjt.battleships.util;

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
                board[i][j] = '-';
    }

    public char[][] getBoard() {
        return board;
    }
}