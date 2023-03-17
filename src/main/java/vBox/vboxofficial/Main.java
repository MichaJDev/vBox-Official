package vBox.vboxofficial;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import vBox.vboxofficial.commands.HomeCommandExecutor;
import vBox.vboxofficial.commands.LookupCommandExecutor;
import vBox.vboxofficial.commands.SpawnCommandExecutor;
import vBox.vboxofficial.commands.WarpCommandExecutor;
import vBox.vboxofficial.data.yml.handlers.YmlHandler;
import vBox.vboxofficial.dataobjects.User;
import vBox.vboxofficial.utils.enums.LogSeverity;

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

	public String colorizeNoTag(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
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
		getCommand("lookup").setExecutor(new LookupCommandExecutor(this));
	}

	private void getListeners() {

	}

}
