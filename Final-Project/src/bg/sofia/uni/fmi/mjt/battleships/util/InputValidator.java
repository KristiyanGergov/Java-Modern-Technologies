package bg.sofia.uni.fmi.mjt.battleships.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bg.sofia.uni.fmi.mjt.battleships.constants.RegexConstants.COMMAND_PATTERN;
import static bg.sofia.uni.fmi.mjt.battleships.constants.RegexConstants.CONNECT_PATTERN;

public class InputValidator {

    public static Matcher getMatcherConnect(String input) {
        Pattern pattern = Pattern.compile(CONNECT_PATTERN);
        return pattern.matcher(input);
    }

    public static Matcher getMatcherCommand(String input) {
        Pattern pattern = Pattern.compile(COMMAND_PATTERN);
        return pattern.matcher(input);
    }

}