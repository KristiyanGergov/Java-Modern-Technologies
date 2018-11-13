package bg.sofia.uni.fmi.mjt.git;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Commit {
    private String hash;
    private String message;
    private LocalDateTime date;
    private ArrayList<String> files;

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm yyyy");

        return date.format(formatter);
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Commit(String message, ArrayList<String> files) {
        this.message = message;
        this.date = LocalDateTime.now();
        this.setHash();
        this.files = files;
    }

    private void setHash() {
        this.hash = hexDigest(getDate() + getMessage());
    }

    public String getHash() {
        return hash;
    }

    public String getMessage() {
        return message;
    }


    public String hexDigest(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return convertBytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String convertBytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte current : bytes) {
            hex.append(String.format("%02x", current));
        }

        return hex.toString();
    }

    public ArrayList<String> getFiles() {
        return files;
    }
}