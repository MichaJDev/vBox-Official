package nl.vBox.data.yml.handlers.Spawn;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import nl.vBox.Main;
import nl.vBox.data.dataobjects.Spawn;
import nl.vBox.data.yml.handlers.YmlHandler;

public class SpawnYmlHandler {

	private static Main main = Main.getInstance();

	public static void createSpawnFolder() {
		File file = new File(main.getDataFolder() + File.pathSeparator + "Spawn");
		if (!file.exists())
			file.mkdirs();
	}

	public static File getSpawnFolder() {
		File file = new File(main.getDataFolder() + File.pathSeparator + "Spawn");
		if (!file.exists()) {
			createSpawnFolder();
			return file;
		}
		return file;
	}

	public static void createSpawnFile() {
		File file = new File(getSpawnFolder() + File.pathSeparator + "spawn.yml");
		Spawn s = new Spawn();
		if (!file.exists()) {
			try {
				file.createNewFile();
				createSpawnLocals(s, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void createSpawnLocals(Spawn s, File file) {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.addDefault("World", s.getWorld());
		cfg.addDefault("X", s.getX());
		cfg.addDefault("Y", s.getY());
		cfg.addDefault("Z", s.getZ());
		cfg.addDefault("Yaw", s.getYaw());
		cfg.addDefault("Pitch", s.getPitch());
		cfg.options().copyDefaults(true);
		YmlHandler.saveConfig(cfg, file);
	}
	public static File getSpawnFile() {
		File file = new File(getSpawnFolder() + File.pathSeparator+ "spawn.yml");
		if(!file.exists()) {
			createSpawnFile();
			return file;
		}
		return file;
	}
	public static FileConfiguration getSpawnCfg() {
		return YamlConfiguration.loadConfiguration(getSpawnFile());
	}
	public static void updateSpawn(Spawn s) {
		FileConfiguration cfg = getSpawnCfg();
		cfg.set("World", s.getWorld());
		cfg.set("X", s.getX());
		cfg.set("Y", s.getY());
		cfg.set("Z", s.getZ());
		cfg.set("Yaw", s.getYaw());
		cfg.set("Pitch", s.getPitch());
		YmlHandler.saveConfig(cfg, getSpawnFile());
	}

}
