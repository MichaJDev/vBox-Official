package vBox.vboxofficial.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlHomeHandler;
import vBox.vboxofficial.dtos.Home;
import vBox.vboxofficial.dtos.User;
import vBox.vboxofficial.dtos.handlers.DtoHandler;
import vBox.vboxofficial.utils.LogSeverity;

public class HomesCmd implements CommandExecutor {

	Main main = Main.getInstance();
	YmlHomeHandler hh;

	public HomesCmd(Main _main) {
		main = _main;
		hh = new YmlHomeHandler(_main);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	    if (!(sender instanceof Player)) {
	        main.log("You cannot use this command as Console", LogSeverity.WARN);
	        return false;
	    }

	    Player p = (Player) sender;
	    if (!p.hasPermission("vBox.homes")) {
	        p.sendMessage(main.colorize("&cYou do not have permission to do that"));
	        return false;
	    }

	    if (args.length < 1) {
	        p.sendMessage(main.colorize("Usage: /homes"));
	        return false;
	    }

	    User u = DtoHandler.createUserDto(p);
	    List<Home> homes = hh.getHomes(u);
	    if (homes.isEmpty()) {
	        p.sendMessage(main.colorize("&cNo homes found!"));
	        return false;
	    }

	    StringBuilder sb = new StringBuilder();
	    for (Home h : homes) {
	        sb.append(h.getName()).append(", ");
	    }
	    String homesList = sb.toString().trim();
	    if (homesList.endsWith(",")) {
	        homesList = homesList.substring(0, homesList.length() - 1);
	    }
	    p.sendMessage(main.colorizeNoTag("&6+&r-|&6Homes&r|--------------"));
	    p.sendMessage(homesList);
	    p.sendMessage(main.colorizeNoTag("&6+&r-|&6Homes&r|--------------"));
	    return false;
	}


}
