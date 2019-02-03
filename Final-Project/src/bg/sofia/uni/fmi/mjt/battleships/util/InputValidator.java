package bg.sofia.uni.fmi.mjt.battleships.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bg.sofia.uni.fmi.mjt.battleships.constants.RegexConstants.*;

public class InputValidator {

    public static Matcher getMatcherConnect(String input) {
        Pattern pattern = Pattern.compile(CONNECT_PATTERN);
        return pattern.matcher(input);
    }

    public static Matcher getMatcherCommand(String input) {
        Pattern pattern = Pattern.compile(COMMAND_PATTERN);
        return pattern.matcher(input);
    }

    public static Matcher getMatcherCoordinatesBuildShip(String input) {
        Pattern pattern = Pattern.compile(COORDINATES_PATTERN_BUILD);
        return pattern.matcher(input);
    }

    public static Matcher getMatcherCoordinatesShootShip(String input) {
        Pattern pattern = Pattern.compile(COORDINATES_PATTERN_HIT);
        return pattern.matcher(input);
    }
}
