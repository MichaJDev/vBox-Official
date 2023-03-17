package vBox.vboxofficial.dataobjects;

public class Ban {
	private User banner;
	private User banned;
	private String reason;
	private long endOfBan;
	private String uuid;
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

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

	public long getEndOfBan() {
		return endOfBan;
	}

	public void setEndOfBan(long endOfBan) {
		this.endOfBan = endOfBan;
	}

}
