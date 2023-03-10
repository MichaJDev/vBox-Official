package nl.vBox.data.yml.handlers.Bans;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import nl.vBox.Main;
import nl.vBox.data.dataobjects.Ban;
import nl.vBox.data.dataobjects.User;
import nl.vBox.data.dataobjects.handlers.DtoHandler;
import nl.vBox.data.yml.handlers.YmlHandler;
import nl.vBox.data.yml.handlers.Users.UserYmlHandler;
import nl.vBox.utils.enums.LogSeverity;

public class BanYmlHandler {

	private static Main main = Main.getInstance();

	public static void createGeneralBanFile(Ban b) {
		File file = new File(getBansFolder(b), "General.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
				createGeneralBanLocals(file, b);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void createGeneralBanLocals(File f, Ban b) {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		cfg.addDefault("Banned", false);
		cfg.options().copyDefaults(true);
		YmlHandler.saveConfig(cfg, f);
	}

	public static File getGeneralBanFile(Ban b) {
		File file = new File(getBansFolder(b), "General.yml");
		if (!file.exists()) {
			createGeneralBanFile(b);
			return file;
		}
		return file;
	}

	public static void updateGeneralBanFile(Ban b, boolean banned) {
		FileConfiguration cfg = getGeneralBanCfg(b);
		cfg.set("Banned", banned);
		cfg.options().copyDefaults(true);
		YmlHandler.saveConfig(cfg, getGeneralBanFile(b));
	}

	public static FileConfiguration getGeneralBanCfg(Ban b) {
		return YamlConfiguration.loadConfiguration(getGeneralBanFile(b));
	}

	public static void createNewBansFolder(Ban b) {
		File file = new File(UserYmlHandler.getUserFolder(b.getBanned()) + File.pathSeparator + "Bans");
		if (!file.exists())
			file.mkdirs();
	}

	public static File getBansFolder(Ban b) {
		File dir = new File(UserYmlHandler.getUserFolder(b.getBanned()) + File.pathSeparator + "Bans");
		if (!dir.exists()) {
			main.log("No bans folder found for user: " + b.getBanned().getName() + ", ...Creating!", LogSeverity.INFO);
			createNewBansFolder(b);
			return dir;
		}
		return dir;
	}

	public static void createBanFile(Ban b) {

		File file = new File(getBansFolder(b) + File.pathSeparator + "Ban#" + b.getUuid() + ".yml");
		if (!file.exists()) {
			updateGeneralBanFile(b, true);
			try {
				file.createNewFile();
				createBanLocals(file, b, b.getUuid());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void createBanLocals(File f, Ban b, String hash) {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		cfg.addDefault("Banner", b.getBanner().getUuid());
		cfg.addDefault("Reason", b.getReason());
		cfg.addDefault("Time", b.getEndOfBan());
		cfg.addDefault("Hash", hash);
		cfg.options().copyDefaults(true);
		YmlHandler.saveConfig(cfg, f);

	}

	public static List<Ban> getBans(User u) {
		Ban b = new Ban();
		b.setBanned(u);
		List<Ban> bans = new ArrayList<Ban>();
		for (File file : getBansFolder(b).listFiles()) {
			bans.add(DtoHandler.ymlToBanDto(YamlConfiguration.loadConfiguration(file)));
		}
		return bans;
	}

	public static Ban getBan(User u, String hash) {
		Ban b = new Ban();
		for (Ban ban : getBans(u)) {
			if (ban.getUuid() == hash) {
				b = ban;
			}
		}
		return b;
	}

	public static boolean isBanned(User u) {
		Ban b = new Ban();
		b.setBanned(u);
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(getGeneralBanFile(b));
		boolean banned = cfg.getBoolean("Banned");
		return banned;

	}
}
