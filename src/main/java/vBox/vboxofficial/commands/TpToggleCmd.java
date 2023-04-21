package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlTpHandler;
import vBox.vboxofficial.dtos.Teleport;
import vBox.vboxofficial.dtos.User;
import vBox.vboxofficial.dtos.handlers.DtoHandler;
import vBox.vboxofficial.utils.LogSeverity;

public class TpToggleCmd implements CommandExecutor {

    private Main main = Main.getInstance();

    public TpToggleCmd(Main _main) {
        main = _main;
    }

    YmlTpHandler tph = new YmlTpHandler(main);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("tptoggle")) {
            return false;
        }
        if (!(sender instanceof Player)) {
            main.log("You cannot use this command as Console!", LogSeverity.INFO);
            return false;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("vBox.teleport.tptoggle")) {
            p.sendMessage(main.colorize("&cYou do not have permission to do that!"));
            return false;
        }
        User u = DtoHandler.createUserDto(p);
        if (tph.isToggled(DtoHandler.createUserDto(p))) {
            tph.unToggle(u);
            p.sendMessage(main.colorize("&aTping to you has been allowed again!"));
        } else {
            tph.createTpToggleFile(u);
            p.sendMessage(main.colorize("&aTping to you disallowed!"));
        }
        return false;
    }

}
