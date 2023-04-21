package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlBackHandler;
import vBox.vboxofficial.data.yml.YmlTpHandler;
import vBox.vboxofficial.dtos.User;
import vBox.vboxofficial.dtos.handlers.DtoHandler;
import vBox.vboxofficial.utils.LogSeverity;

public class TpHereCmd implements CommandExecutor {

    private Main main = Main.getInstance();

    public TpHereCmd(Main _main) {
        main = _main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("tphere")) {
            if (!(sender instanceof Player)) {
                main.log("This command cannot be used by Console!", LogSeverity.INFO);
                return false;
            }
            Player p = (Player) sender;

            if (!p.hasPermission("vBox.teleport.tphere")) {
                p.sendMessage(main.colorize("&cYou do not have permission to do that!"));
                return false;
            }

            User u = DtoHandler.createUserDto(p);
            YmlTpHandler tph = new YmlTpHandler(main);
            if (tph.isToggled(u)) {
                p.sendMessage(main.colorize("This player has Tp off!"));
                return false;
            }

            Player tp = main.getServer().getPlayer(args[0]);
            if (tp != null) {
                YmlBackHandler bh = new YmlBackHandler(main);
                tp.sendMessage(main.colorize("&aTeleporting to: &r" + p.getName()));
                p.sendMessage(main.colorize("&aTelorting: &r" + tp.getName() + " &ato you!"));
                tp.teleport(p.getLocation());
                bh.createBackFile(DtoHandler.createBackDto(tp));
                tp.sendMessage(main.colorize("&aCreated back location you can use &r/back to return to your previous location."));
            } else {
                p.sendMessage(main.colorize("&cPlayer not found!"));
            }
        }
        return false;
    }
}

