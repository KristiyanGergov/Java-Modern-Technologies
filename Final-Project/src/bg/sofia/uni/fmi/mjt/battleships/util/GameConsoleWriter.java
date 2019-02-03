package bg.sofia.uni.fmi.mjt.battleships.util;

import bg.sofia.uni.fmi.mjt.battleships.models.Game;

import java.io.PrintWriter;
import java.util.Arrays;

import static bg.sofia.uni.fmi.mjt.battleships.constants.BoardConstants.*;
import static bg.sofia.uni.fmi.mjt.battleships.constants.ShipConstants.SHIP_FIELD;
import static bg.sofia.uni.fmi.mjt.battleships.constants.SystemOutConstants.PICK_YOUR_SHIPS;

public class GameConsoleWriter {
    private Game game;

    private PrintWriter writerPlayer1;
    private PrintWriter writerPlayer2;

    public GameConsoleWriter(Game game, PrintWriter writerPlayer1, PrintWriter writerPlayer2) {
        this.game = game;
        this.writerPlayer1 = writerPlayer1;
        this.writerPlayer2 = writerPlayer2;
    }

    private String getPrintableBoard(boolean isEnemyBoard, char[][] board) {

        String header = isEnemyBoard ? ENEMY_BOARD : YOUR_BOARD;

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
                if (isEnemyBoard && board[currentRowNum][i] == SHIP_FIELD)
                    builder.append(DEFAULT_BOARD_FIELD).append('|');
                else
                    builder.append(board[currentRowNum][i]).append('|');
            }

            builder.append("\n");
        }

        return builder.toString();
    }

    private void printBoard(PrintWriter writer, char[][] yourBoard, char[][] enemyBoard) {

        writer.println(getPrintableBoard(false, yourBoard));
        writer.println(getPrintableBoard(true, enemyBoard));

    }

    public void printBoards() {
        printBoard(writerPlayer1, game.getPlayer1().getBoard(), game.getPlayer2().getBoard());
        printBoard(writerPlayer2, game.getPlayer2().getBoard(), game.getPlayer1().getBoard());
    }

    public void printPickYourShips() {
        writerPlayer1.println(PICK_YOUR_SHIPS);
        writerPlayer2.println(PICK_YOUR_SHIPS);
    }
}
