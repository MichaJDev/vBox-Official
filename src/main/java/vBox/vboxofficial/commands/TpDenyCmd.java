package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlTpHandler;
import vBox.vboxofficial.dtos.Teleport;
import vBox.vboxofficial.dtos.handlers.DtoHandler;
import vBox.vboxofficial.utils.LogSeverity;

public class TpDenyCmd implements CommandExecutor {


	private Main main = Main.getInstance();
	
	public TpDenyCmd(Main _main) {
		main = _main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!command.getName().equalsIgnoreCase("tpdeny")){
			return false;
		}
		if (!(sender instanceof Player)) {
			main.log("You cannot use this command as Console!", LogSeverity.INFO);
			return false;
		}
		Player p = (Player) sender;
		if (!p.hasPermission("vBox.teleport.tpdeny")) {
			p.sendMessage(main.colorize("&cYou do not have permission to do that!"));
			return false;
		}

		YmlTpHandler tph = new YmlTpHandler(main);
		Teleport tp = tph.getTp(DtoHandler.createUserDto(p));
		if(tp.getHash() != null){
			 Player teleporter = main.getServer().getPlayer(tp.getTeleporter().getUuid());
			Player target = main.getServer().getPlayer((tp.getTarget().getUuid()));
			teleporter.sendMessage(main.colorize("&c" + target.getName() + ", denied your Tpa request."));
			target.sendMessage(main.colorize("&aTpa request denied"));
			tph.delete(tp);
		}else{
			p.sendMessage(main.colorize("&cNo tpa-request found on your name."));
		}
		return false;
	}

}
