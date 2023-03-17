package nl.vBox;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import nl.vBox.commands.HelpCommandExecutor;
import nl.vBox.commands.WarpCommandExecutor;
import nl.vBox.data.dataobjects.User;
import nl.vBox.data.yml.handlers.YmlHandler;
import nl.vBox.utils.enums.LogSeverity;
import nl.vBox.utils.general.CommandUtils;
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
		FileUtils.init();
		PageHandler.init();
		YmlHandler.startUp();
	}

	private void getCommands() {
		try {
			CommandUtils.registerFakeCommand(new HelpCommandExecutor(), this);
		} catch (ReflectiveOperationException e) {
			this.getLogger().severe("Could not register custom command!");
			e.printStackTrace();
		}
		getCommand("warp").setExecutor(new WarpCommandExecutor(this));
		getCommand("warps").setExecutor(new WarpCommandExecutor(this));
		getCommand("delwarp").setExecutor(new WarpCommandExecutor(this));
		getCommand("setwarps").setExecutor(new WarpCommandExecutor(this));
	}

	private void getListeners() {

	}

}
