package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlHomeHandler;
import vBox.vboxofficial.dtos.Home;
import vBox.vboxofficial.dtos.handlers.DtoHandler;
import vBox.vboxofficial.utils.LogSeverity;

public class HomeCmd implements CommandExecutor {

	private Main main = Main.getInstance();
	private YmlHomeHandler hh;

	public HomeCmd(Main _main) {
		main = _main;
		hh = new YmlHomeHandler(_main);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	    if (!(sender instanceof Player)) {
	        main.log("You cannot use this command via Console!", LogSeverity.WARN);
	        return false;
	    }

	    Player p = (Player) sender;
	    if (!p.hasPermission("vBox.homes")) {
	        p.sendMessage(main.colorize("&cYou have no permission to use that"));
	        return false;
	    }

	    if (args.length == 0) {
	        p.sendMessage(main.colorize("Too little arguments! Usage: /home <#>"));
	        return false;
	    }

	    if (args.length > 1) {
	        p.sendMessage(main.colorize("Too many arguments! Usage: /home <#>"));
	        return false;
	    }

	    Home h = hh.getHome(DtoHandler.createUserDto(p), args[0]);
	    if (h.getHash() != null) {
	        p.sendMessage(main.colorize("Teleporting you to home: " + h.getName()));
	        p.sendMessage(h.getLoc().getBlockX() + ", " + h.getLoc().getBlockY() + ", " + h.getLoc().getBlockZ());
	        p.teleport(h.getLoc());
	    } else {
	        p.sendMessage(main.colorize("&cThat home does not exist!"));
	    }

	    return false;
	}

}
