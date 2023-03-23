package vBox.vboxofficial.data.yml;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.dtos.Spawn;
import vBox.vboxofficial.dtos.User;
import vBox.vboxofficial.dtos.handlers.DtoHandler;

public class YmlSpawnHandler {

	Main main = Main.getInstance();

	public YmlSpawnHandler(Main _main) {
		main = _main;
	}

	public void createSpawnFirstTime() {
		createSpawnFile();
	}

	public void createSpawnFolder() {
		File dir = new File(main.getDataFolder(), "Spawn");
		if (!dir.exists())
			dir.mkdirs();
	}

	public File getSpawnFolder() {
		return new File(main.getDataFolder(), "Spawn");
	}

	private void createSpawnFile() {
		createSpawnFolder();
		File file = new File(getSpawnFolder(), "spawn.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			fillSpawnFile(file);
		}
	}

	private File getSpawnFile() {
		return new File(getSpawnFolder(), "spawn.yml");
	}

	private void fillSpawnFile(File f) {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		cfg.addDefault("Location.x", 0);
		cfg.addDefault("Location.y", 0);
		cfg.addDefault("Location.z", 0);
		cfg.addDefault("World", "world");
		cfg.options().copyDefaults(true);
		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private FileConfiguration getSpawnCfg() {
		return YamlConfiguration.loadConfiguration(getSpawnFile());
	}

	public void updateSpawn(User u) {
		FileConfiguration cfg = getSpawnCfg();
		cfg.set("Location.x", u.getLoc().getBlockX());
		cfg.set("Location.y", u.getLoc().getBlockY());
		cfg.set("Location.z", u.getLoc().getBlockZ());
		cfg.set("World", u.getLoc().getWorld().getName());
		cfg.options().copyDefaults(true);
		try {
			cfg.save(getSpawnFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Spawn getSpawn() {
		return DtoHandler.createSpawnDto(getSpawnCfg(), main);
	}

}
