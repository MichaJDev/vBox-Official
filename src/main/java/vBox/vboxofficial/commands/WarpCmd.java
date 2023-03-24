package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlWarpHandler;
import vBox.vboxofficial.dtos.Warp;
import vBox.vboxofficial.utils.LogSeverity;

public class WarpCmd implements CommandExecutor {

	Main main = Main.getInstance();

	public WarpCmd(Main _main) {
		main = _main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("warp")) {
			if (!(sender instanceof Player)) {
				main.log("You are not allowed to use that commands as Console!", LogSeverity.INFO);
				return true;
			} else {
				Player p = (Player) sender;
				if (p.hasPermission("vbox.warps")) {
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
							p.teleport(w.getLocation());
							p.sendMessage(main.colorize("&aTeleported to: &r" + w.getName()));
							return false;
						}else {
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
