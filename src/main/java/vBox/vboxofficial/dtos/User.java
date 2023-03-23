package vBox.vboxofficial.dtos;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;

public class User {

	private String Name;
	private String DisplayName;
	private UUID uuid;
	private String Ip;
	private Location loc;
	private World world;
	private boolean banned;

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDisplayName() {
		return DisplayName;
	}

	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getIp() {
		return Ip;
	}

	public void setIp(String ip) {
		Ip = ip;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

}
