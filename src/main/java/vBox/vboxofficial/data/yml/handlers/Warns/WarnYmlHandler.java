package vBox.vboxofficial.data.yml.handlers.Warns;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.handlers.YmlHandler;
import vBox.vboxofficial.data.yml.handlers.Users.UserYmlHandler;
import vBox.vboxofficial.dataobjects.User;
import vBox.vboxofficial.dataobjects.Warn;
import vBox.vboxofficial.dataobjects.handlers.DtoHandler;
import vBox.vboxofficial.utils.enums.LogSeverity;

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
				createWarnLocals(w, cfg, file, hash);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static void createWarnLocals(Warn w, FileConfiguration cfg, File f, String Hash) {
		cfg.addDefault("Warner", w.getWarned().getUuid());
		cfg.addDefault("Warned", w.getWarned().getUuid());
		cfg.addDefault("Reason", w.getReason());
		cfg.addDefault("Hash", Hash);
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

	public static File getWarnFile(User u, String hash) {
		File f = new File(getWarnsFolder(u), "Warn#" + hash + ".yml");
		return f;
	}

	public static Warn getWarn(User u, String hash) {
		Warn w = new Warn();
		for (FileConfiguration cfg : getWarns(u)) {
			if (cfg.getString("Hash") == hash) {
				w.setWarned(
						DtoHandler.createUserDto(main.getServer().getPlayer(UUID.fromString(cfg.getString("Warned")))));
				w.setWarner(
						DtoHandler.createUserDto(main.getServer().getPlayer(UUID.fromString(cfg.getString("Warner")))));
				w.setReason("Reason");
				w.setHash(hash);
			}
		}
		return w;
	}

	public static List<FileConfiguration> getWarns(User u) {
		List<FileConfiguration> warns = new ArrayList<FileConfiguration>();
		for (File file : getWarnsFolder(u).listFiles()) {
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			warns.add(cfg);
		}
		return warns;
	}

	public static void updateWarn(Warn w) {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(getWarnFile(w.getWarned(), w.getHash()));
		cfg.set("Warner", w.getWarner().getUuid().toString());
		cfg.set("Warned", w.getWarned().getUuid().toString());
		cfg.set("Reason", w.getReason());
		cfg.set("Hash", w.getHash());
		cfg.options().copyDefaults(true);
		YmlHandler.saveConfig(cfg, getWarnFile(w.getWarned(), w.getHash()));
	}
}
