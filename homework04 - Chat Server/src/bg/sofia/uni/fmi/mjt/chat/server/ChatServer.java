package bg.sofia.uni.fmi.mjt.chat.server;

import bg.sofia.uni.fmi.mjt.chat.client.ClientConnectionRunnable;
import bg.sofia.uni.fmi.mjt.chat.models.User;
import bg.sofia.uni.fmi.mjt.chat.repositories.UserRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import static bg.sofia.uni.fmi.mjt.chat.constants.SystemOutConstants.*;

public class ChatServer {

    private final static int PORT = 8080;

    private void processInput(ServerSocket serverSocket) throws IOException {

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.printf(CLIENT_CONNECTED_TO_SERVER, socket.getInetAddress());

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String username = reader.readLine();

            User user = new User(username);

            UserRepository.addUser(user, socket);
            System.out.printf(USER_CONNECTED, username);

            ClientConnectionRunnable runnable = new ClientConnectionRunnable(user, socket);
            new Thread(runnable).start();
        }

    }

    private void startServer() {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.printf(SERVER_RUNNING, PORT);
            processInput(serverSocket);
        } catch (IOException e) {
            System.out.println(PORT_8080_TAKEN);
        }

    }

    public static void main(String[] args) {
        new ChatServer().startServer();
    }

}