package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlWarpHandler;
import vBox.vboxofficial.dtos.Warp;
import vBox.vboxofficial.dtos.handlers.DtoHandler;

public class SetWarpCmd implements CommandExecutor {

	private Main main = Main.getInstance();

	public SetWarpCmd(Main _main) {
		main = _main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("setwarp")) {
			if (!(sender instanceof Player)) {
				main.log("You are not allowed to use that command as Console!", null);
			} else {
				Player p = (Player) sender;
				if (p.hasPermission("vBox.moderation")) {
					if (args.length == 0) {
						p.sendMessage(main.colorize("&cToo little arguments! Usage /setwarp <name>"));
					} else if (args.length > 1) {
						p.sendMessage(main.colorize("&cToo many arguments! Usage /setwarp <name>"));
					} else {
						YmlWarpHandler wh = new YmlWarpHandler(main);
						if (!wh.Exists(args[0])) {
							Warp w = DtoHandler.createWarpDto(p, args[0]);
							wh.createWarpFile(w);
							p.sendMessage(main.colorize("&aWarp: &r" + args[0] + " &a succesfully created!"));
						} else {
							p.sendMessage(main.colorize("&aThis warp arleady exists! updating!"));
							Warp w = wh.getWarp(args[0]);
							wh.updateWarp(w);
							p.sendMessage(main.colorize("&aWarp: &r" + w.getName() + " &ahas been updated!"));
						}
					}
				}
			}
		}
		return false;
	}

}
