package bg.sofia.uni.fmi.mjt.chat.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User {

    private String name;
    private Date connectedAt;

    public User(String name) {
        this.name = name;
    }

    public User(String name, Date connectedAt) {
        this.name = name;
        this.connectedAt = connectedAt;
    }

    public String getName() {
        return name;
    }

    public String getConnectedAt() {
        return new SimpleDateFormat("dd.MM.yyyy kk:mm").format(connectedAt);
    }

    @Override
    public int hashCode() {
        return name.hashCode() & 32;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User)
            return name.equals(((User) obj).name);
        return false;
    }
}