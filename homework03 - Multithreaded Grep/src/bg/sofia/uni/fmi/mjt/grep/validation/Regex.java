package bg.sofia.uni.fmi.mjt.grep.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    private Pattern pattern;
    private Matcher matcher;

    public Regex() {
        pattern = Pattern.compile("^grep\\s([-w]*[-i]*)\\s(.+)\\s(.+)\\s(.+)\\s*(.*)$");
    }

    public boolean validateInput(String command) {
        matcher = pattern.matcher(command);
        return matcher.matches();
    }

    public String getMatcherGroupById(int id) {
        return matcher.group(id);
    }

}
