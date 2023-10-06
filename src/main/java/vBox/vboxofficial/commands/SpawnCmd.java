package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlSpawnHandler;
import vBox.vboxofficial.dtos.Spawn;
import vBox.vboxofficial.utils.LogSeverity;

public class SpawnCmd implements CommandExecutor {

	Main main = Main.getInstance();

	public SpawnCmd(Main _main) {
		main = _main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	    YmlSpawnHandler sh = new YmlSpawnHandler(main);
	    if (command.getName().equalsIgnoreCase("spawn")) {
	        if (!(sender instanceof Player)) {
	            main.log("You are not allowed to use this command as console", LogSeverity.WARN);
	            return true;
	        }

	        Player p = (Player) sender;
	        if (!p.hasPermission("vBox.spawn")) {
	            p.sendMessage(main.colorize("&cYou do not have permission to use this command!"));
	            return true;
	        }

	        if (args.length > 0) {
	            p.sendMessage(main.colorize("&cToo many arguments: Usage /spawn"));
	            return true;
	        }

	        Spawn s = sh.getSpawn();
	        p.teleport(s.getLoc());
	        p.sendMessage(main.colorize("&aTeleported to Spawn!"));
	    }

	    return true;
	}


}
