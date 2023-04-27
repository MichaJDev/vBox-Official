package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import vBox.vboxofficial.Main;

public class WarnCmd implements CommandExecutor {

    private Main main = Main.getInstance();

    public WarnCmd(Main _main){
        main = _main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String l, String[] args) {
        return false;
    }
}
