package vBox.vboxofficial.dtos;

public class Teleport {

	User teleporter;
	User target;
	String coolDownTime;
	String keepAliveTime;
	String hash;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public User getTeleporter() {
		return teleporter;
	}

	public void setTeleporter(User teleporter) {
		this.teleporter = teleporter;
	}

	public User getTarget() {
		return target;
	}

	public void setTarget(User target) {
		this.target = target;
	}

	public String getCoolDownTime() {
		return coolDownTime;
	}

	public void setCoolDownTime(String coolDownTime) {
		this.coolDownTime = coolDownTime;
	}

	public String getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(String keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

}
