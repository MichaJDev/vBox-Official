package vBox.vboxofficial.dtos.handlers;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.dtos.Home;
import vBox.vboxofficial.dtos.Spawn;
import vBox.vboxofficial.dtos.User;
import vBox.vboxofficial.dtos.Warp;

public class DtoHandler {

	public static User createUserDto(Player p) {
		User u = new User();
		u.setName(p.getName());
		u.setDisplayName(p.getDisplayName());
		u.setUuid(p.getUniqueId());
		u.setIp(p.getAddress().getAddress().toString().replace("/", "").replace(":", ""));
		u.setLoc(p.getLocation());
		u.setWorld(p.getWorld());
		return u;
	}

	public static User createUserDto(FileConfiguration cfg, Main main) {
		User u = new User();
		Location loc = new Location(main.getServer().getWorld(cfg.getString("World")), cfg.getDouble("Location.x"),
				cfg.getDouble("Location.y"), cfg.getDouble("Location.z"));
		u.setName(cfg.getString("Name"));
		u.setDisplayName(cfg.getString("DisplayName"));
		u.setUuid(UUID.fromString(cfg.getString("UUID")));
		u.setIp(cfg.getString("IP"));
		u.setLoc(loc);
		u.setWorld(loc.getWorld());
		return u;
	}

	public static Home createHomeDto(Player p, String name) {
		Home h = new Home();
		h.setHash(UUID.randomUUID().toString().substring(0, 5));
		h.setName(name);
		h.setOwner(createUserDto(p));
		h.setLoc(p.getLocation());
		return h;
	}

	public static Home createHomeDto(FileConfiguration cfg, Main main) {
		Home h = new Home();
		Location loc = new Location(main.getServer().getWorld(cfg.getString("World")), cfg.getDouble("Location.x"),
				cfg.getDouble("Location.y"), cfg.getDouble("Location.z"));
		h.setHash(cfg.getString("Hash"));
		h.setName(cfg.getString("Name"));
		h.setLoc(loc);
		h.setOwner(DtoHandler.createUserDto(main.getServer().getPlayer(UUID.fromString(cfg.getString("Owner")))));
		return h;
	}

	public static Spawn createSpawnDto(Player p) {
		Spawn s = new Spawn();
		s.setLoc(p.getLocation());
		return s;
	}

	public static Spawn createSpawnDto(FileConfiguration cfg, Main main) {
		Spawn s = new Spawn();
		Location loc = new Location(main.getServer().getWorld(cfg.getString("World")), cfg.getDouble("Location.x"),
				cfg.getDouble("Location.y"), cfg.getDouble("Location.z"));
		s.setLoc(loc);
		return s;
	}

	public static Warp createWarpDto(Player p, String name) {
		Warp w = new Warp();
		String hash = UUID.randomUUID().toString().substring(0, 5);
		w.setCreator(createUserDto(p));
		w.setHash(hash);
		w.setLocation(p.getLocation());
		w.setName(name);
		return w;
	}

	public static Warp createWarpDto(FileConfiguration cfg, Main main) {
		Warp w = new Warp();
		Location loc = new Location(main.getServer().getWorld(cfg.getString("World")), cfg.getDouble("Location.x"),
				cfg.getDouble("Location.y"), cfg.getDouble("Location.z"));
		w.setCreator(createUserDto(main.getServer().getPlayer(UUID.fromString(cfg.getString("Hash")))));
		w.setName(cfg.getString("Name"));
		w.setLocation(loc);
		w.setHash(cfg.getString("Hash"));
		return w;
	}

}
