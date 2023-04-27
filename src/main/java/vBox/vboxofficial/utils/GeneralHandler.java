package vBox.vboxofficial.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlBanHandler;
import vBox.vboxofficial.data.yml.YmlConfigHandler;
import vBox.vboxofficial.data.yml.YmlUserHandler;
import vBox.vboxofficial.dtos.Ban;
import vBox.vboxofficial.dtos.User;
import vBox.vboxofficial.dtos.handlers.DtoHandler;

import java.io.File;
import java.util.UUID;

public class GeneralHandler {

    Main main = Main.getInstance();

    public GeneralHandler(Main _main) {
        main = _main;
    }
    YmlConfigHandler ch = new YmlConfigHandler(main);
    public void BanChecker() {
        main.getServer().getScheduler().runTaskLater(main, () -> {
            YmlBanHandler bh = new YmlBanHandler(main);
            YmlUserHandler uh = new YmlUserHandler(main);

            for (File file : uh.getUsersFolder().listFiles()) {
                User u = uh.getUserByUUID(UUID.fromString(file.getName()));
                for (File banFile : bh.getBanFolder(u).listFiles()) {
                    FileConfiguration banCfg = YamlConfiguration.loadConfiguration(banFile);
                    TimerHandler th = new TimerHandler(main);
                    Ban b = DtoHandler.createBanDto(banCfg, main);
                    if (th.isTimeUp(b.getDate())) {
                        bh.setBanActivity(u, b.getHash(), false);
                    }
                }
            }

        }, (20 * 60) * ch.getConfig().getInt("BanCheckerTimer"));
    }
}
