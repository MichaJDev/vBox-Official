package nl.vBox.data.yml;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import nl.vBox.Main;
import nl.vBox.data.dataobjects.User;
import nl.vBox.data.dataobjects.Warp;
import nl.vBox.data.dataobjects.handlers.DtoHandler;
import nl.vBox.data.yml.handlers.Warps.WarpYmlHandler;

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
			if (cfg.getString("Name") == name) {
				Location loc = new Location(main.getServer().getWorld(cfg.getString("World")),
						cfg.getDouble("Location.x"), cfg.getDouble("Location.y"), cfg.getDouble("Location.z"));
				w.setName(cfg.getString("Name"));
				w.setLocation(loc);
			}
		}
		return w;
	}

	public void Update(Warp w) {

		for (FileConfiguration cfg : WarpYmlHandler.getWarps()) {
			if (cfg.getString("Name") == w.getName()) {
				WarpYmlHandler.updateWarp(w);
			} else {
				main.getServer().getPlayer(w.getCreator().getUuid())
						.sendMessage(main.colorize("&cWarp does not exist!"));
			}
		}
	}
}
