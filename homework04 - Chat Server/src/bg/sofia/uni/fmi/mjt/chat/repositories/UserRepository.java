package bg.sofia.uni.fmi.mjt.chat.repositories;

import bg.sofia.uni.fmi.mjt.chat.models.User;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private static Map<User, Socket> users = new HashMap<>();

    public static Socket getSocket(String user) {
        return users.get(new User(user));
    }

    public static void removeUser(User user) {
        users.remove(user);
    }

    public static Map<User, Socket> getUsers() {
        return users;
    }

    public static void addUser(User user, Socket socket) {
        users.put(user, socket);
    }
}