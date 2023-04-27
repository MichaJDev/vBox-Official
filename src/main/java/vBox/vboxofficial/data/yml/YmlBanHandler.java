package vBox.vboxofficial.data.yml;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;
import vBox.vboxofficial.Main;
import vBox.vboxofficial.dtos.Ban;
import vBox.vboxofficial.dtos.User;
import vBox.vboxofficial.dtos.handlers.DtoHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YmlBanHandler {

    private Main main = Main.getInstance();

    public YmlBanHandler(Main _main) {
        main = _main;
    }

    YmlUserHandler uh = new YmlUserHandler(main);

    public void createBanFolder(User u) {
        File dir = new File(uh.getUserFolder(u), "Bans");
        if (!dir.exists())
            dir.mkdirs();
    }

    public File getBanFolder(User u) {
        return new File(uh.getUserFolder(u), "Bans");
    }

    public void createBanFile(Ban b) {
        if (!getBanFolder(b.getBanned()).exists()) {
            createBanFolder(b.getBanned());
        }
        File file = new File(getBanFolder(b.getBanned()), "Ban#" + b.getHash() + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            fillBanFile(b, file);
        }
    }

    public File getBanFile(User u, String hash) {
        File file = new File(getBanFolder(u), "Ban#" + hash + ".yml");
        return file;
    }

    private void fillBanFile(Ban b, File f) {
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        cfg.addDefault("Banner", b.getBanner().getUuid().toString());
        cfg.addDefault("Banned", b.getBanned().getUuid().toString());
        cfg.addDefault("Reason", b.getReason());
        cfg.addDefault("Date", b.getDate());
        cfg.addDefault("Active", b.isActive());
        cfg.options().copyDefaults(true);
        try {
            cfg.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Ban> getBans(User u) {
        List<Ban> banList = new ArrayList<Ban>();
        if (getBanFolder(u).listFiles().length > 0) {
            for (File f : getBanFolder(u).listFiles()) {
                FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
                Ban b = DtoHandler.createBanDto(cfg, main);
                banList.add(b);
            }
        }
        return banList;
    }

    public Ban getBan(User u, String hash) {
        Ban b = new Ban();
        for (File f : getBanFolder(u).listFiles()) {
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
            if (cfg.getString("Hash").equals(hash)) {
                b = DtoHandler.createBanDto(cfg, main);
            }
        }
        return b;
    }

    private void updateBan(Ban b) {
        File file = getBanFile(b.getBanned(), b.getHash());
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set("Active", b.isActive());
        cfg.options().copyDefaults(true);
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBanActivity(User u, String hash, boolean activity) {
        Ban b = getBan(u, hash);
        b.setActive(activity);
        YmlBanHandler bh = new YmlBanHandler(main);
        updateBan(b);
    }

    public boolean isBanned(User u) {
        File dir = getBanFolder(u);
        boolean isBanned = false;
        for (File file : dir.listFiles()) {
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            if (cfg.getBoolean("Active")) {
                isBanned = true;
            }
        }
        return isBanned;
    }

    public Ban getActiveBan(User u) {
        Ban b = new Ban();
        for (File file : getBanFolder(u).listFiles()) {
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            if (cfg.getBoolean("Active")) {
                b = DtoHandler.createBanDto(cfg, main);
            }

        }
        return b;
    }
}