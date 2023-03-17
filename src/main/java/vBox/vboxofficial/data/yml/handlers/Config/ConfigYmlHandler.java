package vBox.vboxofficial.data.yml.handlers.Config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.dataobjects.Config;

public class ConfigYmlHandler {

	private static Main main = Main.getInstance();

	public static void createConfig() {
		main.saveDefaultConfig();
		main.getConfig().addDefault("ServerName", "vBox");
		main.getConfig().addDefault("Motd", "Dont forget to vote!");
		main.getConfig().options().copyDefaults(true);
		main.saveConfig();
	}

	public static FileConfiguration getConfig() {
		return main.getConfig();
	}

	public static void updateConfig(Config c) {
		if (!c.getServerName().isBlank()) {
			getConfig().set("ServerName", c.getServerName());
		}
		if (!c.getMotd().isBlank()) {
			getConfig().set("Motd", c.getMotd());
		}
		getConfig().options().copyDefaults(true);
		main.saveConfig();
	}
	public static File getConfigFile() {
		File f = new File(main.getDataFolder() + File.pathSeparator + "config.yml");
		if(!f.exists()) {
			createConfig();
			return f;	
		}
		return f;
	}
}
