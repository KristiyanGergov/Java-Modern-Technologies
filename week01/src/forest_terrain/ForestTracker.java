package forest_terrain;

public class ForestTracker {

    private char[][] copyArr(char[][] forest) {

        char[][] res = new char[forest.length][];

        for (int i = 0; i < forest.length; i++) {
            res[i] = new char[forest[i].length];
            if (forest[i].length >= 0) System.arraycopy(forest[i], 0, res[i], 0, forest[i].length);
        }
        return res;
    }

    public char[][] trackForestTerrain(char[][] forest, int years) {

        char[][] result = copyArr(forest);

        int requiredYears = 10;
        if (years < requiredYears)
            return forest;

        for (int n = 0; n < years / requiredYears; n++) {
            char[][] current = copyArr(result);
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[i].length; j++) {
                    char currChar = result[i][j];

                    switch (currChar) {
                        case '1':
                        case '2':
                        case '3':
                            current[i][j]++;
                            break;
                        case '4':
                            int count = 0;
                            for (int k = i - 1; k <= i + 1; k++) {
                                for (int l = j - 1; l <= j + 1; l++) {
                                    if (k < 0 ||
                                        k >= result.length ||
                                        l < 0 ||
                                        l >= result[i].length ||
                                        (k == i) && (l == j))
                                        continue;
                                    int countNeeded = 3;
                                    if (result[k][l] == '4') {
                                        if (++count == countNeeded) {
                                            current[i][j] = '3';
                                            break;
                                        }
                                    }
                                    if (count >= countNeeded)
                                        break;
                                }
                            }
                            break;
                    }
                }
            }
            result = copyArr(current);
        }

        return result;
    }
}
