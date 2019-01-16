package bg.sofia.uni.fmi.mjt.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private BufferedReader reader;
    private PrintWriter writer;

    public static void main(String[] args) throws IOException {
        new ChatClient().run();
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String input = scanner.nextLine();

                String[] tokens = input.split(" ");
                String command = tokens[0];
                if ("connect".equals(command)) {
                    String host = tokens[1];
                    int port = Integer.parseInt(tokens[2]);
                    String username = tokens[3];

                    connect(host, port, username);
                } else { // a server command is received
                    writer.println(input);
                }
            }
        }
    }

    private void connect(String host, int port, String username) {
        try {
            Socket socket = new Socket(host, port);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println(String.format("=> connected to server running on %s:%s as %s", host, port, username));
            writer.println(username);

            ClientRunnable clientRunnable = new ClientRunnable(socket);
            new Thread(clientRunnable).start();
        } catch (IOException e) {
            System.out.println(String.format("=> cannot connect to server on %s:%s, make sure that the server is started", host, port));
        }
    }

}