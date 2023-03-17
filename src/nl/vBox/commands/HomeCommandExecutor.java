package nl.vBox.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.vBox.Main;
import nl.vBox.data.dataobjects.Home;
import nl.vBox.data.yml.HomeHandler;
import nl.vBox.utils.enums.LogSeverity;

public class HomeCommandExecutor implements CommandExecutor {

	private Main main = Main.getInstance();
	private HomeHandler hh;

	public HomeCommandExecutor(Main _main) {
		main = _main;
		hh = new HomeHandler();
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (!(s instanceof Player)) {
			main.log("Command is for players only!", LogSeverity.WARN);
			return true;
		} else {
			if (c.getName().equalsIgnoreCase("home")) {
				if (s.hasPermission("vBox.homes")) {
					if (args.length == 0 || args.length > 1) {
						if (args.length == 0)
							s.sendMessage(main.colorize("&cMissing Arguments! Usage: /home <name>"));
						if (args.length > 1)
							s.sendMessage(main.colorize("&cToo Much Arguments! Usage: /home <name>"));
						return true;
					} else {
						for (Home h : hh.ReadList((Player) s)) {
							String name = args[0];
							if (h.getName().equalsIgnoreCase(name)) {
								s.sendMessage(main.colorize("&aTeleporting to home: &r" + h.getName()));
								((Player) s).teleport(h.getCoords());
								return false;
							} else {
								s.sendMessage(main.colorize("&cHome:&r " + args[0]
										+ " &cnot found! use &6/homes&c to check which ones you can use!"));
								return true;
							}
						}
					}
				} else {
					s.sendMessage(main.colorize("&cYou do not have permission to use that command!"));
					return true;
				}
			} else if (c.getName().equalsIgnoreCase("sethome")) {
				if (s.hasPermission("vBox.homes")) {
					if (args.length == 0 || args.length > 1) {
						if (args.length == 0)
							s.sendMessage(main.colorize("&cMissing Arguments! Usage: /sethome <name>"));
						if (args.length > 1)
							s.sendMessage(main.colorize("&cToo Much Arguments! Usage: /sethome <name>"));
						return true;
					} else {
						String name = args[0];
						hh.Create((Player) s, name);
						s.sendMessage(main.colorize("&aHome created!"));
						return false;
					}
				} else {
					s.sendMessage(main.colorize("&cYou do not have permission to use that command!"));
					return true;
				}
			} else if (c.getName().equalsIgnoreCase("delhome")) {
				if (s.hasPermission("vBox.homes")) {
					if (args.length == 0 || args.length > 1) {
						if (args.length == 0)
							s.sendMessage(main.colorize("&cMissing Arguments! Usage: /delhome <name>"));
						if (args.length > 1)
							s.sendMessage(main.colorize("&cToo Much Arguments! Usage: /delhome <name>"));
						return true;
					} else {
						for (Home h : hh.ReadList((Player) s)) {
							String name = args[0];
							if (h.getName().equalsIgnoreCase(name)) {
								hh.Delete((Player) s, name);
								s.sendMessage(
										main.colorize("&aHome: &r" + h.getName() + "&a has been delted succesfully"));
								return false;
							} else {
								s.sendMessage(main.colorize("&cHome:&r " + args[0]
										+ " &cnot found! use &6/homes&c to check which ones you can use!"));
								return true;
							}
						}
					}
				} else {
					s.sendMessage(main.colorize("&cYou do not have permission to use that command!"));
				}
			} else if (c.getName().equalsIgnoreCase("homes")) {
				if (s.hasPermission("vBox.homes")) {
					StringBuilder sb = new StringBuilder();
					for (Home h : hh.ReadList((Player) s)) {
						sb.append(h.getName() + ", ");
					}
					s.sendMessage(main.colorize("&6+&c--&r| &6Homes &r|&c----------------&6+"));
					s.sendMessage(main.colorize("&a" + sb.toString()));
				} else {
					s.sendMessage(main.colorize("&cYou do not have permission to use that command!"));
				}
			}

		}
		return false;
	}

}
