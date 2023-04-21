package vBox.vboxofficial.data.yml;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import org.yaml.snakeyaml.Yaml;
import vBox.vboxofficial.Main;
import vBox.vboxofficial.dtos.Teleport;
import vBox.vboxofficial.dtos.User;
import vBox.vboxofficial.dtos.handlers.DtoHandler;

public class YmlTpHandler {

    private Main main = Main.getInstance();

    public YmlTpHandler(Main _main) {
        main = _main;
    }

    public void createTpFolder() {
        File dir = new File(main.getDataFolder(), "Teleports");
        if (!dir.exists())
            dir.mkdirs();
    }

    public File getTpFolder() {
        return new File(main.getDataFolder(), "Teleports");
    }

    public void createTpFile(Teleport tp) {
        File file = new File(getTpFolder(), "TP#" + tp.getHash() + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            fillTpFile(file, tp);
        }
    }

    private void fillTpFile(File file, Teleport tp) {
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.addDefault("Teleporter", tp.getTeleporter().getUuid().toString());
        cfg.addDefault("Target", tp.getTarget().getUuid().toString());
        cfg.addDefault("Cooldown", tp.getCoolDownTime());
        cfg.addDefault("KeepAlive", tp.getKeepAliveTime());
        cfg.addDefault("Hash", tp.getHash());
        cfg.options().copyDefaults(true);
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Teleport getTp(String hash) {
        Teleport tp = new Teleport();
        for (File file : getTpFolder().listFiles()) {
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            if (hash.equals(cfg.getString("Hash"))) {
                tp = DtoHandler.createTeleportDto(cfg, main);
            }
        }
        return tp;
    }

    public Teleport getTp(User u) {
        Teleport tp = new Teleport();
        for (File file : getTpFolder().listFiles()) {
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            if (u.getUuid().toString().equals(cfg.get("Target"))) {
                tp = DtoHandler.createTeleportDto(cfg, main);
            }
        }
        return tp;
    }

    public void delete(Teleport tp) {
        File file = new File(getTpFolder(), "TP#" + tp.getHash() + ".yml");
        if (file.exists())
            file.delete();
    }

    public void addToToggleList(User u) {

    }

    public void createTpToggleMap() {
        File dir = new File(main.getDataFolder(), "tptoggle");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public File getTpToggleMap() {
        return new File(main.getDataFolder(), "tptoggle");
    }

    public void createTpToggleFile(User u) {
        File toggleFile = new File(getTpToggleMap(), u.getUuid().toString() + ".yml");
        if (!toggleFile.exists()) {
            try {
                toggleFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            fillToggleFile(u, toggleFile);
        }
    }

    private void fillToggleFile(User u, File f) {
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        cfg.addDefault("User", u.getUuid());
    }

    private File getToggleFile(User u) {
        return new File(getTpToggleMap(), u.getUuid().toString() + ".yml");
    }

    public boolean isToggled(User u) {
        boolean toggles = false;
        if(getToggleFile(u) != null)
            toggles = true;
        return toggles;
    }

    public void unToggle(User u){
        if(isToggled(u)){
            File f = getToggleFile(u);
            f.delete();
        }
    }
}
