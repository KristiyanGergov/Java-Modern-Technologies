package bg.sofia.uni.fmi.mjt.battleships.IO;

import bg.sofia.uni.fmi.mjt.battleships.models.Game;

import java.io.PrintWriter;
import java.util.Collection;

import static bg.sofia.uni.fmi.mjt.battleships.constants.SystemOutConstants.*;

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

    private String getListGamesText(String text, final int length) {

        StringBuilder sb = new StringBuilder();
        sb.append(text);
        for (int i = text.length(); i < length; i++) {
            sb.append(" ");
        }

        return sb.toString();
    }

    public void printGames(Collection<Game> games) {
        if (games.isEmpty()) {
            writer.println(NO_GAMES_AVAILABLE);
        } else {

            writer.println(LIST_GAMES_HEADER);

            for (Game game :
                    games) {

                String gameName = getListGamesText(game.getName(), LIST_GAMES_NAME_LENGTH);
                String creator = getListGamesText(game.getPlayer1().getUsername(), LIST_GAMES_CREATOR_LENGTH);
                String status = getListGamesText(game.getStatus().getName(), LIST_GAMES_STATUS_LENGTH);
                String players = getListGamesText(String.format(PLAYERS_NUMBER, game.getPlayersNumber()), LIST_GAMES_PLAYERS_LENGTH);

                writer.println(String.format(LIST_GAMES_BODY, gameName, creator, status, players));
            }

        }
    }
}
