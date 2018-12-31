package bg.sofia.uni.fmi.mjt.grep;

import bg.sofia.uni.fmi.mjt.grep.IO.Reader;
import bg.sofia.uni.fmi.mjt.grep.validation.Regex;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {

            while (true) {

                Reader reader = new Reader();

                String command = reader.readLineFromConsole();

                Regex regex = new Regex();

                if (regex.validateInput(command)) {
                    reader.read(regex);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}