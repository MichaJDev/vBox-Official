package vBox.vboxofficial.data.yml;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import vBox.vboxofficial.Main;
import vBox.vboxofficial.dtos.Back;
import vBox.vboxofficial.dtos.User;
import vBox.vboxofficial.dtos.handlers.DtoHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class YmlBackHandler {


    private Main main = Main.getInstance();

    public YmlBackHandler(Main _main) {
        main = _main;
    }

    public void createBackFolder() {
        File dir = new File(main.getDataFolder(), "backs");
        if (!dir.exists())
            dir.mkdirs();
    }

    public File getBacksFolder() {
        File dir = new File(main.getDataFolder(), "backs");
        if (!dir.exists())
            return null;
        return dir;
    }

    public void createBackFile(Back b) {
        File file = new File(getBacksFolder(), b.getUser().getName() + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            fillBackFile(b, file);
        }else{
            try{
                file.createNewFile();

            }catch(IOException e){
                e.printStackTrace();
            }
            fillBackFile(b,file);
        }
    }

    private void fillBackFile(Back b, File f) {
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        cfg.addDefault("User", b.getUser().getUuid());
        cfg.addDefault("Location.x", b.getLocation().getBlockX());
        cfg.addDefault("Location.y", b.getLocation().getBlockY());
        cfg.addDefault("Location.z", b.getLocation().getBlockZ());
        cfg.options().copyDefaults(true);
        try {
            cfg.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getBackFile(Back b){
        File f = new File(getBackFile(b), b.getUser().getName() + ".yml");
        if(!f.exists())
            return null;
        return f;
    }

    public Back getBack(Player p){
        if(!getBackFile(DtoHandler.createBackDto(p)).exists())
            return null;
        return DtoHandler.createBackDto(p);
    }

}
