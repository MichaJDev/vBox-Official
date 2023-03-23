package vBox.vboxofficial.commands;

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
		StringBuilder sb = new StringBuilder();
		if (!(sender instanceof Player)) {
			main.log("You cannot use this command as Console", LogSeverity.WARN);
		} else {
			Player p = (Player) sender;
			if (p.hasPermission("vBox.homes")) {
				if (args.length < 0) {
					p.sendMessage(main.colorize("Usage: /homes"));
				} else {
					User u = DtoHandler.createUserDto(p);
					if (!hh.getHomes(u).isEmpty()) {

						for (Home h : hh.getHomes(u)) {
							sb.append(h.getName() + ", ");
						}
						p.sendMessage(main.colorizeNoTag("&6+&r-|&6Homes&r|--------------"));
						p.sendMessage(sb.toString());
						p.sendMessage(main.colorizeNoTag("&6+&r-|&6Homes&r|--------------"));
					} else {
						p.sendMessage(main.colorize("&cNo homes found!"));
					}
				}
			} else {
				p.sendMessage(main.colorize("&cYou do not have permission to do that"));
			}
		}
		return false;
	}

}
