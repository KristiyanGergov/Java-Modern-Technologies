package bg.sofia.uni.fmi.mjt.battleships.util;

import bg.sofia.uni.fmi.mjt.battleships.IO.OutputHandler;
import bg.sofia.uni.fmi.mjt.battleships.enums.GameStatus;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.AlreadyJoinedGameException;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.GameFullException;
import bg.sofia.uni.fmi.mjt.battleships.exceptions.NoGamesAvailableException;
import bg.sofia.uni.fmi.mjt.battleships.models.Game;
import bg.sofia.uni.fmi.mjt.battleships.models.Player;
import bg.sofia.uni.fmi.mjt.battleships.server.GameServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static bg.sofia.uni.fmi.mjt.battleships.constants.ExceptionConstants.ALREADY_JOINED_GAME;
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

    public void create(String gameName, Player player) {
        Game game = new Game(player);
        server.addGame(gameName, game);
        player.setGame(game);
        outputHandler.printCommands(CREATED_GAME, gameName);
    }

    public void delete(String gameName) {
        server.removeGame(gameName);
        outputHandler.printCommands(DELETED_GAME, gameName);
        //todo
    }

    public void join(String gameName, Player player) throws NoGamesAvailableException, AlreadyJoinedGameException {
        try {

            if (player.getGame() != null)
                throw new AlreadyJoinedGameException(ALREADY_JOINED_GAME);

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
                    throw new NoGamesAvailableException(NO_GAMES_AVAILABLE);

            } else {
                game = server.getGame(gameName);
            }

            if (player == game.getPlayer1() || player == game.getPlayer2()) {
                throw new AlreadyJoinedGameException(ALREADY_JOINED_GAME);
            }

            player.setGame(game);
            server.joinGame(gameName, player);
            outputHandler.printCommands(JOINED_GAME, gameName);

            PrintWriter otherWriter = new PrintWriter(game.getPlayer1().getSocket().getOutputStream(), true);
            otherWriter.println(game.getPlayer2().getUsername() + " joined.");
            startGame(game);
        } catch (GameFullException | IOException e) {
            writer.println(e.getMessage());
            System.out.println(e.getMessage());
            //todo
        }
    }

    private void startGame(Game game) {
        GameConsoleWriter gameConsoleWriter = new GameConsoleWriter(game);
        gameConsoleWriter.printBoards();
        game.setStatus(GameStatus.InProgress);
    }


}