package bg.sofia.uni.fmi.mjt.battleships.constants;

public class RegexConstants {

    public static String COMMAND_PATTERN = "^([\\w-]+)\\s*([\\w-]*)$";

    public static String COORDINATES_PATTERN_BUILD = "^([a-j])(10|[1-9])\\s*-\\s*([a-j])(10|[1-9])$";

    public static String COORDINATES_PATTERN_HIT = "^(([a-j])(10|[1-9]))$";
}
