package bg.sofia.uni.fmi.mjt.battleships.IO;

import java.io.PrintWriter;

import static bg.sofia.uni.fmi.mjt.battleships.constants.SystemOutConstants.MENU;

public class OutputHandler {

    private PrintWriter writer;

    public OutputHandler(PrintWriter writer) {
        this.writer = writer;
    }

    public void print(String output) {
        writer.println(output);
    }

    public void printCommands(String output, String argument) {
        if (argument == null)
            writer.println(output);
        else
            writer.println(String.format(output, argument));

        writer.write(MENU);
        writer.flush();
    }
}
