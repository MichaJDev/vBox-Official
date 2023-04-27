package vBox.vboxofficial.dtos;

public class Ban {

    private User banner;
    private User banned;
    private String reason;
    private String date;
    private String hash;

    private boolean active;
    public User getBanner() {
        return banner;
    }

    public void setBanner(User banner) {
        this.banner = banner;
    }

    public User getBanned() {
        return banned;
    }

    public void setBanned(User banned) {
        this.banned = banned;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
