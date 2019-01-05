package bg.sofia.uni.fmi.mjt.grep.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static Matcher validateInput(String command) {
        Pattern pattern = Pattern.compile("^grep\\s*([-w]*[-i]*)\\s(\\w+)\\s(.+)\\s(\\d+)\\s*(.*)$");

        return pattern.matcher(command);
    }

    public static boolean checkIfStringIsContained(String line, String word, String type) {

        switch (type) {

            case "-w":
                return line.matches("^.*\\b(" + word + ")\\b.*$");
            case "-i":
                return line.toLowerCase().contains(word.toLowerCase());
            case "-w-i":
            case "-i-w":
                return line.toLowerCase().matches("^.*\\b(" + word.toLowerCase() + ")\\b.*$");

            default:
                return line.contains(word);
        }
    }
}
