package bg.sofia.uni.fmi.mjt.grep;

import bg.sofia.uni.fmi.mjt.grep.utility.CommandExecutor;
import bg.sofia.uni.fmi.mjt.grep.validation.InputHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;

public class Main {

    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            //noinspection InfiniteLoopStatement
            while (true) {

                Matcher matcher = InputHandler.validateCommand(reader.readLine());

                if (matcher.matches())
                    CommandExecutor.execute(matcher);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}