package bg.sofia.uni.fmi.mjt.grep.IO;

import bg.sofia.uni.fmi.mjt.grep.constants.RegexGroups;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;

public class Writer {

    private boolean append;

    public Writer(boolean append) {
        this.append = append;
    }

    public void write(String output, Matcher matcher) throws IOException {

        if (matcher.group(RegexGroups.PATH_TO_OUTPUT_FILE).equals("")) {
            System.out.print(output);
        } else {
            try (BufferedWriter writer =
                         new BufferedWriter(new FileWriter(
                                 matcher.group(RegexGroups.PATH_TO_OUTPUT_FILE), append))) {
                writer.write(output);
                writer.flush();
            }
        }
    }
}
