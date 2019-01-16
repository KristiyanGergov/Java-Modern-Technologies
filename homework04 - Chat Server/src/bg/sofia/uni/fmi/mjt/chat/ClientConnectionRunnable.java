package bg.sofia.uni.fmi.mjt.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

public class ClientConnectionRunnable implements Runnable {

    private String username;
    private Socket socket;

    public ClientConnectionRunnable(String username, Socket socket) {
        this.username = username;
        this.socket = socket;
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
                        PrintWriter toWriter = new PrintWriter(toSocket.getOutputStream(), true);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm");
                        String date = dateFormat.format(Calendar.getInstance().getTime());

                        toWriter.println(String.format("=> [%s] [%s]: %s", date, username, message));
                        System.out.println("successfully send message");
                    } else if ("disconnect".equals(command)) {
                        ChatServer.removeUser(username);
                    } else if ("list-users".equals(command)) {
                        Set<User> users = ChatServer.getUsers();
                        for (User user :
                                users) {
                            writer.println(String.format("=> %s, connect at %s", user.getName(), user.getConnectedAt()));
                        }
                    } else if ("send-all".equals(command)) {
                        System.out.println();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("socket is closed");
            System.out.println(e.getMessage());
        }
    }

}