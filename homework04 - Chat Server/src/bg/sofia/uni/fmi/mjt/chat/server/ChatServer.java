package bg.sofia.uni.fmi.mjt.chat.server;

import bg.sofia.uni.fmi.mjt.chat.models.User;
import bg.sofia.uni.fmi.mjt.chat.client.ClientConnectionRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {

    private final static int PORT = 8080;

    private static Map<User, Socket> users = new HashMap<>();

    public static Socket getUser(String user) {
        return users.get(new User(user));
    }

    public static void removeUser(User user) {
        users.remove(user);
    }

    public static Map<User, Socket> getUsers() {
        return users;
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.printf("server is running on localhost:%d%n", PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("A client connected to server " + socket.getInetAddress());

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String username = reader.readLine();

                User user = new User(username, new Date());

                users.put(user, socket);
                System.out.println(username + " connected");

                ClientConnectionRunnable runnable = new ClientConnectionRunnable(user, socket);
                new Thread(runnable).start();
            }
        } catch (IOException e) {
            System.out.println("maybe another server is running or port 8080");
        }
    }

}