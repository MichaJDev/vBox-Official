package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlBackHandler;
import vBox.vboxofficial.dtos.handlers.DtoHandler;
import vBox.vboxofficial.utils.LogSeverity;

public class TpCmd implements CommandExecutor {

	private Main main = Main.getInstance();

	public TpCmd(Main _main) {
		main = _main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	    if (command.getName().equalsIgnoreCase("tp")) {
	        if (!(sender instanceof Player)) {
	            main.log("This command cannot be used by Console!", LogSeverity.INFO);
	            return true;
	        }
	        
	        Player p = (Player) sender;
	        if (!p.hasPermission("vBox.teleport.tp")) {
	            p.sendMessage(main.colorize("&cYou do not have permission to use this command!"));
	            return true;
	        }
	        
	        if (args.length == 0) {
	            p.sendMessage(main.colorize("&cUsage: /tp <player>"));
	            return true;
	        }
	        
	        Player tp = main.getServer().getPlayer(args[0]);
	        if (tp != null) {
				YmlBackHandler bh = new YmlBackHandler(main);
				p.sendMessage(main.colorize("&aTeleporting to: &r" + tp.getName()));
	            p.teleport(tp.getLocation());
				bh.createBackFile(DtoHandler.createBackDto(p));
	        } else {
	            p.sendMessage(main.colorize("&cPlayer not found!"));
	        }
	    }
	    
	    return true;
	}


}
