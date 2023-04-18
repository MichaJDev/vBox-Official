package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlBackHandler;
import vBox.vboxofficial.data.yml.YmlWarpHandler;
import vBox.vboxofficial.dtos.Warp;
import vBox.vboxofficial.dtos.handlers.DtoHandler;
import vBox.vboxofficial.utils.LogSeverity;

public class WarpCmd implements CommandExecutor {

	Main main = Main.getInstance();

	public WarpCmd(Main _main) {
		main = _main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	    if (!command.getName().equalsIgnoreCase("warp")) {
	        return false;
	    }

	    if (!(sender instanceof Player)) {
	        main.log("You are not allowed to use that command as Console!", LogSeverity.INFO);
	        return true;
	    }

	    Player player = (Player) sender;
	    if (!player.hasPermission("vbox.warps")) {
	        player.sendMessage(main.colorize("&cYou don't have permission to do that!"));
	        return true;
	    }

	    if (args.length == 0 || args.length > 1) {
	        player.sendMessage(main.colorize("&cUsage: /warp <name>"));
	        return true;
	    }

	    YmlWarpHandler wh = new YmlWarpHandler(main);
	    if (!wh.Exists(args[0])) {
	        player.sendMessage(main.colorize("&cThis warp doesn't exist!"));
	        return true;
	    }
		YmlBackHandler bh = new YmlBackHandler(main);
	    Warp warp = wh.getWarp(args[0]);
	    player.teleport(warp.getLocation());
	    player.sendMessage(main.colorize("&aTeleported to: &r" + warp.getName()));
		bh.createBackFile(DtoHandler.createBackDto(player));
		player.sendMessage(main.colorize("&aCreated back location you can use &r/back to return to your previous location."));
	    return true;
	}

}
