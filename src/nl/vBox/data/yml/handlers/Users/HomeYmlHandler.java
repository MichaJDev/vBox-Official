package nl.vBox.data.yml.handlers.Users;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.md_5.bungee.api.ChatColor;
import nl.vBox.Main;
import nl.vBox.data.dataobjects.Home;
import nl.vBox.data.dataobjects.User;
import nl.vBox.data.dataobjects.handlers.DtoHandler;
import nl.vBox.data.yml.handlers.YmlHandler;

public class HomeYmlHandler {

	private static Main main = Main.getInstance();

	public static void createUserHomeFolder(User u) {
		File file = new File(UserYmlHandler.getUserFolder(u) + File.pathSeparator + "homes");
		if (!file.exists())
			file.mkdirs();
	}

	public static File getHomeFolder(User u) {
		File file = new File(UserYmlHandler.getUserFolder(u) + File.pathSeparator + "homes");
		if (!file.exists()) {
			createUserHomeFolder(u);
			return file;
		}
		return file;
	}

	public static void createUserHomeFile(User u, String name) {
		File file = new File(getHomeFolder(u), name + ".yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
				createHomeLocals(u, file, name);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void createHomeLocals(User u, File f, String name) {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		cfg.addDefault("Home.Name", name);
		cfg.addDefault("Home.x", u.getLocation().getBlockX());
		cfg.addDefault("Home.y", u.getLocation().getBlockY());
		cfg.addDefault("Home.z", u.getLocation().getBlockZ());
		cfg.addDefault("Home.World", u.getWorld());
		cfg.addDefault("Home.Owner", u.getUuid().toString());
		cfg.options().copyDefaults(true);
		YmlHandler.saveConfig(cfg, f);
	}

	public static File getUserHomeFile(User u, String name) {
		File file = new File(getHomeFolder(u), name + ".yml");
		if (!file.exists()) {
			createUserHomeFile(u, name);
			return file;
		}
		return file;
	}

	public static void updateHome(Home h) {
		User u = DtoHandler.createUserDto(main.getServer().getPlayer(h.getOwner().getUuid()));
		FileConfiguration cfg = getHomeConfig(u, h.getName());
		cfg.set("Home.Name", h.getName());
		cfg.set("Home.x", h.getCoords().getBlockX());
		cfg.set("Home.y", h.getCoords().getBlockY());
		cfg.set("Home.z", h.getCoords().getBlockZ());
		cfg.set("Home.Owner", h.getOwner().getUuid().toString());
		YmlHandler.saveConfig(cfg, getUserHomeFile(h.getOwner(), h.getName()));
	}
	
	public static void deleteHome(Home h) {
		File file = getUserHomeFile(h.getOwner(), h.getName());
		file.delete();
	}

	public static List<FileConfiguration> getUserHomeListCfg(User u) {
		List<FileConfiguration> homes = new ArrayList<FileConfiguration>();
		File[] cfgFiles = getHomeFolder(u).listFiles();
		for (File f : cfgFiles) {
			homes.add(YamlConfiguration.loadConfiguration(f));
		}
		return homes;
	}

	public static FileConfiguration getHomeConfig(User u, String name) {
		List<FileConfiguration> homes = getUserHomeListCfg(u);
		FileConfiguration home = null;
		for (FileConfiguration f : homes) {
			if (f.getString("Home.Name") == name) {
				home = f;
			} else {
				main.getServer().getPlayer(u.getUuid()).sendMessage(
						ChatColor.translateAlternateColorCodes('&', "Home with name:&a" + name + " &rdoes not exist!"));
			}
		}
		return home;
	}

	public static List<Home> getHomes(User u) {
		List<Home> homes = new ArrayList<Home>();
		for (FileConfiguration cfg : getUserHomeListCfg(u)) {
			Home h = DtoHandler.ymlHomeToDto(cfg);
			homes.add(h);
		}
		return homes;
	}
}
