package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlBackHandler;
import vBox.vboxofficial.dtos.Back;
import vBox.vboxofficial.dtos.handlers.DtoHandler;
import vBox.vboxofficial.utils.LogSeverity;

public class BackCmd implements CommandExecutor {

    private Main main = Main.getInstance();

    public BackCmd(Main _main) {
        main = _main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("back")) {
            return false;
        }
        if (!(sender instanceof Player)) {
            main.log("You are not allowed to use this command as Console!", LogSeverity.WARN);
            return false;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("vBox.teleport.back")) {
            p.sendMessage(main.colorize("&cYou do not have permission to use this command!"));
            return false;
        }
        YmlBackHandler bh = new YmlBackHandler(main);
        if(bh.getBack(p) == null){
            p.sendMessage(main.colorize("&cYou dont have a location stored to go back to!"));
            return false;
        }

        p.sendMessage(main.colorize("Teleporting you back to previous location"));
        p.teleport(bh.getBack(p).getLocation());

        return false;
    }

}
