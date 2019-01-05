package bg.sofia.uni.fmi.mjt.grep.IO;

import bg.sofia.uni.fmi.mjt.grep.constants.RegexGroups;
import bg.sofia.uni.fmi.mjt.grep.validation.Regex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;

public class Reader {

    private File file;
    private Matcher matcher;

    public Reader(File file, Matcher matcher) {
        this.file = file;
        this.matcher = matcher;
    }

    public void readFile() {

        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            int lineCount = 1;

            while ((line = reader.readLine()) != null) {

                boolean wordIsContained = Regex.checkIfStringIsContained(
                        line,
                        matcher.group(RegexGroups.STRING_TO_FIND),
                        matcher.group(RegexGroups.PARAMETERS));

                if (wordIsContained) {

                    String output = file.getAbsolutePath()
                            .substring(matcher.group(RegexGroups.PATH_TO_DIRECTORY_TREE).length() + 1)
                            + ":" + lineCount + ":" + line + "\n";

                    Writer writer = new Writer();
                    writer.write(output, matcher);
                }

                lineCount++;
            }

        } catch (IOException ignore) {
            //Ignore it because it is a file that cannot be readFile
        }

    }


}