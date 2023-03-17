package vBox.vboxofficial.data.yml;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.handlers.Warps.WarpYmlHandler;
import vBox.vboxofficial.dataobjects.User;
import vBox.vboxofficial.dataobjects.Warp;
import vBox.vboxofficial.dataobjects.handlers.DtoHandler;

public class WarpHandler {

	private Main main = Main.getInstance();

	public WarpHandler(Main _main) {
		main = _main;

	}

	public void Create(String name, User u) {
		Warp w = DtoHandler.createWarpDto(u, name);
		WarpYmlHandler.createNewWarp(w);
	}

	public Warp Read(String name) {
		Warp w = new Warp();
		for (FileConfiguration cfg : WarpYmlHandler.getWarps()) {
			if (cfg.getString("Name").equalsIgnoreCase(name)) {
				Location loc = new Location(main.getServer().getWorld(cfg.getString("World")),
						cfg.getDouble("Location.x"), cfg.getDouble("Location.y"), cfg.getDouble("Location.z"));
				w.setName(cfg.getString("Name"));
				w.setLocation(loc);
			}
		}
		return w;
	}

	public List<Warp> GetAll() {
		List<Warp> warps = new ArrayList<Warp>();
		for (FileConfiguration cfg : WarpYmlHandler.getWarps()) {
			Warp w = DtoHandler.ymlToWarpDto(cfg);
			warps.add(w);
		}
		return warps;
	}

	public void Update(Warp w) {

		for (FileConfiguration cfg : WarpYmlHandler.getWarps()) {
			if (cfg.getString("Name").equalsIgnoreCase(w.getName())) {
				WarpYmlHandler.updateWarp(w);
			} else {
				main.getServer().getPlayer(w.getCreator().getUuid())
						.sendMessage(main.colorize("&cWarp does not exist!"));
			}
		}
	}

	public boolean Exists(String name) {
		boolean exists = false;
		for (FileConfiguration cfg : WarpYmlHandler.getWarps()) {
			if (cfg.getString("Name").equalsIgnoreCase(name)) {
				exists = true;
			}
		}
		return exists;
	}

	public boolean Delete(String name) {
		if (Exists(name)) {
			Warp w = new Warp();
			for (Warp warp : GetAll()) {
				if (w.getName().equalsIgnoreCase(warp.getName())) {
					WarpYmlHandler.deleteWarp(w);
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
}
