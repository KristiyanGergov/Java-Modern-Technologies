package bg.sofia.uni.fmi.mjt.grep.validation;

import bg.sofia.uni.fmi.mjt.grep.constants.SearchGroups;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler {

    public static Matcher validateCommand(String command) {
        Pattern pattern = Pattern.compile("^grep\\s*([-w]*[-i]*)\\s(\\w+)\\s(.+)\\s(\\d+)\\s*(.*)$");

        return pattern.matcher(command);
    }

    public static boolean wordIsContained(String line, String word, String type) {

        switch (type) {

            case SearchGroups.WHOLE_WORDS:
                return line.matches("^.*\\b(" + word + ")\\b.*$");
            case SearchGroups.CASE_INSENSITIVE:
                return line.toLowerCase().contains(word.toLowerCase());
            case SearchGroups.WHOLE_WORDS_CASE_INSENSITIVE:
                return line.toLowerCase().matches("^.*\\b(" + word.toLowerCase() + ")\\b.*$");

            default:
                return line.contains(word);
        }
    }
}
