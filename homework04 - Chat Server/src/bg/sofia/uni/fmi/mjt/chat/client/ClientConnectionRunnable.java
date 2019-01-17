package bg.sofia.uni.fmi.mjt.chat.client;

import bg.sofia.uni.fmi.mjt.chat.models.User;
import bg.sofia.uni.fmi.mjt.chat.server.ChatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientConnectionRunnable implements Runnable {

    private User user;
    private Socket socket;

    public ClientConnectionRunnable(User user, Socket socket) {
        this.user = user;
        this.socket = socket;
    }

    private void sentMessage(Collection<Socket> sockets, String message) throws IOException {

        for (Socket toSocket :
                sockets) {

            PrintWriter toWriter = new PrintWriter(toSocket.getOutputStream(), true);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm");
            String date = dateFormat.format(Calendar.getInstance().getTime());

            toWriter.println(String.format("=> [%s] [%s]: %s", date, user.getName(), message));
        }

    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                String commandInput = reader.readLine();

                if (commandInput != null) {
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
                            sentMessage(socket, message);
                            System.out.println("successfully send message");
                        } else if ("disconnect".equals(command)) {
                            ChatServer.removeUser(user);
                        } else if ("list-users".equals(command)) {

                            var users = ChatServer.getUsers().entrySet();

                            if (users.size() == 0) {
                                writer.println("=> nobody is online");
                                continue;
                            }

                            for (var user :
                                    users) {
                                writer.println(String.format("=> %s, connect at %s",
                                        user.getKey().getName(),
                                        user.getKey().getConnectedAt()));
                            }
                        } else if ("send-all".equals(command)) {
                            String message = matcher.group(2);
                            sentMessage(ChatServer.getUsers().values(), message);
                        } else {
                            writer.println(String.format("Invalid command \"%s\"", command));
                        }
                    } else {
                        writer.println("Invalid input");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("socket is closed");
            System.out.println(e.getMessage());
        }
    }

}