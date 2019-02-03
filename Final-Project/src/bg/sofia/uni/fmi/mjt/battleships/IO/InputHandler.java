package bg.sofia.uni.fmi.mjt.battleships.IO;

import bg.sofia.uni.fmi.mjt.battleships.client.GameClient;
import bg.sofia.uni.fmi.mjt.battleships.enums.GameStatus;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.NotConnectedException;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongCoordinatesException;
import bg.sofia.uni.fmi.mjt.battleships.models.Player;
import bg.sofia.uni.fmi.mjt.battleships.server.GameServer;
import bg.sofia.uni.fmi.mjt.battleships.util.CommandExecutor;
import bg.sofia.uni.fmi.mjt.battleships.util.InputValidator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;

import static bg.sofia.uni.fmi.mjt.battleships.constants.ExceptionConstants.INVALID_COMMAND;
import static bg.sofia.uni.fmi.mjt.battleships.constants.SystemOutConstants.*;
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

                Matcher matcher = InputValidator.getMatcherCommand(input);

                if (matcher.matches()) {
                    String command = matcher.group(1);
                    String commandParams = matcher.group(2);

                    if (command.equals(CONNECT.getCommand())) {

                        connected = GameClient.connect(commandParams);

                        if (connected)
                            System.out.println(AVAILABLE_COMMANDS);
                    } else if (outputHandler != null) {
                        if (connected) {
                            print(input);
                        }
                    } else
                        throw new NotConnectedException("You need to connect to the server first!");
                }
            }
        }
    }


    public void processServerCommand(String input, final PrintWriter writer, final GameServer server, final Player player)
            throws InvalidCommandException, WrongCoordinatesException, IOException {

        final boolean hasGameStarted = player.getGame() != null && player.getGame().getStatus() == GameStatus.InProgress;
        final CommandExecutor executor = new CommandExecutor(writer, server);
        input = input.toLowerCase();

        if (player.hasBuildAllShips()) {
            processServerCommandShootShips(player, input, writer, executor);
        } else if (hasGameStarted) {
            processServerCommandGameStarted(player, input, executor);
        } else {
            processServerCommandGameNotStarted(player, input, writer, executor);
        }


    }

    private void processServerCommandGameStarted(Player player, String input, CommandExecutor executor)
            throws WrongCoordinatesException, IOException, InvalidCommandException {

        Matcher matcher = InputValidator.getMatcherCommand(input);

        if (matcher.matches()) {
            String command = matcher.group(1);
            String commandParams = matcher.group(2);

            if (command.equals(LEAVE.getCommand())) {
                executor.leave();
            } else {

                matcher = InputValidator.getMatcherCoordinatesBuildShip(commandParams);

                if (matcher.matches()) {
                    executor.buildShip(matcher, player);
                } else {
                    throw new InvalidCommandException(
                            String.format(INVALID_COMMAND, input, AVAILABLE_COMMANDS_AFTER_JOINING_GAME));
                }
            }
        }
    }

    private void processServerCommandShootShips(Player player, String input, PrintWriter writer, CommandExecutor executor)
            throws IOException, WrongCoordinatesException, InvalidCommandException {
        if (player.getGame().bothPlayersHasBuildTheirShips()) {

            Matcher matcher = InputValidator.getMatcherCommand(input);

            if (matcher.matches()) {

                final String command = matcher.group(1);
                final String commandParams = matcher.group(2);

                if (command.equals(HIT.getCommand())) {
                    matcher = InputValidator.getMatcherCoordinatesShootShip(commandParams);

                    if (matcher.matches()) {
                        executor.makeShot(matcher, player);
                        player.getGame().printMessage(YOUR_TURN, true);
                        player.getGame().printMessage(YOUR_OPPONENT_TURN, false);
                    }
                } else if (command.equals(SAVE.getCommand())) {
                    executor.save(player);
                }

            }

        } else {
            writer.println(YOUR_OPPONENT_STILL_NOT_READY);
        }
    }

    private void processServerCommandGameNotStarted(Player player, String input, PrintWriter writer, CommandExecutor executor)
            throws InvalidCommandException {
        Matcher matcher = InputValidator.getMatcherCommand(input);

        if (matcher.matches()) {

            String command = matcher.group(1);
            System.out.println(command);

            if (command.equals(CREATE.getCommand())) {
                executor.create(matcher.group(2), player);
            } else if (command.equals(DELETE.getCommand())) {
                executor.delete(matcher.group(2));
            } else if (command.equals(JOIN.getCommand())) {
                executor.join(matcher.group(2), player);
            } else if (command.equals(LIST.getCommand())) {
                executor.list();
            } else if (command.equals(LOAD.getCommand())) {
                executor.load(matcher.group(2));
            } else if (command.equals(SAVED.getCommand())) {
                //todo
            } else {
                writer.println(AVAILABLE_COMMANDS);
            }
        }
    }
}
