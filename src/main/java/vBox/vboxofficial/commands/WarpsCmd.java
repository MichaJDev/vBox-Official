package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlWarpHandler;
import vBox.vboxofficial.dtos.Warp;
import vBox.vboxofficial.utils.LogSeverity;

public class WarpsCmd implements CommandExecutor {

	private Main main = Main.getInstance();

	public WarpsCmd(Main _main) {
		main = _main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		StringBuilder sb = new StringBuilder();
		YmlWarpHandler wh = new YmlWarpHandler(main);
		if (command.getName().equalsIgnoreCase("warps")) {
			if (sender instanceof Player) {
				for (Warp w : wh.getWarps()) {
					sb.append(w.getName() + ", ");
				}
				main.log("+-|Warps|----------------------+", LogSeverity.INFO);
				main.log(sb.toString().substring(0, sb.length() - 2), LogSeverity.INFO);
				main.log("+-|Warps|----------------------+", LogSeverity.INFO);
			}else {
				Player p = (Player)sender;
				for (Warp w : wh.getWarps()) {
					sb.append(w.getName() + ", ");
				}
				p.sendMessage(main.colorizeNoTag("&6+&r-|&6Warps&r|-------------------------+"));
				p.sendMessage(main.colorizeNoTag(sb.toString().substring(0, sb.length() - 2)));
				p.sendMessage(main.colorizeNoTag("&6+&r-|&6Warps&r|-------------------------+"));
			}
		}
		return false;
	}

}
