package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlBanHandler;
import vBox.vboxofficial.dtos.Ban;
import vBox.vboxofficial.dtos.User;
import vBox.vboxofficial.dtos.handlers.DtoHandler;

import java.util.ArrayList;
import java.util.List;

public class UnBanCmd implements CommandExecutor {

    private Main main = Main.getInstance();
    public UnBanCmd(Main _main) {
        main = _main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String l, String[] args) {
        if(!command.getName().equalsIgnoreCase("unban")){
            return false;
        }
        if(!sender.hasPermission("vBox.moderation.unban")){
            sender.sendMessage(main.colorize("&cYou do not have permission for that command!"));
            return false;
        }
        if(args.length == 0 || args.length > 1) {
            sender.sendMessage(main.colorize("&c Invalid Arguments, Usage: /unban <name>"));
            return false;
        }
        Player tp = main.getServer().getPlayer(args[1]);
        if(tp != null){
            YmlBanHandler bh = new YmlBanHandler(main);
            User u = DtoHandler.createUserDto(tp);
            List<Ban> bans = new ArrayList<>();
            for(Ban b : bans){
                if(b.isActive()){
                    b.setActive(false);
                    bh.setBanActivity(u, b.getHash(), b.isActive());
                }
            }
            sender.sendMessage(main.colorize("&aPlayer:&r " + tp.getName() + " &a was unbanned."));
            return true;
        }else{
            sender.sendMessage(main.colorize("&cPlayer not found!"));
        }
        return false;
    }
}
