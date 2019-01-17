package bg.sofia.uni.fmi.mjt.chat.IO;

import bg.sofia.uni.fmi.mjt.chat.models.User;
import bg.sofia.uni.fmi.mjt.chat.server.ChatServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler {

    private static void sentMessage(Collection<Socket> sockets, String message, User user) throws IOException {

        for (Socket toSocket :
                sockets) {

            PrintWriter toWriter = new PrintWriter(toSocket.getOutputStream(), true);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm");
            String date = dateFormat.format(Calendar.getInstance().getTime());

            toWriter.println(String.format("=> [%s] [%s]: %s", date, user.getName(), message));
        }

    }

    public static void processCommand(String commandInput, PrintWriter writer, User user) throws IOException {

        Pattern pattern = Pattern.compile("^([A-Za-z-]*)\\s*(\\w*)\\s*([\\w\\s]*)$");
        Matcher matcher = pattern.matcher(commandInput);


        if (matcher.matches()) {

            String command = matcher.group(1);
            System.out.println(command);
            if ("send".equals(command)) {
                String to = matcher.group(2);
                String message = matcher.group(2 + 1);

                Socket toSocket = ChatServer.getUser(to);
                if (toSocket == null) {
                    writer.println(String.format("=> %s seems to be offline", to));
                }

                System.out.println("inside");
                List<Socket> socket = new LinkedList<>();
                socket.add(toSocket);
                sentMessage(socket, message, user);
                System.out.println("successfully send message");
            } else if ("disconnect".equals(command)) {
                ChatServer.removeUser(user);
            } else if ("list-users".equals(command)) {

                var users = ChatServer.getUsers().entrySet();

                if (users.size() == 0) {
                    writer.println("=> nobody is online");
                    return;
                }

                for (var currentUser :
                        users) {
                    writer.println(String.format("=> %s, connect at %s",
                            currentUser.getKey().getName(),
                            currentUser.getKey().getConnectedAt()));
                }
            } else if ("send-all".equals(command)) {
                String message = matcher.group(2);
                sentMessage(ChatServer.getUsers().values(), message, user);
            } else {
                writer.println(String.format("Invalid command \"%s\"", command));
            }

        } else {
            writer.println("Invalid input");
        }

    }

}
