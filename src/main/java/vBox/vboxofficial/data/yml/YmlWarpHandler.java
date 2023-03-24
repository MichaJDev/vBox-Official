package vBox.vboxofficial.data.yml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.dtos.Warp;
import vBox.vboxofficial.dtos.handlers.DtoHandler;

public class YmlWarpHandler {

	private Main main = Main.getInstance();

	public YmlWarpHandler(Main _main) {
		main = _main;
	}

	public void createWarpsFolder() {
		File dir = new File(main.getDataFolder(), "Warps");
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	public File getWarpsFolder() {
		return new File(main.getDataFolder(), "Warps");
	}

	public void createWarpFile(Warp w) {
		File file = new File(getWarpsFolder(), "Warp#" + w.getHash() + ".yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			fillWarpFile(file, w);
		}
	}

	private void fillWarpFile(File f, Warp w) {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		cfg.addDefault("Name", w.getName());
		cfg.addDefault("Hash", w.getHash());
		cfg.addDefault("Location.x", w.getLocation().getBlockX());
		cfg.addDefault("Location.y", w.getLocation().getBlockY());
		cfg.addDefault("Location.z", w.getLocation().getBlockZ());
		cfg.addDefault("World", w.getLocation().getWorld().getName());
		cfg.addDefault("Creator", w.getCreator().getUuid().toString());
		cfg.options().copyDefaults(true);
		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File getWarpFile(Warp w) {
		File file = new File(getWarpsFolder(), "Warp#" + w.getHash() + ".yml");
		if (file.exists())
			return file;
		return file;
	}

	private FileConfiguration getWarpCfg(Warp w) {
		return YamlConfiguration.loadConfiguration(getWarpFile(w));
	}

	public Warp getWarp(String name) {
		Warp w = new Warp();
		for (Warp warp : getWarps()) {
			if (name.equalsIgnoreCase(warp.getName())) {
				w = warp;
			}
		}
		return w;
	}

	public List<Warp> getWarps() {
		List<Warp> warps = new ArrayList<Warp>();
		for (File file : getWarpsFolder().listFiles()) {
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			Warp w = DtoHandler.createWarpDto(cfg, main);
			warps.add(w);
		}
		return warps;

	}

	public void updateWarp(Warp w) {
		FileConfiguration cfg = getWarpCfg(w);
		cfg.set("Hash", w.getHash());
		cfg.set("Name", w.getName());
		cfg.set("Location.x", w.getLocation().getBlockX());
		cfg.set("Location.y", w.getLocation().getBlockY());
		cfg.set("Location.z", w.getLocation().getBlockZ());
		cfg.set("World", w.getLocation().getWorld().getName());
		cfg.set("Creator", w.getCreator().getUuid().toString());
		try {
			cfg.save(getWarpFile(w));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteWarp(Warp w) {
		File file = getWarpFile(w);
		if (file.exists())
			file.delete();
	}

	public boolean Exists(String name) {
		boolean exists = false;
		for (Warp warp : getWarps()) {
			if (name.equalsIgnoreCase(warp.getName())) {
				exists = true;
			}
		}
		return exists;
	}

}
