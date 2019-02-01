package bg.sofia.uni.fmi.mjt.battleships.constants;

import java.util.Map;

public class BoardConstants {

    public final static int ROWS = 10;

    public final static int COLUMNS = 10;

    public final static char DEFAULT_BOARD_FIELD = '_';

    public final static char START_ROW = 'A';

    public final static char END_ROW = 'J';

    public final static int START_COL = 1;

    public final static int END_COL = 10;

    public final static Map<Character, Integer> ROWS_CELLS = Map.of(
            'A', 0,
            'B', 1,
            'C', 2,
            'D', 3,
            'E', 4,
            'F', 5,
            'G', 6,
            'H', 7,
            'I', 8,
            'J', 9
    );


    public final static Map<Integer, Integer> COLUMNS_CELLS = Map.of(
            1, 0,
            2, 1,
            3, 2,
            4, 3,
            5, 4,
            6, 5,
            7, 6,
            8, 7,
            9, 8,
            10, 9
    );


    public final static String YOUR_BOARD = "YOUR BOARD";
    public final static String ENEMY_BOARD = "ENEMY BOARD";


}