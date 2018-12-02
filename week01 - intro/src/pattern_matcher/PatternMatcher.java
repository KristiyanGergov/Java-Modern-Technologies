package pattern_matcher;

public class PatternMatcher {

    public static boolean match(String s, String p) {

        if (!(s.contains("*") && s.contains("?"))) {
            return s.contains(p);
        }

        return false;
    }
}
