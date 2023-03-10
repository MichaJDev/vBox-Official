package nl.vBox.data.yml.handlers.Warns;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import nl.vBox.Main;
import nl.vBox.data.dataobjects.User;
import nl.vBox.data.dataobjects.Warn;
import nl.vBox.data.yml.handlers.YmlHandler;
import nl.vBox.data.yml.handlers.Users.UserYmlHandler;
import nl.vBox.utils.enums.LogSeverity;

public class WarnYmlHandler {

	private static Main main = Main.getInstance();

	public static void createWarnsFolder(User u) {
		File file = new File(UserYmlHandler.getUserFolder(u) + File.pathSeparator + "warns");
		if (!file.exists()) {
			main.log("Warns folder not found...Creating;", LogSeverity.INFO);
			file.mkdirs();
		}

	}

	public static File getWarnsFolder(User u) {
		File file = new File(UserYmlHandler.getUserFolder(u) + File.pathSeparator + "warns");
		if (!file.exists()) {
			createWarnsFolder(u);
			return file;
		}
		return file;
	}

	public static void createWarnsFile(Warn w) {
		String hash = UUID.randomUUID().toString().substring(0, 5);
		File file = new File(getWarnsFolder(w.getWarned()), "Warn#" + hash + ".yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
				FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
				createWarnLocals(w, cfg, file);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static void createWarnLocals(Warn w, FileConfiguration cfg, File f) {
		cfg.addDefault("Warner", w.getWarned().getUuid());
		cfg.addDefault("Warned", w.getWarned().getUuid());
		cfg.addDefault("Reason", w.getReason());
		cfg.options().copyDefaults(true);
		YmlHandler.saveConfig(cfg, f);
	}

	public static int getWarnAmount(User u) {
		int amount = 0;
		if (getWarnsFolder(u).list().length < 0) {
			for (@SuppressWarnings("unused")
			File file : getWarnsFolder(u).listFiles()) {
				amount++;
			}
		}

		return amount;
	}
}
