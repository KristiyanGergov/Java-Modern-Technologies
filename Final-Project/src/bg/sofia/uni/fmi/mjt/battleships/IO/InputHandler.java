package bg.sofia.uni.fmi.mjt.battleships.IO;

import bg.sofia.uni.fmi.mjt.battleships.client.GameClient;
import bg.sofia.uni.fmi.mjt.battleships.enums.GameCommands;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.NotConnectedException;
import bg.sofia.uni.fmi.mjt.battleships.models.Game;
import bg.sofia.uni.fmi.mjt.battleships.models.Player;
import bg.sofia.uni.fmi.mjt.battleships.util.InputValidator;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;

public class InputHandler {

    private boolean connected;
    private static OutputHandler outputHandler;

    public static void setOutputHandler(PrintWriter writer) {
        outputHandler = new OutputHandler(writer);
    }

    public static void print(String input) {
        outputHandler.print(input);
    }

    public void processClientCommand() throws NotConnectedException {

        try (Scanner scanner = new Scanner(System.in)) {

            while (true) {
                String input = scanner.nextLine();

                Matcher matcher = InputValidator.getMatcherConnect(input);

                if (matcher.matches()) {
                    String username = matcher.group(1);
                    connected = GameClient.connect(username);

                } else if (outputHandler != null) {
                    if (connected)
                        print(input);
                    else
                        throw new NotConnectedException("You need to connect to the server first!");
                }
            }
        }
    }

    public void processServerCommand(String commandInput, PrintWriter writer, Player player) {

        Matcher matcher = InputValidator.getMatcherConnect(commandInput);

        if (matcher.matches()) {

            String command = matcher.group(1);
            System.out.println(command);

            if (command.equals(GameCommands.CREATE.getCommand())) {
                Game game = new Game(matcher.group(2), player);
            } else if (command.equals(GameCommands.DELETE.getCommand())) {

            } else if (command.equals(GameCommands.JOIN.getCommand())) {

            } else if (command.equals(GameCommands.LOAD.getCommand())) {

            } else if (command.equals(GameCommands.SAVE.getCommand())) {

            } else if (command.equals(GameCommands.SAVED.getCommand())) {

            }

        }

    }


}
