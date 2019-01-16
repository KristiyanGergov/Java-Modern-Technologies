package bg.sofia.uni.fmi.mjt.chat.client;

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

public class ClientConnectionRunnable implements Runnable {

    private String username;
    private Socket socket;

    public ClientConnectionRunnable(String username, Socket socket) {
        this.username = username;
        this.socket = socket;
    }

    private void sentMessage(Collection<Socket> sockets, String message) throws IOException {

        for (Socket toSocket :
                sockets) {

            PrintWriter toWriter = new PrintWriter(toSocket.getOutputStream(), true);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm");
            String date = dateFormat.format(Calendar.getInstance().getTime());

            toWriter.println(String.format("=> [%s] [%s]: %s", date, username, message));
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
                    String[] tokens = commandInput.split("\\s+");
                    String command = tokens[0];
                    System.out.println(command);
                    if ("send".equals(command)) {
                        String to = tokens[1];
                        String message = tokens[2];

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
                        ChatServer.removeUser(username);
                    } else if ("list-users".equals(command)) {

                        var users = ChatServer.getUsers().entrySet();

                        if (users.size() == 0) {
                            writer.println("=> nobody is online");
                            continue;
                        }

                        for (var user :
                                users) {
                            writer.println(String.format("=> %s, connect at %s", user.getKey().getName(), user.getKey().getConnectedAt()));
                        }
                    } else if ("send-all".equals(command)) {
                        String message = tokens[1];
                        sentMessage(ChatServer.getUsers().values(), message);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("socket is closed");
            System.out.println(e.getMessage());
        }
    }

}