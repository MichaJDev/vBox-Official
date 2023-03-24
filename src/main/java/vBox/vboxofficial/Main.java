package vBox.vboxofficial;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import vBox.vboxofficial.commands.DelHomeCmd;
import vBox.vboxofficial.commands.DelWarpCmd;
import vBox.vboxofficial.commands.HomeCmd;
import vBox.vboxofficial.commands.HomesCmd;
import vBox.vboxofficial.commands.LookupCmd;
import vBox.vboxofficial.commands.SetHomeCmd;
import vBox.vboxofficial.commands.SetSpawnCmd;
import vBox.vboxofficial.commands.SetWarpCmd;
import vBox.vboxofficial.commands.SpawnCmd;
import vBox.vboxofficial.commands.WarpCmd;
import vBox.vboxofficial.data.yml.YmlSpawnHandler;
import vBox.vboxofficial.data.yml.YmlUserHandler;
import vBox.vboxofficial.data.yml.YmlWarpHandler;
import vBox.vboxofficial.dtos.User;
import vBox.vboxofficial.listeners.UserListener;
import vBox.vboxofficial.utils.LogSeverity;

public class Main extends JavaPlugin {

	private static Main main;
	private YmlUserHandler uh = new YmlUserHandler(this);
	private YmlSpawnHandler sh = new YmlSpawnHandler(this);
	private YmlWarpHandler wh = new YmlWarpHandler(this);

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
		return ChatColor.translateAlternateColorCodes('&', "[&3v&8Box&r] " + msg);
	}

	public String colorizeNoTag(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	public Player getPlayer(User u) {
		return getServer().getPlayer(u.getUuid());
	}

	@Override
	public void onEnable() {
		log("Initialising vBox", LogSeverity.INFO);
		uh.createUsersFolder();
		sh.createSpawnFirstTime();
		wh.createWarpsFolder();
		log("Injecting Listeners", LogSeverity.INFO);
		getListeners();
		log("Injecting Commands", LogSeverity.INFO);
		getCommands();
	}

	@Override
	public void onDisable() {

	}

	private void getCommands() {
		getCommand("lookup").setExecutor(new LookupCmd(this));
		getCommand("home").setExecutor(new HomeCmd(this));
		getCommand("homes").setExecutor(new HomesCmd(this));
		getCommand("sethome").setExecutor(new SetHomeCmd(this));
		getCommand("delHome").setExecutor(new DelHomeCmd(this));
		getCommand("spawn").setExecutor(new SpawnCmd(this));
		getCommand("setspawn").setExecutor(new SetSpawnCmd(this));
		getCommand("setwarp").setExecutor(new SetWarpCmd(this));
		getCommand("warp").setExecutor(new WarpCmd(this));
		getCommand("delwarp").setExecutor(new DelWarpCmd(this));
		getCommand("warps").setExecutor(new WarpsCmd(this));
	}

	private void getListeners() {
		getServer().getPluginManager().registerEvents(new UserListener(this), this);
	}

}
