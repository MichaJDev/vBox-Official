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
import vBox.vboxofficial.utils.TimerHandler;

public class TpaCmd implements CommandExecutor {

    private Main main = Main.getInstance();

    public TpaCmd(Main _main) {
        main = _main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        YmlTpHandler th = new YmlTpHandler(main);
        if (command.getName().equalsIgnoreCase("tpa")) {
            if (!(sender instanceof Player)) {
                main.log("This command cannot be used by Console", LogSeverity.INFO);
                return false;
            }

            Player p = (Player) sender;
            if (!p.hasPermission("vBox.teleport.tpa")) {
                p.sendMessage(main.colorize("&cYou do not have permission to use that command!"));
                return false;
            }

            if (args.length != 1) {
                p.sendMessage(main.colorize("&cInvalid Arguments! Usage: /tpa <name>"));
                return false;
            }
            Player tp = main.getServer().getPlayer(args[0]);
            if (tp != null) {
                TimerHandler timerHandler = new TimerHandler(main);
                Teleport tpDTO = DtoHandler.createTeleportDto(p, tp, main);
                th.createTpFile(tpDTO);
                tp.sendMessage(
                        main.colorize(p.getName() + " wants to teleport to you, type &a/tpaccept &r or &c/tpdeny"));
                p.sendMessage(main.colorize("&aTeleport request send to " + tp.getName()));
                timerHandler.startDeleteTimer(tpDTO);
            }

        }
        return false;
    }

}
