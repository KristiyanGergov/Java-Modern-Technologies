package bg.sofia.uni.fmi.mjt.grep.utility;

import bg.sofia.uni.fmi.mjt.grep.IO.Reader;
import bg.sofia.uni.fmi.mjt.grep.constants.RegexGroups;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Stream;

public class CommandExecutor {

    public static void execute(Matcher matcher) {

        try (Stream<Path> paths =
                     Files.walk(Paths.get(matcher.group(RegexGroups.PATH_TO_DIRECTORY_TREE)))) {

            List<Reader> threads = new ArrayList<>();

            for (Object entry :
                    paths.toArray()) {

                File file = new File(entry.toString());

                threads.add(new Reader(file, matcher));
            }

            int numberOfThreads = Integer.parseInt(matcher.group(RegexGroups.NUMBER_OF_THREADS));

            for (int i = 0; i < threads.size() / numberOfThreads; i += numberOfThreads) {

                for (int j = i; j < numberOfThreads + i; j++) {
                    if (threads.get(j) != null) {
                        threads.get(j).start();
                    }
                    System.out.println(Thread.getAllStackTraces().keySet().size());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Invalid directory: " + RegexGroups.PATH_TO_DIRECTORY_TREE);
        }
    }

}
