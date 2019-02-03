package bg.sofia.uni.fmi.mjt.battleships.util;

import bg.sofia.uni.fmi.mjt.battleships.IO.OutputHandler;
import bg.sofia.uni.fmi.mjt.battleships.enums.GameStatus;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.UnableToJoinGameException;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.WrongCoordinatesException;
import bg.sofia.uni.fmi.mjt.battleships.models.Game;
import bg.sofia.uni.fmi.mjt.battleships.models.Player;
import bg.sofia.uni.fmi.mjt.battleships.models.Ship;
import bg.sofia.uni.fmi.mjt.battleships.server.GameServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.regex.Matcher;

import static bg.sofia.uni.fmi.mjt.battleships.constants.ExceptionConstants.ALREADY_JOINED_GAME;
import static bg.sofia.uni.fmi.mjt.battleships.constants.ExceptionConstants.CANT_ADD_MORE_THAN_10_SHIPS;
import static bg.sofia.uni.fmi.mjt.battleships.constants.SystemOutConstants.*;

public class CommandExecutor {

    private PrintWriter writer;
    private GameServer server;
    private OutputHandler outputHandler;

    public CommandExecutor(PrintWriter writer, GameServer server) {
        this.writer = writer;
        this.server = server;
        this.outputHandler = new OutputHandler(writer);
    }

    public void create(String gameName, Player player) throws InvalidCommandException {

        if (server.getGames().containsKey(gameName))
            throw new InvalidCommandException("A game with the same name already exist!");

        Game game = new Game(player, gameName);
        server.addGame(gameName, game);
        outputHandler.printCommands(CREATED_GAME, gameName);
        writer.println(NOTIFIED_WHEN_JOIN);
    }

    public void delete(String gameName) {
        server.removeGame(gameName);
        outputHandler.printCommands(DELETED_GAME, gameName);
        //todo
    }

    public void buildShip(Matcher matcher, Player player)
            throws WrongCoordinatesException, IOException, InvalidCommandException {

        char startRow = matcher.group(1).toUpperCase().charAt(0);
        char endRow = matcher.group(3).toUpperCase().charAt(0);
        int startCol = Integer.parseInt(matcher.group(2));
        int endCol = Integer.parseInt(matcher.group(4));

        if (!player.buildShip(startRow, endRow, startCol, endCol))
            throw new InvalidCommandException(CANT_ADD_MORE_THAN_10_SHIPS);


        if (player.getGame().bothPlayersHasBuildTheirShips()) {

            Game game = player.getGame();

            game.getPlayer1PrintWriter().println(BOTH_PLAYER_HAS_BUILT_ALL_SHIPS);
            game.getPlayer2PrintWriter().println(BOTH_PLAYER_HAS_BUILT_ALL_SHIPS);

            GameConsoleWriter gameConsoleWriter =
                    new GameConsoleWriter(player.getGame(),
                            game.getPlayer1PrintWriter(),
                            game.getPlayer2PrintWriter());
            gameConsoleWriter.printBoards();

            game.getPlayer1PrintWriter().println(YOUR_OPPONENT_TURN);
            game.getPlayer2PrintWriter().println(YOUR_TURN);
        } else if (player.hasBuildAllShips()) {
            outputHandler.print(SUCCESSFULLY_BUILD_ALL_SHIPS);
        } else {
            player.printNumberOfLeftShips(writer);
        }
    }

    public void list() {
        outputHandler.printGames(server.getGames().values());
    }

    public void join(String gameName, Player player) {
        try {

            if (player.getGame() != null)
                throw new UnableToJoinGameException(ALREADY_JOINED_GAME);

            Game game = null;

            if (gameName == null || gameName.isEmpty()) {

                for (Map.Entry<String, Game> current :
                        server.getGames().entrySet()) {
                    if (!current.getValue().isFull()) {
                        game = current.getValue();
                        gameName = current.getKey();
                        break;
                    }
                }

                if (game == null)
                    throw new UnableToJoinGameException(NO_GAMES_AVAILABLE);

            } else {
                game = server.getGame(gameName);
            }

            if (player == game.getPlayer1() || player == game.getPlayer2()) {
                throw new UnableToJoinGameException(ALREADY_JOINED_GAME);
            }

            server.joinGame(gameName, player);
            outputHandler.printCommands(JOINED_GAME, gameName);

            PrintWriter otherWriter = new PrintWriter(game.getPlayer1().getSocket().getOutputStream(), true);
            otherWriter.println(game.getPlayer2().getUsername() + " joined.");
            startGame(game);
        } catch (UnableToJoinGameException | IOException e) {
            writer.println(e.getMessage());
            System.out.println(e.getMessage());
            //todo
        }
    }

    public void makeShot(Matcher matcher, Player player) throws WrongCoordinatesException, InvalidCommandException, IOException {
        Ship ship = player.makeShot(matcher.group(1));

        if (ship == null) {
            player.getGame().printMessage(NOT_HIT_SHIP_OPPONENT, true);
            player.getGame().printMessage(NOT_HIT_SHIP_YOU, false);

        } else {
            if (ship.destroyed()) {
                player.getGame().printMessage(DESTROYED_SHIP_OPPONENT, true);
                player.getGame().printMessage(DESTROYED_SHIP_YOU, false);
            } else {
                player.getGame().printMessage(HIT_SHIP_OPPONENT, true);
                player.getGame().printMessage(HIT_SHIP_YOU, false);
            }
        }

        GameConsoleWriter consoleWriter = new GameConsoleWriter(
                player.getGame(),
                player.getGame().getPlayer1PrintWriter(),
                player.getGame().getPlayer2PrintWriter());

        consoleWriter.printBoards();
    }

    private void startGame(Game game) throws IOException {

        GameConsoleWriter gameConsoleWriter =
                new GameConsoleWriter(game, game.getPlayer1PrintWriter(), game.getPlayer2PrintWriter());

        gameConsoleWriter.printBoards();
        gameConsoleWriter.printPickYourShips();
        game.setStatus(GameStatus.InProgress);


    }


}