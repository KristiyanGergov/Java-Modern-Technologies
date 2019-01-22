package bg.sofia.uni.fmi.mjt.chat.IO;

import bg.sofia.uni.fmi.mjt.chat.client.ChatClient;
import bg.sofia.uni.fmi.mjt.chat.exceptions.NotConnectedException;
import bg.sofia.uni.fmi.mjt.chat.models.User;
import bg.sofia.uni.fmi.mjt.chat.repositories.UserRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler {

    public static PrintWriter writer;
    private static boolean connected;

    public void processClientCommand() throws NotConnectedException {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String input = scanner.nextLine();

                String[] tokens = input.split(" ");
                String command = tokens[0];

                if ("connect".equals(command)) {
                    String host = tokens[1];
                    int port = Integer.parseInt(tokens[2]);

                    String username = tokens[2 + 1];

                    connected = ChatClient.connect(host, port, username);

                } else if (writer != null) {
                    if (connected)
                        writer.println(input);
                    else
                        throw new NotConnectedException("You need to connect to the server first!");
                }
            }
        }
    }


    public void processServerCommand(String commandInput, PrintWriter writer, User user) throws IOException {

        Pattern pattern = Pattern.compile("^([A-Za-z-]*)\\s*(\\w*)\\s*([\\w\\s]*)$");
        Matcher matcher = pattern.matcher(commandInput);


        if (matcher.matches()) {

            String command = matcher.group(1);
            System.out.println(command);
            if ("send".equals(command)) {
                String to = matcher.group(2);
                String message = matcher.group(2 + 1);

                Socket toSocket = UserRepository.getSocket(to);
                if (toSocket == null) {
                    writer.println(String.format("=> %s seems to be offline", to));
                }

                System.out.println("inside");
                List<Socket> socket = new LinkedList<>();
                socket.add(toSocket);
                sentMessage(socket, message, user);
                System.out.println("successfully send message");
            } else if ("disconnect".equals(command)) {
                UserRepository.removeUser(user);
                writer.println("=> you are now disconnected");
            } else if ("list-users".equals(command)) {

                var users = UserRepository.getUsers().entrySet();

                if (users.isEmpty()) {
                    writer.println("=> nobody is online");
                } else {
                    for (var currentUser :
                            users) {
                        writer.println(String.format("=> %s, connect at %s",
                                currentUser.getKey().getName(),
                                currentUser.getKey().getConnectedAt()));
                    }
                }
            } else if ("send-all".equals(command)) {
                String message = matcher.group(2);
                sentMessage(UserRepository.getUsers().values(), message, user);
            }
        }

    }


    private static void sentMessage(Collection<Socket> sockets, String message, User user) throws IOException {

        for (Socket toSocket :
                sockets) {

            PrintWriter toWriter = new PrintWriter(toSocket.getOutputStream(), true);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm");
            String date = dateFormat.format(Calendar.getInstance().getTime());

            toWriter.println(String.format("=> [%s] [%s]: %s", date, user.getName(), message));
        }

    }

}
