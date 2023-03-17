package vBox.vboxofficial.dataobjects;

import org.bukkit.Location;

public class Warp {

	private String name;
	private Location location;
	private User creator;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

}
