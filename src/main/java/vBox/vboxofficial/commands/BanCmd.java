package vBox.vboxofficial.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlBanHandler;
import vBox.vboxofficial.dtos.Ban;
import vBox.vboxofficial.dtos.handlers.DtoHandler;
import vBox.vboxofficial.utils.LogSeverity;

public class BanCmd implements CommandExecutor {

    private Main main = Main.getInstance();

    public BanCmd(Main _main) {
        main = _main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equalsIgnoreCase("ban")) {
            return false;
        }
        if (!(sender instanceof Player)) {
            main.log("You cannot use this command as Console!", LogSeverity.INFO);
            return false;
        }
        Player p = (Player) sender;
        Player target = main.getServer().getPlayer(args[0]);
        if (target == null) {
            p.sendMessage(main.colorize("&cPlayer not found!"));
            return false;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            sb.append(args[i] + " ");
        }
        String reason = sb.substring(0, sb.length() - 1);
        if (!p.hasPermission("vBox.moderation.ban")) {
            p.sendMessage("&cYou have no permission to use  that command!");
            return false;
        }
        if (args.length == 0) {
            p.sendMessage(main.colorize("&cToo little arguments"));
            return false;
        }
        String time = args[1];

        Ban b = DtoHandler.createBanDto(p, target, reason, time);
        YmlBanHandler bh = new YmlBanHandler(main);
        bh.createBanFile(b);
        p.sendMessage(main.colorize("&aBanning:  &r" + "&a #" + b.getHash() + "&6 | " + b.getBanned().getName() + "&a for: &r + " + b.getReason() + "&a Until:&r " + b.getDate()));
        target.kickPlayer("Banned for: " + b.getReason() + "\nby: " + p.getName());
        main.getServer().broadcastMessage(main.colorize(target.getName() + " &a got banned by: &r" + p.getName() + " &aReason: &r" + b.getReason()));
        return false;
    }
}
