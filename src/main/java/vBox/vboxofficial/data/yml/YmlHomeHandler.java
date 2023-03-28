package vBox.vboxofficial.data.yml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.dtos.Home;
import vBox.vboxofficial.dtos.User;
import vBox.vboxofficial.dtos.handlers.DtoHandler;

public class YmlHomeHandler {
	private final Main main;

	public YmlHomeHandler(Main main) {
		this.main = main;
	}

	public void createHomesFolder(User u) {
		YmlUserHandler uh = new YmlUserHandler(main);
		File dir = new File(uh.getUserFolder(u), "homes");
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	public File getHomeFile(Home h) {
		return new File(getHomesFolder(h.getOwner()), "Home#" + h.getHash() + ".yml");
	}

	public File getHomesFolder(User u) {
		YmlUserHandler uh = new YmlUserHandler(main);
		return new File(uh.getUserFolder(u), "homes");
	}

	public void createHome(Home h) {
		File file = getHomeFile(h);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			fillHomeFile(file, h);
		}
	}

	private void fillHomeFile(File f, Home h) {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		cfg.addDefault("Hash", h.getHash());
		cfg.addDefault("Name", h.getName());
		cfg.addDefault("Location.x", h.getLoc().getBlockX());
		cfg.addDefault("Location.y", h.getLoc().getBlockY());
		cfg.addDefault("Location.z", h.getLoc().getBlockZ());
		cfg.addDefault("World", h.getLoc().getWorld().getName());
		cfg.addDefault("Owner", h.getOwner().getUuid().toString());
		cfg.options().copyDefaults(true);
		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Home getHome(Home h) {
		return DtoHandler.createHomeDto(YamlConfiguration.loadConfiguration(getHomeFile(h)), main);
	}

	public Home getHome(User u, String home) {
		Home h = new Home();
		for (File file : getHomesFolder(u).listFiles()) {
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			if (home.equals(cfg.getString("Name"))) {
				h = DtoHandler.createHomeDto(cfg, main);
			}
		}
		return h;
	}

	public FileConfiguration getHomeConfig(Home h) {
		return YamlConfiguration.loadConfiguration(getHomeFile(h));
	}

	public void update(Home h) {
		FileConfiguration cfg = getHomeConfig(h);
		cfg.set("Name", h.getName());
		cfg.set("Location.x", h.getLoc().getBlockX());
		cfg.set("Location.y", h.getLoc().getBlockY());
		cfg.set("Location.z", h.getLoc().getBlockZ());
		cfg.set("World", h.getLoc().getWorld().getName());
		cfg.set("Owner", h.getOwner().getUuid().toString());
		try {
			cfg.save(getHomeFile(h));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void delete(Home h) {
		File f = getHomeFile(h);
		f.delete();
	}

	public List<Home> getHomes(User u) {
		List<Home> homes = new ArrayList<Home>();
		for (File f : getHomesFolder(u).listFiles()) {
			Home h = DtoHandler.createHomeDto(YamlConfiguration.loadConfiguration(f), main);
			homes.add(h);
		}
		return homes;
	}

	public int amount(User u) {
		int amount = 0;
		for (int i = 0; i < getHomesFolder(u).listFiles().length; i++) {
			amount = i;
		}
		return amount;
	}
}
