package bg.sofia.uni.fmi.mjt.grep.IO;

import bg.sofia.uni.fmi.mjt.grep.constants.RegexGroups;
import bg.sofia.uni.fmi.mjt.grep.validation.Regex;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    public void write(String output, Regex regex) throws IOException {

        if (regex.getMatcherGroupById(RegexGroups.PATH_TO_OUTPUT_FILE).equals("")) {
            System.out.print(output);
        } else {
            try (BufferedWriter writer =
                         new BufferedWriter(new FileWriter(
                                 regex.getMatcherGroupById(RegexGroups.PATH_TO_OUTPUT_FILE), true))) {
                writer.write(output);
                writer.flush();
            }
        }
    }
}
