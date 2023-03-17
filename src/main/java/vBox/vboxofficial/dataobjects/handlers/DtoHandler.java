package vBox.vboxofficial.dataobjects.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.handlers.Users.HomeYmlHandler;
import vBox.vboxofficial.dataobjects.Ban;
import vBox.vboxofficial.dataobjects.Config;
import vBox.vboxofficial.dataobjects.Home;
import vBox.vboxofficial.dataobjects.Spawn;
import vBox.vboxofficial.dataobjects.User;
import vBox.vboxofficial.dataobjects.Warp;
import vBox.vboxofficial.utils.enums.BanUnit;

public class DtoHandler {

	private static Main main = Main.getInstance();

	public DtoHandler(Main _main) {
		main = _main;

	}

	/*
	 * User
	 */

	public static User createUserDto(Player p) {
		User u = new User();
		u.setName(p.getName());
		u.setUuid(p.getUniqueId());
		u.setDisplayName(p.getDisplayName());
		u.setIp(p.getAddress().getAddress().toString());
		u.setLocation(p.getLocation());
		u.setWorld(p.getWorld());
		List<Home> homes = new ArrayList<Home>();
		for (FileConfiguration home : HomeYmlHandler.getUserHomeListCfg(u)) {
			Home h = new Home();
			Location loc = new Location(main.getServer().getWorld(home.getString("Home.Name")),
					home.getDouble("Home.X"), home.getDouble("Home.Y"), home.getDouble("Home.Z"));
			h.setCoords(loc);
			h.setWorld(loc.getWorld());
			homes.add(h);
		}
		u.setHomes(homes);
		return u;
	}

	public static User YmlUserToDto(FileConfiguration cfg) {
		User u = new User();
		u.setName(cfg.getString("Name"));
		u.setUuid(UUID.fromString(cfg.getString("UUID")));
		u.setDisplayName(cfg.getString("DisplayName"));
		u.setIp(cfg.getString("Ip"));
		u.setLocation(new Location(main.getServer().getWorld(cfg.getString("World")), cfg.getDouble("Location.Y"),
				cfg.getDouble("Location.Y"), cfg.getDouble("Location.Z")));
		u.setWorld(main.getServer().getWorld(cfg.getString("World")));

		return u;
	}

	/*
	 * Homes
	 */
	public static Home createHomeDto(User u, String name) {
		Home h = new Home();
		h.setName(name);
		h.setWorld(u.getWorld());
		h.setCoords(u.getLocation());
		h.setOwner(DtoHandler.createUserDto(main.getServer().getPlayer(u.getUuid())));
		return h;
	}

	public static Home ymlHomeToDto(FileConfiguration cfg) {
		Home h = new Home();
		Location loc = new Location(main.getServer().getWorld(cfg.getString("Home.World")), cfg.getDouble("Home.x"),
				cfg.getDouble("Home.y"), cfg.getDouble("Home.z"));
		h.setName(cfg.getString("Home.Name"));
		h.setCoords(loc);
		h.setWorld(main.getServer().getWorld(cfg.getString("Home.World")));
		return h;
	}

	/*
	 * Warps
	 */

	public static Warp ymlToWarpDto(FileConfiguration cfg) {
		Warp w = new Warp();
		User u = DtoHandler.createUserDto(main.getServer().getPlayer(w.getCreator().getUuid()));
		Location loc = new Location(main.getServer().getWorld(cfg.getString("World")), cfg.getDouble("Location.X"),
				cfg.getDouble("Location.Y"), cfg.getDouble("Location.Z"));
		w.setName(cfg.getString("Name"));
		w.setLocation(loc);
		w.setCreator(u);
		return w;
	}

	public static Warp createWarpDto(User u, String name) {
		Warp w = new Warp();
		w.setName(name);
		w.setLocation(u.getLocation());
		w.setCreator(DtoHandler.createUserDto(main.getServer().getPlayer(u.getUuid())));
		return w;
	}

	/*
	 * Spawn
	 */

	public static Spawn createSpawnDto(User u) {
		Spawn s = new Spawn();
		s.setWorld(u.getWorld());

		s.setX(u.getLocation().getBlockX());
		s.setY(u.getLocation().getBlockY());
		s.setZ(u.getLocation().getBlockZ());
		s.setYaw(u.getLocation().getYaw());
		s.setPitch(u.getLocation().getPitch());
		return s;
	}

	public static Spawn ymlToSpawnDto(FileConfiguration cfg) {
		Spawn s = new Spawn();
		s.setWorld(main.getServer().getWorld(cfg.getString("World")));

		s.setX(cfg.getDouble("X"));
		s.setY(cfg.getDouble("Y"));
		s.setZ(cfg.getDouble("Z"));
		s.setYaw(cfg.getDouble("Yaw"));
		s.setPitch(cfg.getDouble("PitchX"));

		return s;
	}

	/*
	 * Config
	 */

	public static Config createConfigDto(String s, String m) {
		Config c = new Config();
		c.setServerName(s);
		c.setMotd(m);
		return c;
	}

	public static Config ymlToConfigDto(FileConfiguration cfg) {
		Config c = new Config();
		c.setServerName(cfg.getString("ServerName"));
		c.setMotd(cfg.getString("Motd"));
		return c;
	}

	/*
	 * Bans
	 */

	public static Ban ymlToBanDto(FileConfiguration cfg) {
		Ban b = new Ban();
		b.setBanner(DtoHandler.createUserDto(main.getServer().getPlayer(cfg.getString("Banner"))));
		b.setBanned(DtoHandler.createUserDto(main.getServer().getPlayer(cfg.getString("Banned"))));
		b.setEndOfBan(cfg.getLong("Time"));
		b.setReason(cfg.getString("Reason"));
		b.setUuid(cfg.getString("Hash"));
		return b;
	}

	public static Ban createBanDto(Player p, Player tp, int time, String unit, String reason, String Hash) {
		Ban b = new Ban();
		b.setBanner(DtoHandler.createUserDto(p));
		b.setBanned(DtoHandler.createUserDto(tp));
		b.setEndOfBan(BanUnit.getTicks(unit, time));
		b.setReason(reason);
		b.setUuid(Hash);
		return b;
	}
}
