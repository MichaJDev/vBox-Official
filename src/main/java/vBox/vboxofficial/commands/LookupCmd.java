package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlUserHandler;
import vBox.vboxofficial.dtos.User;

public class LookupCmd implements CommandExecutor {

	Main main = Main.getInstance();
	YmlUserHandler uh;

	public LookupCmd(Main _main) {
		main = _main;
		uh = new YmlUserHandler(_main);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	    if (!(sender instanceof Player)) {
	        sender.sendMessage(main.colorize("&cYou cannot use this command as Console"));
	        return false;
	    }

	    Player p = (Player) sender;
	    if (!command.getName().equalsIgnoreCase("lookup")) {
	        return false;
	    }

	    if (!p.hasPermission("vBox.moderation")) {
	        p.sendMessage(main.colorize("&cYou have no permission to use that"));
	        return false;
	    }

	    if (args.length != 1) {
	        p.sendMessage(main.colorize("&cUsage: /lookup <name>"));
	        return false;
	    }

	    User u = uh.getUserByName(args[0]);
	    if (u.getName() == null) {
	        p.sendMessage(main.colorize("&cUser not found by the name of: " + args[0]));
	        return false;
	    }

	    p.sendMessage(main.colorizeNoTag("&6+&r-|&6Lookup&r|--------------------&6+&r"));
	    p.sendMessage(main.colorizeNoTag("Name: " + u.getName()));
	    p.sendMessage(main.colorizeNoTag("DisplayName: " + u.getDisplayName()));
	    p.sendMessage(main.colorizeNoTag("UUID: " + u.getUuid().toString()));
	    p.sendMessage(main.colorizeNoTag("IP: " + u.getIp()));
	    p.sendMessage(main.colorizeNoTag("Location: " + u.getLoc().getBlockX() + ", "
	            + u.getLoc().getBlockY() + ", " + u.getLoc().getBlockZ()));
	    p.sendMessage(main.colorizeNoTag("World: " + u.getWorld().getName()));
	    p.sendMessage(main.colorizeNoTag("Banned: " + u.isBanned()));
	    p.sendMessage(main.colorizeNoTag("&6+&r-|&6Lookup&r|--------------------&6+&r"));
	    return false;
	}


}
