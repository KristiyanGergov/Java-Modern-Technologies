package bg.sofia.uni.fmi.mjt.battleships.util;

import bg.sofia.uni.fmi.mjt.battleships.models.Game;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.*;

public class GameConsoleWriter {
    private Game game;

    public GameConsoleWriter(Game game) {
        this.game = game;
    }

    private String getPrintableBoard(String header, char[][] board) {

        StringBuilder builder = new StringBuilder();

        builder.append("\n");

        for (int i = 0; i < ((board.length * 2 - 1) - header.length()) / 2; i++) {
            builder.append(" ");
        }
        builder.append("   ");
        builder.append(header).append("\n");
        builder.append("   ");

        var sortedCols = COLUMNS_CELLS.keySet().toArray();
        Arrays.sort(sortedCols);

        for (var col :
                sortedCols) {
            builder.append(col).append(" ");
        }

        builder.append("\n");

        builder.append("   ");
        for (Object ignored : sortedCols) {
            builder.append(DEFAULT_BOARD_FIELD).append(" ");
        }

        builder.append("\n");

        var sortedRows = ROWS_CELLS.keySet().toArray(new Character[]{});
        Arrays.sort(sortedRows);

        for (var row :
                sortedRows) {
            builder.append(row).append(" ").append("|");

            int currentRowNum = ROWS_CELLS.get(row);

            for (int i = 0; i < board[currentRowNum].length; i++) {
                builder.append(board[currentRowNum][i]).append('|');
            }

            builder.append("\n");
        }

        return builder.toString();
    }

    private void printBoard(OutputStream stream, char[][] yourBoard, char[][] enemyBoard) {

        PrintWriter writer = new PrintWriter(stream, true);
        writer.println(getPrintableBoard(YOUR_BOARD, yourBoard));
        writer.println(getPrintableBoard(ENEMY_BOARD, enemyBoard));

    }

    public void printBoards() {
        try {
            printBoard(game.getPlayer1().getSocket().getOutputStream(), game.getPlayer1().getBoard(), game.getPlayer2().getBoard());
            printBoard(game.getPlayer2().getSocket().getOutputStream(), game.getPlayer2().getBoard(), game.getPlayer1().getBoard());
        } catch (IOException e) {
            e.printStackTrace();
            //todo
        }
    }
}
