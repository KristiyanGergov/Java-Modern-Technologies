package bg.sofia.uni.fmi.mjt.grep;

import bg.sofia.uni.fmi.mjt.grep.utility.CommandExecutor;
import bg.sofia.uni.fmi.mjt.grep.validation.Regex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;

public class Main {

    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            //noinspection InfiniteLoopStatement
            while (true) {

                Matcher matcher = Regex.validateInput(reader.readLine());

                if (matcher.matches()) {

                    CommandExecutor commandExecutor = new CommandExecutor();
                    commandExecutor.execute(matcher);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}