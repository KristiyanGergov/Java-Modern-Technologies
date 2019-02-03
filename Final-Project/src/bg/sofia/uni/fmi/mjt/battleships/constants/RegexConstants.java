package bg.sofia.uni.fmi.mjt.battleships.constants;

public class RegexConstants {

    public static String CONNECT_PATTERN = "^\\$\\sjava\\srun-client.java\\s--username\\s(.+)$";

    public static String COMMAND_PATTERN = "^([\\w-]+)\\s*([\\w-]*)$";

    public static String COORDINATES_PATTERN_BUILD = "^build\\s+([a-j])(10|[1-9])\\s*-\\s*([a-j])(10|[1-9])$";

    public static String COORDINATES_PATTERN_HIT = "^hit\\s+(([a-j])(10|[1-9]))$";
}
