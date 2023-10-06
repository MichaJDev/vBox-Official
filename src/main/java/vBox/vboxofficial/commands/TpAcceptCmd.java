package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.utils.LogSeverity;

public class TpAcceptCmd implements CommandExecutor {

	private Main main = Main.getInstance();

	public TpAcceptCmd(Main _main) {
		main = _main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("tpaccept")) {
			if (!(sender instanceof Player)) {
				main.log("You cannot use this command as Console!", LogSeverity.INFO);
				return false;
			}
			Player p = (Player) sender;
			if (!p.hasPermission("vBox.teleport.tpaccept")) {
				p.sendMessage(main.colorize("&cYou do not have permission to do that!"));
				return false;
			}

		}
		return false;
	}

}
