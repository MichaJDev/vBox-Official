package nl.vBox.data.yml.handlers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;

import nl.vBox.Main;
import nl.vBox.data.yml.handlers.Config.ConfigYmlHandler;
import nl.vBox.data.yml.handlers.Users.UserYmlHandler;
import nl.vBox.data.yml.handlers.Warps.WarpYmlHandler;
import nl.vBox.utils.enums.LogSeverity;

public class YmlHandler {

	private static Main main = Main.getInstance();

	/*********/
	/* Setup */
	/*********/

	public static void startUp() {
		main.log("Initializing startup", LogSeverity.INFO);
		if (!UserYmlHandler.getUserMainFolder().exists()) {
			main.log("Main user folder not found, creating!", LogSeverity.INFO);
			UserYmlHandler.createUserMainFolder();
		}
		if (!WarpYmlHandler.getWarpsFolder().exists()) {
			main.log("Main warps folder not found, creating!", LogSeverity.INFO);
			WarpYmlHandler.createWarpsFolder();
		}
		if (!ConfigYmlHandler.getConfigFile().exists()) {
			main.log("Main config file not found, creating!", LogSeverity.INFO);
			ConfigYmlHandler.createConfig();
		}
	}

	public static void saveConfig(FileConfiguration cfg, File file) {
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}