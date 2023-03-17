package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.UserHandler;
import vBox.vboxofficial.dataobjects.User;

public class LookupCommandExecutor implements CommandExecutor {

	private Main main = Main.getInstance();
	private UserHandler uh;
	public LookupCommandExecutor(Main _main) {
		main = _main;
		uh = new UserHandler();
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (s instanceof Player) {
			if (s.hasPermission("vBox.moderation")) {
				if (args.length == 0 || args.length > 1) {
					if (args.length == 0)
						s.sendMessage(main.colorize("&cMissing Arguments! Usage: /lookup <name>"));
					if (args.length > 1)
						s.sendMessage(main.colorize("&cToo Much Arguments! Usage: /lookup <name>"));
					return true;
				} else {
					for(User u : uh.getAll()) {
						String name = args[0];
						if(u.getName().equalsIgnoreCase(name)) {
							
						}else {
							s.sendMessage(main.colorize("&c User not found!"));
							return true;
						}
					}
				}
			} else {

			}
		} else {

		}
		return false;
	}

}
