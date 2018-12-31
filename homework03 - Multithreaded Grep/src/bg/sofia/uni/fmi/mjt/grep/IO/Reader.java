package bg.sofia.uni.fmi.mjt.grep.IO;

import bg.sofia.uni.fmi.mjt.grep.constants.RegexGroups;
import bg.sofia.uni.fmi.mjt.grep.exceptions.UnknownLineTypeException;
import bg.sofia.uni.fmi.mjt.grep.validation.Regex;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Reader {

    private static boolean checkIfStringIsContained(String line, String word, String type) {

        switch (type) {

            case "":
                return line.contains(word);
            case "-w":
                return line.matches("^.*\\b(" + word + ")\\b.*$");
            case "-i":
                return line.toLowerCase().contains(word.toLowerCase());
            case "-w-i":
            case "-i-w":
                return line.toLowerCase().matches("^.*\\b(" + word.toLowerCase() + ")\\b.*$");

            default:
                throw new UnknownLineTypeException("Wrong line type provided!");
        }
    }


    public String readLineFromConsole() throws IOException {
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(System.in))) {
            return reader.readLine();
        }
    }


    public void read(Regex regex) throws IOException {

        int lineCount;

        try (Stream<Path> paths = Files.walk(Paths.get(regex.getMatcherGroupById(RegexGroups.PATH_TO_DIRECTORY_TREE)))) {

            for (Object entry :
                    paths.toArray()) {

                Path path = (Path) entry;

                File file = new File(path.toString());

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                    String line;

                    lineCount = 1;

                    while ((line = reader.readLine()) != null) {
                        boolean wordIsContained = checkIfStringIsContained(
                                line,
                                regex.getMatcherGroupById(RegexGroups.STRING_TO_FIND),
                                regex.getMatcherGroupById(RegexGroups.PARAMETERS));

                        if (wordIsContained) {

                            String output = path.toString()
                                    .substring(regex.getMatcherGroupById(RegexGroups.PATH_TO_DIRECTORY_TREE).length() + 1)
                                    + ":" + lineCount + ":" + line + "\n";

                            Writer writer = new Writer();
                            writer.write(output, regex);
                        }

                        lineCount++;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }

}