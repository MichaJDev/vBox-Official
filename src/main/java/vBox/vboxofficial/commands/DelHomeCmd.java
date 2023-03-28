package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlHomeHandler;
import vBox.vboxofficial.dtos.Home;
import vBox.vboxofficial.dtos.handlers.DtoHandler;
import vBox.vboxofficial.utils.LogSeverity;

public class DelHomeCmd implements CommandExecutor {

	private Main main = Main.getInstance();
	private YmlHomeHandler hh;

	public DelHomeCmd(Main _main) {
		main = _main;
		hh = new YmlHomeHandler(_main);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			main.log("You are not allowed to use this command as Console!", LogSeverity.WARN);
			return true;
		}

		Player player = (Player) sender;

		if (args.length > 1) {
			player.sendMessage(main.colorize("&cToo many arguments, Usage /sethome <name>"));
			return true;
		} else if (args.length < 1) {
			player.sendMessage(main.colorize("&cToo little arguments, Usage /sethome <name>"));
			return true;
		}

		Home home = hh.getHome(DtoHandler.createUserDto(player), args[0]);

		if (home.getHash() != null) {
			hh.delete(home);
			player.sendMessage(main.colorize("&4Home successfully deleted&r: " + home.getName()));
		} else {
			player.sendMessage(main.colorize("&cHome not found!"));
		}

		return true;
	}

}
