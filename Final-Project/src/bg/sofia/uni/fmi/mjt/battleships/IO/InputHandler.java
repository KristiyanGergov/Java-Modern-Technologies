package bg.sofia.uni.fmi.mjt.battleships.IO;

import bg.sofia.uni.fmi.mjt.battleships.client.GameClient;
import bg.sofia.uni.fmi.mjt.battleships.enums.GameStatus;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.NotConnectedException;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongCoordinatesException;
import bg.sofia.uni.fmi.mjt.battleships.models.Game;
import bg.sofia.uni.fmi.mjt.battleships.models.Player;
import bg.sofia.uni.fmi.mjt.battleships.server.GameServer;
import bg.sofia.uni.fmi.mjt.battleships.util.CommandExecutor;
import bg.sofia.uni.fmi.mjt.battleships.util.InputValidator;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    private static String readFileAsString(String fileName) throws IOException {
        String data;
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    public void processServerCommand(String commandInput, final PrintWriter writer, final GameServer server, final Player player)
            throws InvalidCommandException, WrongCoordinatesException, IOException {

        final boolean hasGameStarted = player.getGame() != null && player.getGame().getStatus() == GameStatus.InProgress;
        final CommandExecutor executor = new CommandExecutor(writer, server);
        commandInput = commandInput.toLowerCase();

        if (player.hasBuildAllShips()) {
            processServerCommandShootShips(player, commandInput, writer, executor);
        } else if (hasGameStarted) {
            processServerCommandGameStarted(player, commandInput, executor);
        } else {
            processServerCommandGameNotStarted(player, commandInput, writer, executor);
        }


    }

    private void processServerCommandGameStarted(Player player, String commandInput, CommandExecutor executor)
            throws WrongCoordinatesException, IOException, InvalidCommandException {

        if (commandInput.equals(LEAVE.getCommand())) {
            //todo
        } else {

            Matcher matcher = InputValidator.getMatcherCoordinatesBuildShip(commandInput);

            if (matcher.matches()) {
                executor.buildShip(matcher, player);
            } else {
                throw new InvalidCommandException(
                        String.format(INVALID_COMMAND, commandInput, AVAILABLE_COMMANDS_AFTER_JOINING_GAME));
            }
        }

    }

    private void processServerCommandShootShips(Player player, String commandInput, PrintWriter writer, CommandExecutor executor)
            throws IOException, WrongCoordinatesException, InvalidCommandException {
        if (player.getGame().bothPlayersHasBuildTheirShips()) {

            Matcher matcher = InputValidator.getMatcherCoordinatesShootShip(commandInput);

            if (matcher.matches()) {
                executor.makeShot(matcher, player);
                player.getGame().printMessage(YOUR_TURN, true);
                player.getGame().printMessage(YOUR_OPPONENT_TURN, false);
            }

        } else {
            writer.println(YOUR_OPPONENT_STILL_NOT_READY);
        }
    }

    private void processServerCommandGameNotStarted(Player player, String commandInput, PrintWriter writer, CommandExecutor executor)
            throws InvalidCommandException, IOException {
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
            } else if (command.equals(LIST.getCommand())) {
                executor.list();
            } else if (command.equals(SAVE.getCommand())) {
                if (player.getGame() == null) {
                    //todo
                } else {
                    Gson gson = new Gson();
                    String game = gson.toJson(player.getGame());

                    try (PrintWriter pw = new PrintWriter("games/" + player.getGame().getName() + ".json")) {
                        pw.write(game);
                        pw.flush();
                    }

                }

            } else if (command.equals(LOAD.getCommand())) {
                String game = readFileAsString("games/game.json");
                Gson gson = new Gson();
                Game game1 = gson.fromJson(game, Game.class);
                int a = 5;
            } else if (command.equals(SAVED.getCommand())) {
                //todo
            } else {
                writer.println(AVAILABLE_COMMANDS);
            }
        }
    }
}
