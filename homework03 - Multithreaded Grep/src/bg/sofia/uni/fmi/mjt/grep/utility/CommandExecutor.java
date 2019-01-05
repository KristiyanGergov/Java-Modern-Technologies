package bg.sofia.uni.fmi.mjt.grep.utility;

import bg.sofia.uni.fmi.mjt.grep.IO.Reader;
import bg.sofia.uni.fmi.mjt.grep.constants.RegexGroups;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.stream.Stream;

public class CommandExecutor {

    public static void execute(Matcher matcher) {

        try (Stream<Path> paths =
                     Files.walk(Paths.get(matcher.group(RegexGroups.PATH_TO_DIRECTORY_TREE)))) {

            List<File> files = new LinkedList<>();

            for (Object entry :
                    paths.toArray()) {
                files.add(new File(entry.toString()));
            }

            int numberOfThreads = Integer.parseInt(matcher.group(RegexGroups.NUMBER_OF_THREADS));

            final BlockingQueue<File> queue = new ArrayBlockingQueue<>(files.size());
            queue.addAll(files);

            ExecutorService pool = Executors.newFixedThreadPool(numberOfThreads);

            for (int i = 0; i < numberOfThreads; i++) {
                Runnable runnable = () -> {
                    File file;

                    while ((file = queue.poll()) != null) {
                        Reader reader = new Reader(file, matcher);
                        reader.readFile();
                    }
                };
                pool.execute(runnable);
            }

            pool.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Invalid directory: " + RegexGroups.PATH_TO_DIRECTORY_TREE);
        }
    }

}
