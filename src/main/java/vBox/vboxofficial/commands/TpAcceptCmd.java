package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlBackHandler;
import vBox.vboxofficial.data.yml.YmlTpHandler;
import vBox.vboxofficial.dtos.handlers.DtoHandler;
import vBox.vboxofficial.utils.LogSeverity;

public class TpAcceptCmd implements CommandExecutor {

	private Main main = Main.getInstance();

	public TpAcceptCmd(Main _main) {
		main = _main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("tpaccept")) {
			if (!(sender instanceof Player)) {
				main.log("You cannot use this command as Console!", LogSeverity.INFO);
				return false;
			}
			Player p = (Player) sender;
			if (!p.hasPermission("vBox.teleport.tpaccept")) {
				p.sendMessage(main.colorize("&cYou do not have permission to do that!"));
				return false;
			}

			YmlTpHandler tph = new YmlTpHandler(main);

			if(tph.getTp(DtoHandler.createUserDto(p)).getHash() != null){
				YmlBackHandler bh = new YmlBackHandler(main);
				bh.createBackFile(DtoHandler.createBackDto(p));
				p.sendMessage(main.colorize("&aCreated back location you can use &r/back to return to your previous location."));
			}else{
				p.sendMessage(main.colorize("&cNo tpa-request found on your name."));
			}
		}
		return false;
	}

}
