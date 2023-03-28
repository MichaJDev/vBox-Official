/**
 * 
 */
package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlSpawnHandler;
import vBox.vboxofficial.dtos.User;
import vBox.vboxofficial.dtos.handlers.DtoHandler;
import vBox.vboxofficial.utils.LogSeverity;

public class SetSpawnCmd implements CommandExecutor {

	private Main main = Main.getInstance();

	public SetSpawnCmd(Main _main) {
		main = _main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!command.getName().equalsIgnoreCase("setspawn")) {
			return false;
		}
		
		if (!(sender instanceof Player)) {
			main.log("You are not allowed to use this command on console!", LogSeverity.WARN);
			return false;
		}
		
		Player p = (Player) sender;
		if (!p.hasPermission("vBox.moderation")) {
			p.sendMessage(main.colorize("&cYou do not have the permission to use this command!"));
			return false;
		}
		
		if (args.length > 0) {
			p.sendMessage(main.colorize("&cToo many arguments!"));
			return true;
		}
		
		YmlSpawnHandler sh = new YmlSpawnHandler(main);
		User u = DtoHandler.createUserDto(p);
		sh.updateSpawn(u);
		p.sendMessage(main.colorize("&aSpawn set!"));
		
		return true;
	}

}
