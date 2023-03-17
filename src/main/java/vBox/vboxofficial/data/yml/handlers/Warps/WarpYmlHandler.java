package vBox.vboxofficial.data.yml.handlers.Warps;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.handlers.YmlHandler;
import vBox.vboxofficial.dataobjects.Warp;

public class WarpYmlHandler {

	private static Main main = Main.getInstance();

	public static void createWarpsFolder() {
		File file = new File(main.getDataFolder() + File.pathSeparator + "warps");
		if (!file.exists())
			file.mkdirs();
	}

	public static File getWarpsFolder() {
		File file = new File(main.getDataFolder() + File.pathSeparator + "warps");
		if (!file.exists()) {
			createWarpsFolder();
			return file;
		}
		return file;

	}

	public static void createNewWarp(Warp w) {
		File file = new File(getWarpsFolder() + File.pathSeparator + w.getName() + ".yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
				createWarpLocals(w, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void createWarpLocals(Warp w, File file) {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.addDefault("Name", file.getName().replace(".yml", ""));
		cfg.addDefault("Location.x", w.getLocation().getBlockX());
		cfg.addDefault("Location.y", w.getLocation().getBlockY());
		cfg.addDefault("Location.z", w.getLocation().getBlockZ());
		cfg.addDefault("World", w.getLocation().getWorld());
		cfg.addDefault("Creator", w.getCreator().getUuid());
		cfg.options().copyDefaults(true);
		YmlHandler.saveConfig(cfg, file);
	}

	public static File getWarpFile(Warp w) {
		File file = new File(getWarpsFolder(), w.getName() + ".yml");
		if (file.exists())
			return file;
		return file;
	}

	public static List<FileConfiguration> getWarps() {
		List<FileConfiguration> wList = new ArrayList<FileConfiguration>();
		for (File f : getWarpsFolder().listFiles()) {
			wList.add(YamlConfiguration.loadConfiguration(f));
		}
		return wList;
	}

	public static void updateWarp(Warp w) {
		for (FileConfiguration cfg : getWarps()) {
			if (w.getName() == cfg.getString("Name")) {
				cfg.set("Name", w.getName());
				cfg.set("Location.x", w.getLocation().getBlockX());
				cfg.set("Location.y", w.getLocation().getBlockX());
				cfg.set("Location.z", w.getLocation().getBlockX());
				cfg.set("World", w.getLocation().getWorld().toString());
			}
		}
	}

	public static void deleteWarp(Warp w) {
		File f = getWarpFile(w);
		if (f.exists()) {
			try {
				f.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
