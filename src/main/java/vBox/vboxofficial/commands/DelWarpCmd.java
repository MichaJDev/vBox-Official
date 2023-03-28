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
		if (!command.getName().equalsIgnoreCase("delwarp")) {
			return false;
		}

		if (!(sender instanceof Player)) {
			main.log("You are not allowed to use that command as Console!", LogSeverity.INFO);
			return true;
		}

		Player player = (Player) sender;
		if (!player.hasPermission("vbox.moderation")) {
			player.sendMessage(main.colorize("&cYou don't have permission to do that!"));
			return true;
		}

		if (args.length != 1) {
			player.sendMessage(main.colorize("&cInvalid arguments! Usage: /delwarp <name>"));
			return true;
		}

		YmlWarpHandler wh = new YmlWarpHandler(main);
		if (!wh.Exists(args[0])) {
			player.sendMessage(main.colorize("&cThis warp doesn't exist!"));
			return true;
		}

		Warp warp = wh.getWarp(args[0]);
		wh.deleteWarp(warp);
		player.sendMessage(main.colorize("&aWarp: &r" + warp.getName() + " &asuccessfully deleted!"));

		return true;
	}

}
