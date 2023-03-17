package nl.vBox.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.vBox.Main;
import nl.vBox.data.dataobjects.Spawn;
import nl.vBox.data.dataobjects.User;
import nl.vBox.data.dataobjects.handlers.DtoHandler;
import nl.vBox.data.yml.SpawnHandler;
import nl.vBox.data.yml.handlers.Spawn.SpawnYmlHandler;
import nl.vBox.utils.enums.LogSeverity;
import nl.vBox.utils.general.LogHandler;

public class SpawnCommandExecutor implements CommandExecutor {
	private Main main = Main.getInstance();
	private SpawnHandler sh;

	public SpawnCommandExecutor(Main _main) {
		main = _main;
		sh = new SpawnHandler(_main);
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {

		if (!(s instanceof Player)) {
			LogHandler.writeToLog("Console tried using spawn");
			main.log("This command is for players only", LogSeverity.WARN);
			return false;
		} else {
			Player p = (Player) s;
			LogHandler.writeToLog("Player: " + p.getUniqueId() + "(" + p.getName() + ") used /spawn");
			if (c.getName().equalsIgnoreCase("spawn")) {
				if (s.hasPermission("vBox.spawn")) {
					Spawn spawn = DtoHandler.ymlToSpawnDto(SpawnYmlHandler.getSpawnCfg());
					s.sendMessage(main.colorize("&aTeleporting to Spawn!"));

					Location loc = new Location(spawn.getWorld(), spawn.getX(), spawn.getY(), spawn.getZ());
					p.teleport(loc);
				} else {
					s.sendMessage(main.colorize("&cYou do not have the permission to use this command!"));
					LogHandler.writeToLog(
							"Player: " + p.getUniqueId() + "(" + p.getName() + ") no Permission for /spawn");
				}
			} else if (c.getName().equalsIgnoreCase("setspawn")) {
				if (s.hasPermission("vBox.spawn.admin")) {
					User u = DtoHandler.createUserDto(p);
					Spawn spawn = DtoHandler.createSpawnDto(u);
					sh.Create(spawn);
					s.sendMessage(main.colorize("&aSpawn has been set!"));
				}
			}
		}
		return false;
	}

}
