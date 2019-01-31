package bg.sofia.uni.fmi.mjt.battleships.IO;

import java.io.PrintWriter;

public class OutputHandler {

    private PrintWriter writer;

    public OutputHandler(PrintWriter writer) {
        this.writer = writer;
    }

    public void print(String output) {
        writer.println(output);
    }

}
