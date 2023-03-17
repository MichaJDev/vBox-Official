package vBox.vboxofficial.dataobjects;

public class Warn {

	private User warner;
	private User warned;
	private String reason;
	private String hash;

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

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}
