package vBox.vboxofficial.dtos;

public class Warn {

    private User warner;
    private User warned;
    private String reason;

    public User getWarner() {
        return warner;
    }

    public void setWarner(User warner) {
        this.warner = warner;
    }

    public User getWarned() {
        return warned;
    }

    public void setWarned(User warned) {
        this.warned = warned;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
