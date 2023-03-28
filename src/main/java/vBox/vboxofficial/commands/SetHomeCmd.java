package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlHomeHandler;
import vBox.vboxofficial.dtos.Home;
import vBox.vboxofficial.dtos.handlers.DtoHandler;
import vBox.vboxofficial.utils.LogSeverity;

public class SetHomeCmd implements CommandExecutor {

	private Main main = Main.getInstance();

	public SetHomeCmd(Main _main) {
		main = _main;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		YmlHomeHandler hh = new YmlHomeHandler(main);
		if (!(sender instanceof Player)) {
			main.log("You are not allowed to use this command on console!", LogSeverity.WARN);
			return false;
		}

		Player p = (Player) sender;

		boolean hasHomePermission = false;
		int homeAmount = 0;
		for (PermissionAttachmentInfo permission : p.getEffectivePermissions()) {
			String perm = permission.getPermission();
			if (perm.startsWith("vBox.homes") && permission.getValue()) {
				try {
					homeAmount = Integer.parseInt(perm.replace("vBox.homes", ""));
					hasHomePermission = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}

		if (!hasHomePermission) {
			p.sendMessage(main.colorize("&cYou have no permission to use that"));
			return false;
		}

		if (hh.amount(DtoHandler.createUserDto(p)) >= homeAmount) {
			p.sendMessage(main.colorize("You've reached the max amount of homes allowed!"));
			return false;
		}

		if (p.hasPermission("vBox.homes.*") || hh.amount(DtoHandler.createUserDto(p)) < homeAmount || p.isOp()) {
			if (args.length != 1) {
				p.sendMessage(main.colorize("&cUsage: /sethome <name>"));
				return false;
			}

			Home h = DtoHandler.createHomeDto(p, args[0]);
			hh.createHome(h);
			p.sendMessage(main.colorize("&4Successfully set home: " + h.getName()));
		} else {
			p.sendMessage(main.colorize("&cYou have no permission to use that"));
		}

		return false;
	}

}
