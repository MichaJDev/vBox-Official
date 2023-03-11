package nl.vBox.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.vBox.Main;
import nl.vBox.data.yml.WarpHandler;

public class WarpCommandExecutor implements CommandExecutor {

	Main main = Main.getInstance();
	WarpHandler wh;

	public WarpCommandExecutor(Main _main) {
		main = _main;
		wh = new WarpHandler(main);
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (s.hasPermission("vBox.warps")) {
			Player p = (Player) s;
			if (c.getName().length() == 0) {
				s.sendMessage(main.colorize("&cUsage: /warp <name>"));
			} else if (c.getName().length() == 1) {
				if(wh.Exists(args[0])) {
					p.sendMessage(main.colorize("&aTeleporting to warp: &6" + args[0]));
					p.teleport(wh.Read(args[0]).getLocation());
				}
			}
		} else {
			s.sendMessage(main.colorize("&cYou do not have the permission to use that command!"));
		}
		return false;
	}

}
