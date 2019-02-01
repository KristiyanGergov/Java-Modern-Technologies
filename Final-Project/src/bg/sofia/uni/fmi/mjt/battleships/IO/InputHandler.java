package bg.sofia.uni.fmi.mjt.battleships.IO;

import bg.sofia.uni.fmi.mjt.battleships.client.GameClient;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.AlreadyJoinedGameException;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.NoGamesAvailableException;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.NotConnectedException;
import bg.sofia.uni.fmi.mjt.battleships.models.Player;
import bg.sofia.uni.fmi.mjt.battleships.server.GameServer;
import bg.sofia.uni.fmi.mjt.battleships.util.CommandExecutor;
import bg.sofia.uni.fmi.mjt.battleships.util.InputValidator;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;

import static bg.sofia.uni.fmi.mjt.battleships.constants.SystemOutConstants.AVAILABLE_COMMANDS;
import static bg.sofia.uni.fmi.mjt.battleships.constants.SystemOutConstants.MENU;
import static bg.sofia.uni.fmi.mjt.battleships.enums.GameCommands.*;

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

                    if (connected) {
                        System.out.println(AVAILABLE_COMMANDS);
                        System.out.print(MENU);
                    }
                } else if (outputHandler != null) {
                    if (connected) {
                        print(input);
                    }
                } else
                    throw new NotConnectedException("You need to connect to the server first!");
            }
        }
    }

    public void processServerCommand(String commandInput, PrintWriter writer, GameServer server, Player player) throws NoGamesAvailableException, AlreadyJoinedGameException {

        CommandExecutor executor = new CommandExecutor(writer, server);
        Matcher matcher = InputValidator.getMatcherCommand(commandInput);

        if (matcher.matches()) {

            String command = matcher.group(1);
            System.out.println(command);

            if (command.equals(CREATE.getCommand())) {
                executor.create(matcher.group(2), player);
            } else if (command.equals(DELETE.getCommand())) {
                executor.delete(matcher.group(2));
            } else if (command.equals(JOIN.getCommand())) {
                executor.join(matcher.group(2), player);
            } else if (command.equals(SAVE.getCommand())) {
                //todo
            } else if (command.equals(LOAD.getCommand())) {
                //todo
            } else if (command.equals(SAVED.getCommand())) {
                //todo
            } else {
                writer.println(AVAILABLE_COMMANDS);
            }

        }

    }


}
