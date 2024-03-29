package vBox.vboxofficial.dtos.handlers;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlConfigHandler;
import vBox.vboxofficial.dtos.*;
import vBox.vboxofficial.utils.TimerHandler;

public class DtoHandler {

    public static User createUserDto(Player p) {
        User u = new User();
        u.setName(p.getName());
        u.setDisplayName(p.getDisplayName());
        u.setUuid(p.getUniqueId());
        u.setIp(p.getAddress().getAddress().toString().replace("/", "").replace(":", ""));
        u.setLoc(p.getLocation());
        u.setWorld(p.getWorld());
        return u;
    }

    public static User createUserDto(FileConfiguration cfg, Main main) {
        User u = new User();
        Location loc = new Location(main.getServer().getWorld(cfg.getString("World")), cfg.getDouble("Location.x"),
                cfg.getDouble("Location.y"), cfg.getDouble("Location.z"));
        u.setName(cfg.getString("Name"));
        u.setDisplayName(cfg.getString("DisplayName"));
        u.setUuid(UUID.fromString(cfg.getString("UUID")));
        u.setIp(cfg.getString("IP"));
        u.setLoc(loc);
        u.setWorld(loc.getWorld());
        return u;
    }

    public static Home createHomeDto(Player p, String name) {
        Home h = new Home();
        h.setHash(UUID.randomUUID().toString().substring(0, 5));
        h.setName(name);
        h.setOwner(createUserDto(p));
        h.setLoc(p.getLocation());
        return h;
    }

    public static Home createHomeDto(FileConfiguration cfg, Main main) {
        Home h = new Home();
        Location loc = new Location(main.getServer().getWorld(cfg.getString("World")), cfg.getDouble("Location.x"),
                cfg.getDouble("Location.y"), cfg.getDouble("Location.z"));
        h.setHash(cfg.getString("Hash"));
        h.setName(cfg.getString("Name"));
        h.setLoc(loc);
        h.setOwner(DtoHandler.createUserDto(main.getServer().getPlayer(UUID.fromString(cfg.getString("Owner")))));
        return h;
    }

    public static Spawn createSpawnDto(Player p) {
        Spawn s = new Spawn();
        s.setLoc(p.getLocation());
        return s;
    }

    public static Spawn createSpawnDto(FileConfiguration cfg, Main main) {
        Spawn s = new Spawn();
        Location loc = new Location(main.getServer().getWorld(cfg.getString("World")), cfg.getDouble("Location.x"),
                cfg.getDouble("Location.y"), cfg.getDouble("Location.z"));
        s.setLoc(loc);
        return s;
    }

    public static Warp createWarpDto(Player p, String name) {
        Warp w = new Warp();
        w.setCreator(createUserDto(p));
        w.setHash(UUID.randomUUID().toString().substring(0, 5));
        w.setLocation(p.getLocation());
        w.setName(name);
        return w;
    }

    public static Warp createWarpDto(FileConfiguration cfg, Main main) {
        Warp w = new Warp();
        Location loc = new Location(main.getServer().getWorld(cfg.getString("World")), cfg.getDouble("Location.x"),
                cfg.getDouble("Location.y"), cfg.getDouble("Location.z"));
        w.setCreator(createUserDto(main.getServer().getPlayer(UUID.fromString(cfg.getString("Hash")))));
        w.setName(cfg.getString("Name"));
        w.setLocation(loc);
        w.setHash(cfg.getString("Hash"));
        return w;
    }

    public static Teleport createTeleportDto(Player tper, Player target, Main main) {
        TimerHandler th = new TimerHandler(main);
        Teleport tp = new Teleport();
        YmlConfigHandler ch = new YmlConfigHandler(main);
        tp.setTeleporter(createUserDto(tper));
        tp.setTarget(createUserDto(target));
        tp.setCoolDownTime(th.getDate(ch.getConfig().getString("TpaCooldown")));
        tp.setKeepAliveTime(th.getDate(ch.getConfig().getString("KeepAlive")));
        tp.setHash(UUID.randomUUID().toString().substring(0, 5));
        return tp;
    }

    public static Teleport createTeleportDto(FileConfiguration cfg, Main main) {
        Teleport tp = new Teleport();
        tp.setTeleporter(
                DtoHandler.createUserDto(main.getServer().getPlayer(UUID.fromString(cfg.getString("Teleporter")))));
        tp.setTarget(DtoHandler.createUserDto(main.getServer().getPlayer(UUID.fromString(cfg.getString("Target")))));
        tp.setCoolDownTime(cfg.getString("Cooldown"));
        tp.setKeepAliveTime(cfg.getString("KeepAlive"));
        tp.setHash(cfg.getString("Hash"));
        return tp;
    }

    public static Back createBackDto(Player p) {
        Back b = new Back();
        b.setUser(createUserDto(p));
        b.setLocation(p.getLocation());
        return b;
    }

    public static Back createBackDto(FileConfiguration cfg, Main main) {
        Back b = new Back();
        b.setUser(DtoHandler.createUserDto(main.getServer().getPlayer(UUID.fromString(cfg.getString("User")))));
        b.setLocation(DtoHandler.createUserDto(main.getServer().getPlayer(UUID.fromString(cfg.getString("User")))).getLoc());
        return b;
    }

    public static Ban createBanDto(Player p, Player target, String reason, String time){
        Ban b = new Ban();
        b.setBanner(createUserDto(p));
        b.setBanned(createUserDto(target));
        b.setReason(reason);
        b.setDate(time);
        b.setHash(UUID.randomUUID().toString().substring(0, 5));
        b.setActive(true);
        return b;
    }

    public static Ban createBanDto(FileConfiguration cfg , Main main){
        Ban b = new Ban();;
        Player p = main.getServer().getPlayer(UUID.fromString(cfg.getString("Banner")));
        Player target = main.getServer().getPlayer(UUID.fromString(cfg.getString("Banned")));
        TimerHandler th = new TimerHandler(main);
        b.setBanner(createUserDto(p));
        b.setBanned(createUserDto(target));
        b.setReason(cfg.getString("Reason"));
        b.setDate(cfg.getString("Date"));
        b.setHash(cfg.getString("Hash"));
        b.setActive(cfg.getBoolean("Active"));
        return b;
    }

}
