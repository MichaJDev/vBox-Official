package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlWarpHandler;
import vBox.vboxofficial.dtos.Warp;
import vBox.vboxofficial.utils.LogSeverity;

public class DelWarpCmd implements CommandExecutor {

	private Main main = Main.getInstance();

	public DelWarpCmd(Main _main) {
		main = _main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("delwarp")) {
			if (!(sender instanceof Player)) {
				main.log("You are not allowed to use that commands as Console!", LogSeverity.INFO);
			} else {
				Player p = (Player) sender;
				if (p.hasPermission("vbox.moderation")) {
					if (args.length == 0) {
						p.sendMessage(main.colorize("&cToo little arguments! Usage /warp <name>"));
						return true;
					} else if (args.length > 1) {
						p.sendMessage(main.colorize("&cToo many arguments! Usage /warp <name>"));
						return true;
					} else {
						YmlWarpHandler wh = new YmlWarpHandler(main);

						if (wh.Exists(args[0])) {
							Warp w = wh.getWarp(args[0]);
							wh.deleteWarp(w);
							p.sendMessage(main.colorize("&aWarp: &r" + w.getName() + " &a succesfully deleted!"));
						} else {
							p.sendMessage(main.colorize("&cThis warp doesnt exist!"));
						}
					}
				} else {
					p.sendMessage(main.colorize("&cYou don't have permission to do that!"));
				}
			}
		}
		return false;
	}

}
