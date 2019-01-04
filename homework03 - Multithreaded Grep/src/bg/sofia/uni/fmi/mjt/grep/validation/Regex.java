package bg.sofia.uni.fmi.mjt.grep.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static Matcher validateInput(String command) {
        Pattern pattern = Pattern.compile("^grep\\s*([-w]*[-i]*)\\s(\\w+)\\s(.+)\\s(\\d+)\\s*(.*)$");

        return pattern.matcher(command);
    }
}
