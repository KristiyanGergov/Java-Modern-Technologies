package bg.sofia.uni.fmi.mjt.grep.IO;

import bg.sofia.uni.fmi.mjt.grep.constants.RegexGroups;
import bg.sofia.uni.fmi.mjt.grep.utility.CommandExecutor;
import bg.sofia.uni.fmi.mjt.grep.validation.Regex;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Reader {

    public String readLineFromConsole() throws IOException {
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(System.in))) {
            return reader.readLine();
        }
    }


    public void read(Regex regex) throws IOException {

        int lineCount;

        CommandExecutor executor = new CommandExecutor();

        try (Stream<Path> paths =
                     Files.walk(Paths.get(regex.getMatcherGroupById(RegexGroups.PATH_TO_DIRECTORY_TREE)))) {

            for (Object entry :
                    paths.toArray()) {

                File file = new File(entry.toString());

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                    String line;

                    lineCount = 1;

                    while ((line = reader.readLine()) != null) {
                        executor.execute(line, regex, entry.toString(), lineCount);
                        lineCount++;
                    }

                } catch (IOException ignore) {
                }
            }

        }

    }

}