package vBox.vboxofficial.dtos;

import org.bukkit.Location;

public class Back {

    private User user;
    private Location location;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
