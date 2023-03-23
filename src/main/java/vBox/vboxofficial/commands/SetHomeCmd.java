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
	private YmlHomeHandler hh;

	public SetHomeCmd(Main _main) {
		main = _main;
		hh = new YmlHomeHandler(_main);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			main.log("You are not allowed to use this command on console!", LogSeverity.WARN);
		} else {
			Player p = (Player) sender;

			for (PermissionAttachmentInfo permission : p.getEffectivePermissions()) {
				if ((permission.getPermission().startsWith("vBox.homes")) && (permission.getValue())) {
					int homeAmount = 0;

					try {
						homeAmount = Integer.parseInt(permission.getPermission().replace("vBox.homes", ""));
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (hh.amount(DtoHandler.createUserDto(p)) >= homeAmount) {
						p.sendMessage(main.colorize("You've reached the max amount of homes allowed!"));
					} else if (p.hasPermission("vBox.homes.*") || hh.amount(DtoHandler.createUserDto(p)) < homeAmount
							|| p.isOp()) {
						if (args.length < 1) {
							p.sendMessage(main.colorize("&cToo many arguments, Usage /sethome <name>"));
						} else if (args.length > 1) {
							p.sendMessage(main.colorize("&cToo little arguments, Usage /sethome <name>"));
						} else {
							Home h = DtoHandler.createHomeDto(p, args[0]);
							hh.createHome(h);
							p.sendMessage(main.colorize("&4Succesfully set home&r: " + h.getName()));
						}
					}
				} else {
					p.sendMessage(main.colorize("&cYou have no permission to use that"));
				}
			}

		}
		return false;
	}

}
