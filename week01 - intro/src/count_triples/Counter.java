package count_triples;

public class Counter {

    public int countTriples(String str) {
        int counter = 0;
        for (int i = 0; i < str.length() - 2; i++)
            if (str.charAt(i) == str.charAt(i + 1) && str.charAt(i + 1) == str.charAt(i + 2)) counter++;
        return counter;
    }
}