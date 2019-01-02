package bg.sofia.uni.fmi.mjt.grep.utility;

import bg.sofia.uni.fmi.mjt.grep.IO.Writer;
import bg.sofia.uni.fmi.mjt.grep.constants.RegexGroups;
import bg.sofia.uni.fmi.mjt.grep.validation.Regex;

import java.io.IOException;

public class CommandExecutor {

    private static boolean checkIfStringIsContained(String line, String word, String type) {

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


    public void execute(String line, Regex regex, String path, int lineCount) throws IOException {

        boolean wordIsContained = checkIfStringIsContained(
                line,
                regex.getMatcherGroupById(RegexGroups.STRING_TO_FIND),
                regex.getMatcherGroupById(RegexGroups.PARAMETERS));

        if (wordIsContained) {

            String output = path
                    .substring(regex.getMatcherGroupById(RegexGroups.PATH_TO_DIRECTORY_TREE).length() + 1)
                    + ":" + lineCount + ":" + line + "\n";

            Writer writer = new Writer();
            writer.write(output, regex);

        }

    }


}
