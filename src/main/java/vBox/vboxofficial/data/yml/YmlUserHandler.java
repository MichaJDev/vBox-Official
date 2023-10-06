package vBox.vboxofficial.data.yml;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.dtos.User;
import vBox.vboxofficial.dtos.handlers.DtoHandler;

public class YmlUserHandler {

	private Main main = Main.getInstance();

	public YmlUserHandler(Main _main) {
		main = _main;

	}

	public void createNew(User u) {
		createUserFolder(u);
		File file = new File(getUserFolder(u), "user.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			fillUserFile(u, file);
			YmlHomeHandler hh = new YmlHomeHandler(main);
			hh.createHomesFolder(u);
		}

	}

	public void createUsersFolder() {
		File dir = new File(main.getDataFolder(), "Users");
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	public File getUsersFolder() {
		return new File(main.getDataFolder(), "Users");
	}

	public void createUserFolder(User u) {
		File dir = new File(getUsersFolder(), u.getUuid().toString());
		if (!dir.exists())
			dir.mkdirs();
	}

	public File getUserFolder(User u) {
		return new File(getUsersFolder(), u.getUuid().toString());
	}

	public void fillUserFile(User u, File file) {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.addDefault("Name", u.getName());
		cfg.addDefault("DisplayName", u.getDisplayName());
		cfg.addDefault("UUID", u.getUuid().toString());
		cfg.addDefault("IP", u.getIp());
		cfg.addDefault("Location.x", u.getLoc().getBlockX());
		cfg.addDefault("Location.y", u.getLoc().getBlockY());
		cfg.addDefault("Location.z", u.getLoc().getBlockZ());
		cfg.addDefault("World", u.getWorld().getName());
		cfg.addDefault("Banned", u.isBanned());
		cfg.options().copyDefaults(true);
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public boolean exists(User u) {
		boolean exists = false;

		for (File file : getUsersFolder().listFiles()) {
			String name = file.getName();
			String uuid = u.getUuid().toString();
			if (name.equals(uuid)) {
				exists = true;
			} else {
				exists = false;
			}
		}
		return exists;
	}

	public boolean exists(String name) {
		boolean exists = false;
		for (File file : getUsersFolder().listFiles()) {
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			if (name.equalsIgnoreCase(cfg.getString("Name")) || name.equalsIgnoreCase(cfg.getString("DisplayName"))) {
				exists = true;
			}
		}
		return exists;
	}

	public FileConfiguration getUserConfig(User u) {
		File file = new File(getUserFolder(u), "user.yml");
		return YamlConfiguration.loadConfiguration(file);
	}

	public void update(User u) {
		File file = new File(getUserFolder(u), "user.yml");
		FileConfiguration cfg = getUserConfig(u);
		cfg.set("Name", u.getName());
		cfg.set("DisplayerName", u.getDisplayName());
		cfg.set("UUID", u.getUuid().toString());
		cfg.set("IP", u.getIp());
		cfg.set("Location.x", u.getLoc().getBlockX());
		cfg.set("Location.y", u.getLoc().getBlockY());
		cfg.set("Location.z", u.getLoc().getBlockZ());
		cfg.set("Banned", u.isBanned());
		cfg.set("World", u.getWorld().getName());
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public User getUserByName(String name) {
		User u = new User();
		for (File file : getUsersFolder().listFiles()) {
			File userFile = new File(file, "user.yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(userFile);
			if (name.equalsIgnoreCase(cfg.getString("Name"))) {
				u = DtoHandler.createUserDto(cfg, main);
			}
		}
		return u;
	}
}
