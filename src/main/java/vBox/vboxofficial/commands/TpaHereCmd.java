package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlBackHandler;
import vBox.vboxofficial.data.yml.YmlTpHandler;
import vBox.vboxofficial.dtos.Teleport;
import vBox.vboxofficial.dtos.User;
import vBox.vboxofficial.dtos.handlers.DtoHandler;
import vBox.vboxofficial.utils.LogSeverity;
import vBox.vboxofficial.utils.TimerHandler;

public class TpaHereCmd implements CommandExecutor {

    private Main main = Main.getInstance();

    public TpaHereCmd(Main _main) {
        main = _main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            main.log("This command cannot be used by Console", LogSeverity.INFO);
            return false;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("vBox.teleport.tpahere")) {
            p.sendMessage(main.colorize("&cYou do not have permission to use that command!"));
            return false;
        }
        if (args.length != 1) {
            p.sendMessage(main.colorize("&cInvalid Arguments! Usage: /tpa <name>"));
            return false;
        }
        YmlTpHandler tph = new YmlTpHandler(main);
        User u = DtoHandler.createUserDto(p);
        if (tph.isToggled(u)) {
            p.sendMessage(main.colorize("This player has Tp off!"));
            return false;
        }
        Player tp = main.getServer().getPlayer(args[0]);
        if (tp != null) {
            YmlBackHandler bh = new YmlBackHandler(main);
            TimerHandler timerHandler = new TimerHandler(main);
            Teleport tpDTO = DtoHandler.createTeleportDto(tp, p, main);
            tph.createTpFile(tpDTO);
            tp.sendMessage(
                    main.colorize(p.getName() + " wants you to teleport to them, type &a/tpaccept &r or &c/tpdeny"));
            p.sendMessage(main.colorize("&aTeleport request send to " + tp.getName()));
            timerHandler.startDeleteTimer(tpDTO);
        }
        return false;
    }

}
