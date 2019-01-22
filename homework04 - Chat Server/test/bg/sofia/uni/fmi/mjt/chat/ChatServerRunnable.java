package bg.sofia.uni.fmi.mjt.chat;

import bg.sofia.uni.fmi.mjt.chat.server.ChatServer;

public class ChatServerRunnable implements Runnable {
    @Override
    public void run() {
        ChatServer.main(null);
    }
}
