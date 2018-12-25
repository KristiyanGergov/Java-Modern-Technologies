package bg.sofia.uni.fmi.mjt.grep;

import bg.sofia.uni.fmi.mjt.grep.exceptions.UnknownLineTypeException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

    // R E G E X    G R O U P S
    private static final int PARAMETERS = 1;
    private static final int STRING_TO_FIND = 2;
    private static final int PATH_TO_DIRECTORY_TREE = 3;
    private static final int NUMBER_OF_THREADS = 4;
    private static final int PATH_TO_OUTPUT_FILE = 5;


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


    public static void main(String[] args) {

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(System.in))) {

            while (true) {

                String command = reader.readLine();

                Pattern pattern = Pattern.compile("^grep\\s([-w]*[-i]*)\\s(.+)\\s(.+)\\s(.+)\\s*(.*)$");
                Matcher matcher = pattern.matcher(command);

                if (matcher.matches()) {

                    int lineCount;

                    try (Stream<Path> paths = Files.walk(Paths.get(matcher.group(PATH_TO_DIRECTORY_TREE)))) {

                        for (Object entry :
                                paths.toArray()) {

                            Path path = (Path) entry;

                            File file = new File(path.toString());

                            BufferedReader reader2;

                            try {
                                reader2 = new BufferedReader(new FileReader(file));

                            } catch (IOException e) {
                                continue;
                            }

                            String line;

                            lineCount = 1;

                            while ((line = reader2.readLine()) != null) {
                                boolean result = checkIfStringIsContained(line, matcher.group(STRING_TO_FIND), matcher.group(PARAMETERS));

                                if (result) {

                                    String output = path.toString().substring(matcher.group(PATH_TO_DIRECTORY_TREE).length() + 1)
                                            + ":" + lineCount + ":" + line + "\n";

                                    if (matcher.group(PATH_TO_OUTPUT_FILE).equals("")) {
                                        System.out.print(output);
                                    } else {
                                        try (BufferedWriter writer =
                                                     new BufferedWriter(new FileWriter(matcher.group(PATH_TO_OUTPUT_FILE)))) {
                                            writer.write(output);
                                            writer.flush();
                                        }
                                    }

                                }

                                lineCount++;
                            }
                        }

                    }


                }

            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Please enter a valid statement!");
        }
    }
}