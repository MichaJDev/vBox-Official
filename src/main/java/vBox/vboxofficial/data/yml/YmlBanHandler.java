package vBox.vboxofficial.data.yml;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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

    /**
     * Creates Folder within the Users folder to store Ban files.
     *
     * @param u User Object
     */
    public void createBanFolder(User u) {
        File dir = new File(uh.getUserFolder(u), "Bans");
        if (!dir.exists())
            dir.mkdirs();
    }

    /**
     * Returns directory of the User's banfiles
     * @param u User Object
     * @return Directory that holds the banfiles
     */
    public File getBanFolder(User u) {
        return new File(uh.getUserFolder(u), "Bans");
    }

    /**
     * Creates a new Banfile for the designated User
     * @param b the Ban object necessary to create a Ban file
     */
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

    /**
     * Returns the banfile requested by the hash and the user
     * @param u User object
     * @param hash Hash of the Banfile
     * @return the banfile
     */
    public File getBanFile(User u, String hash) {
        File file = new File(getBanFolder(u), "Ban#" + hash + ".yml");
        return file;
    }

    /**
     * Fills banfile with necessary daya on the player
     * @param b Ban Object
     * @param f File
     */
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

    /**
     * returns a list of all Bans of the designated user
     * @param u User Object
     * @return list of all bans the user ever had
     */
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

    /**
     * Returns a ban object with all stored data on the ban (reason and time included)
     * @param u User Object
     * @param hash Ban Hash Code
     * @return Ban depending on the Hash
     */
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

    /**
     * Sets ban activity to the boolean given
     * @param u User Object
     * @param hash Ban Hash Code
     * @param activity boolean of the activity
     */
    public void setBanActivity(User u, String hash, boolean activity) {
        Ban b = getBan(u, hash);
        b.setActive(activity);
        YmlBanHandler bh = new YmlBanHandler(main);
        updateBan(b);
    }

    /**
     * Checks if User is banned
     * @param u User Object
     * @return returns boolean if they are banned.
     */
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

    /**
     * Returns the latest active ban
     * @param u User Object
     * @return returns active bans
     */
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