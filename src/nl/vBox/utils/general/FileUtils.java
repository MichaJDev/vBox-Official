package nl.vBox.utils.general;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import nl.vBox.Main;

@SuppressWarnings("static-access")
public class FileUtils {
	private static File configFile;
	private static YamlConfiguration config;
	private static Main main = Main.getInstance();

	public static void init() {
		checkForFiles();
	}

	private static void checkForFiles() {
		FileUtils.configFile = new File(main.getInstance().getDataFolder(), "config.yml");

		if (!FileUtils.configFile.exists()) {
			main.getInstance().getLogger().info("No configfile found, creating...");
			setConfigs();
		}
		loadFiles();
	}

	public static void loadFiles() {
		try {
			if (FileUtils.config == null)
				FileUtils.configFile = new File(main.getInstance().getDataFolder(), "config.yml");

			FileUtils.config = YamlConfiguration.loadConfiguration(FileUtils.configFile);
		} catch (Exception e) {
			main.getInstance().getLogger().warning("Could not load configfiles!");
			e.printStackTrace();
		}
	}

	public static void setConfigs() {
		FileUtils.config = YamlConfiguration.loadConfiguration(FileUtils.configFile);
		if (!FileUtils.configFile.exists())
			main.getInstance().saveResource("config.yml", false);
	}

	public static YamlConfiguration getConfig() {
		return config;
	}
}
