package nl.vBox;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import nl.vBox.commands.HomeCommandExecutor;
import nl.vBox.commands.SpawnCommandExecutor;
import nl.vBox.commands.WarpCommandExecutor;
import nl.vBox.data.dataobjects.User;
import nl.vBox.data.yml.handlers.YmlHandler;
import nl.vBox.utils.enums.LogSeverity;
import nl.vBox.utils.general.FileUtils;
import nl.vBox.utils.pages.PageHandler;

public class Main extends JavaPlugin {

	private static Main main;

	public static Main getInstance() {
		return main;
	}

	public void log(String msg, LogSeverity ls) {
		switch (ls) {
		case INFO:
			getLogger().info(msg);
			break;
		case WARN:
			getLogger().warning(msg);
			break;
		case SEVERE:
			getLogger().severe(msg);
			break;
		}
	}

	public String colorize(String msg) {
		return ChatColor.translateAlternateColorCodes('&', "[&3vBox&r] " + msg);
	}

	public Player getPlayer(User u) {
		return getServer().getPlayer(u.getUuid());
	}

	@Override
	public void onEnable() {
		log("Initialising Handlers", LogSeverity.INFO);
		initialise();
		log("Getting Commands", LogSeverity.INFO);
		getCommands();
		log("Getting Listeners", LogSeverity.INFO);
		getListeners();
	}

	@Override
	public void onDisable() {

	}

	private void initialise() {
		FileUtils.init();
		PageHandler.init();
		YmlHandler.startUp();
	}

	private void getCommands() {
		getCommand("warp").setExecutor(new WarpCommandExecutor(this));
		getCommand("warps").setExecutor(new WarpCommandExecutor(this));
		getCommand("delwarp").setExecutor(new WarpCommandExecutor(this));
		getCommand("setwarps").setExecutor(new WarpCommandExecutor(this));
		getCommand("setspawn").setExecutor(new SpawnCommandExecutor(this));
		getCommand("spawn").setExecutor(new SpawnCommandExecutor(this));
		getCommand("home").setExecutor(new HomeCommandExecutor(this));
		getCommand("sethome").setExecutor(new HomeCommandExecutor(this));
		getCommand("delhome").setExecutor(new HomeCommandExecutor(this));
		getCommand("homes").setExecutor(new HomeCommandExecutor(this));
	}

	private void getListeners() {

	}

}
