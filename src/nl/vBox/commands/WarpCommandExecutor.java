package nl.vBox.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import nl.vBox.Main;

public class WarpCommandExecutor implements CommandExecutor {

	Main main = Main.getInstance();
	
	public WarpCommandExecutor(Main _main) {
		main = _main;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (s.hasPermission("vBox.warps")) {
			if (c.getName().length() == 0) {
				s.sendMessage(main.colorize("&cUsage: /warp <name>"));
			}else if(c.getName().length() == 1) {
				
			}
		} else {
			s.sendMessage(main.colorize("&cYou do not have the permission to use that command!"));
		}
		return false;
	}

}
