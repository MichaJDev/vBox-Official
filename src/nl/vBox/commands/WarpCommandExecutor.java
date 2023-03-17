package nl.vBox.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.vBox.Main;
import nl.vBox.data.dataobjects.User;
import nl.vBox.data.dataobjects.Warp;
import nl.vBox.data.dataobjects.handlers.DtoHandler;
import nl.vBox.data.yml.WarpHandler;
import nl.vBox.utils.enums.LogSeverity;

public class WarpCommandExecutor implements CommandExecutor {

	Main main = Main.getInstance();
	WarpHandler wh;

	public WarpCommandExecutor(Main _main) {
		main = _main;
		wh = new WarpHandler(main);
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {

		// warp <args>

		if (c.getName().equalsIgnoreCase("warp")) {
			if (!(s instanceof Player)) {
				if (s.hasPermission("vBox.warps")) {
					Player p = (Player) s;
					if (c.getName().length() == 0) {
						s.sendMessage(main.colorize("&cUsage: /warp <name>"));
						return false;
					} else if (c.getName().length() == 1) {
						if (wh.Exists(args[0])) {
							p.sendMessage(main.colorize("&aTeleporting to warp: &6" + args[0]));
							p.teleport(wh.Read(args[0]).getLocation());
							return false;
						} else {
							p.sendMessage(main.colorize("&cWarp: &r" + args[0] + "&c does not exist"));
							return true;
						}
					} else {
						s.sendMessage(main.colorize("&cUsage: /warp <name>"));
						return true;
					}
				} else {
					s.sendMessage(main.colorize("&cYou do not have permission for that!"));
					return false;
				}
			}

			// Warps

		} else if (c.getName().equalsIgnoreCase("warps")) {
			if (!(s instanceof Player)) {
				main.log("This Command is only for Players", LogSeverity.WARN);
				return true;
			} else {
				if (s.hasPermission("vBox.warps")) {
					if (args.length == 0) {
						StringBuilder sb = new StringBuilder();
						for (Warp w : wh.GetAll()) {
							sb.append(w.getName() + ", ");
						}
						s.sendMessage(main.colorize("&6+&c--&r| &6Warps &r|&c----------------&6+"));
						s.sendMessage(main.colorize("&c" + sb.toString()));
						return false;

					} else {
						s.sendMessage(main.colorize("&cUsage: /warps"));
						return true;
					}
				} else {
					s.sendMessage(main.colorize("&cYou do not have permission for that!"));
					return false;
				}
			}

			// setwarp <args>

		} else if (c.getName().equalsIgnoreCase("setwarp")) {
			if (!(s instanceof Player)) {
				main.log("This Command is only for Players", LogSeverity.WARN);
				return true;
			} else {
				if (s.hasPermission("vBox.warps.admin")) {
					if (args.length == 0 || args.length < 1) {
						if (args.length == 0)
							s.sendMessage(main.colorize("&cMissing Arguments! Usage: /setwarp <name>"));
						if (args.length > 1)
							s.sendMessage(main.colorize("&cToo Much Arguments! Usage: /setwarp <name>"));
						return true;
					} else {
						String name = args[0];
						User u = DtoHandler.createUserDto((Player) s);
						wh.Create(name, u);
						return false;
					}
				} else {
					s.sendMessage(main.colorize("&cYou do not have permission for that!"));
					return false;
				}
			}

			// delwarp <name>
		} else if (c.getName().equalsIgnoreCase("delwarp")) {
			if (!(s instanceof Player)) {
				main.log("This Command is only for Players", LogSeverity.WARN);
				return true;
			} else {
				if (s.hasPermission("vBox.warps.admin")) {
					if (args.length == 0 || args.length < 1) {
						if (args.length == 0)
							s.sendMessage(main.colorize("&cMissing Arguments! Usage: /delwarp <name>"));
						if (args.length > 1)
							s.sendMessage(main.colorize("&cToo Much Arguments! Usage: /delwarp <name>"));
						return true;
					} else {
						String name = args[0];
						if (wh.Delete(name)) {
							s.sendMessage(main.colorize("&aWarp succesfully deleted&6 | &r" + name + " &6| "));
						} else {
							s.sendMessage(main.colorize("&cWarp not found : &r" + name));
						}
						return false;
					}
				}
			}
		}
		return false;
	}
}