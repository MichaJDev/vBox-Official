package vBox.vboxofficial.data.yml.handlers.Users;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.handlers.YmlHandler;
import vBox.vboxofficial.dataobjects.User;
import vBox.vboxofficial.dataobjects.handlers.DtoHandler;
import vBox.vboxofficial.utils.enums.LogSeverity;

public class UserYmlHandler {

	private static Main main = Main.getInstance();

	// New Users
	public static void newUser(Player p) {
		User u = new User();

		u.setUuid(p.getUniqueId());
		u.setName(p.getName());
		u.setDisplayName(p.getDisplayName());
		u.setIp(p.getAddress().getAddress().toString());
		u.setLocation(p.getLocation());
		u.setWorld(p.getWorld());

		createUserFolder(u);
		createUserFile(u);
		HomeYmlHandler.createUserHomeFolder(u);
	}

	public static void createUserMainFolder() {
		File file = new File(main.getDataFolder() + File.pathSeparator + "Users");
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static File getUserMainFolder() {
		File file = new File(main.getDataFolder() + File.pathSeparator + "Users");
		if (!file.exists()) {
			createUserMainFolder();
			return file;
		}
		return file;
	}

	public static void createUserFolder(User u) {
		File file = new File(getUserMainFolder() + File.pathSeparator + u.getUuid().toString());
		if (!file.exists())
			file.mkdirs();
	}

	public static File getUserFolder(User u) {
		File file = new File(getUserMainFolder() + File.pathSeparator + u.getUuid().toString());
		if (!file.exists()) {
			createUserFolder(u);
			return file;
		}

		return file;

	}

	public static void createUserFile(User u) {
		File file = new File(getUserFolder(u) + File.pathSeparator + "user.yml");
		if (!file.exists()) {
			main.log("User file not found.", LogSeverity.INFO);
			try {
				main.log("Creating user file", LogSeverity.INFO);
				file.createNewFile();
				createUserLocals(u, file);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static FileConfiguration getUserConfig(User u) {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(UserYmlHandler.getUserFile(u));
		return cfg;
	}

	private static void createUserLocals(User u, File file) {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.addDefault("UUID", u.getUuid());
		cfg.addDefault("Name", u.getName());
		cfg.addDefault("DisplayName", u.getDisplayName());
		cfg.addDefault("Ip", u.getIp());
		cfg.addDefault("Location.x", u.getLocation().getBlockX());
		cfg.addDefault("Location.y", u.getLocation().getBlockY());
		cfg.addDefault("Location.z", u.getLocation().getBlockZ());
		cfg.addDefault("World", u.getWorld());
		cfg.options().copyDefaults(true);
		YmlHandler.saveConfig(cfg, file);

	}

	public static File getUserFile(User u) {
		File file = new File(getUserFolder(u) + File.pathSeparator + "user.yml");
		if (!file.exists()) {
			createUserFile(u);
			return file;
		}
		return file;
	}

	public static void updateUser(User u) {
		for (File f : getUserMainFolder().listFiles()) {
			if (f.exists()) {
				FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
				cfg.set("UUID", u.getUuid());
				cfg.set("Name", u.getName());
				cfg.set("DisplayName", u.getDisplayName());
				cfg.set("Ip", u.getIp());
				cfg.set("Location.X", u.getLocation().getBlockX());
				cfg.set("Location.Y", u.getLocation().getBlockX());
				cfg.set("Location.Z", u.getLocation().getBlockX());
				cfg.set("World", u.getWorld());
				cfg.options().copyDefaults(true);
				YmlHandler.saveConfig(cfg, f);
			}
		}
	}

	public static User getUser(User u) {
		User un = new User();
		for (File f : getUserMainFolder().listFiles()) {
			for (File uf : f.listFiles()) {
				if (uf.getName() == u.getUuid().toString()) {
					un = DtoHandler.YmlUserToDto(YamlConfiguration.loadConfiguration(uf));
				}
			}
		}
		return un;
	}
}
