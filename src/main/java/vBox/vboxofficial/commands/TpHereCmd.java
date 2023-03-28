package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.utils.LogSeverity;

public class TpHereCmd implements CommandExecutor {

	private Main main = Main.getInstance();

	public TpHereCmd(Main _main) {
		main = _main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("tphere")) {
			if (!(sender instanceof Player)) {
				main.log("This command cannot be used by Console!", LogSeverity.INFO);
			} else {
				Player p = (Player) sender;
				if (p.hasPermission("vBox.teleport.tp")) {
					Player tp = main.getServer().getPlayer(args[0]);
					if (tp != null) {
						tp.sendMessage(main.colorize("&aTeleporting to: &r" + p.getName()));
						p.sendMessage(main.colorize("&aTelorting: &r" + tp.getName() + " &ato you!"));
						tp.teleport(p.getLocation());

					} else {
						p.sendMessage(main.colorize("&cPlayer not found!"));
					}
				}
			}
		}
		return false;
	}

}
