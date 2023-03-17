package nl.vBox.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.vBox.Main;
import nl.vBox.utils.enums.LogSeverity;

public class HomeCommandExecutor implements CommandExecutor {

	private Main main = Main.getInstance();

	public HomeCommandExecutor(Main _main) {
		main = _main;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (!(s instanceof Player)) {
			main.log("Command is for players only!", LogSeverity.WARN);
			return true;
		} else {
			if (c.getName().equalsIgnoreCase("home")) {
				if (s.hasPermission("vBox.homes")) {

				} else {
					s.sendMessage(main.colorize("&cYou do not have permission to use that command!"));
				}
			} else if (c.getName().equalsIgnoreCase("sethome")) {
				if (s.hasPermission("vBox.homes")) {

				} else {
					s.sendMessage(main.colorize("&cYou do not have permission to use that command!"));
				}
			} else if (c.getName().equalsIgnoreCase("delhome")) {
				if (s.hasPermission("vBox.homes")) {

				} else {
					s.sendMessage(main.colorize("&cYou do not have permission to use that command!"));
				}
			} else if (c.getName().equalsIgnoreCase("homes")) {
				if (s.hasPermission("vBox.homes")) {

				} else {
					s.sendMessage(main.colorize("&cYou do not have permission to use that command!"));
				}
			}

		}
		return false;
	}

}
