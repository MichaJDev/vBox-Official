package nl.vBox.data.yml;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import nl.vBox.Main;
import nl.vBox.data.dataobjects.Home;
import nl.vBox.data.dataobjects.User;
import nl.vBox.data.dataobjects.handlers.DtoHandler;
import nl.vBox.data.yml.handlers.Users.HomeYmlHandler;

public class HomeHandler {

	private static Main main = Main.getInstance();

	public void Create(Player p, String name) {
		User u = DtoHandler.createUserDto(p);
		HomeYmlHandler.createUserHomeFile(u, name);
	}

	public Home Read(Player p, String name) {
		User u = DtoHandler.createUserDto(p);
		Home h = new Home();
		for (FileConfiguration cfg : HomeYmlHandler.getUserHomeListCfg(u)) {
			if (cfg.getString("Home.Name") == name) {
				h = DtoHandler.ymlHomeToDto(cfg);
			} else {
				p.sendMessage(main.colorize("&cHome not found!"));
			}
		}
		return h;
	}

	public List<Home> ReadList(Player p) {
		User u = DtoHandler.createUserDto(p);
		List<Home> homes = new ArrayList<Home>();
		for (FileConfiguration cfg : HomeYmlHandler.getUserHomeListCfg(u)) {
			Home h = DtoHandler.ymlHomeToDto(cfg);
			homes.add(h);
		}
		return homes;
	}

	public void Update(Player p, String name) {
		User u = DtoHandler.createUserDto(p);
		for (FileConfiguration cfg : HomeYmlHandler.getUserHomeListCfg(u)) {
			if (cfg.getString("Home.Name") == name) {
				HomeYmlHandler.updateHome(DtoHandler.ymlHomeToDto(cfg));
			}
		}
	}

	public void Delete(Player p, String name) {
		User u = DtoHandler.createUserDto(p);
		for(Home h : HomeYmlHandler.getHomes(u)) {
			if(h.getName().equalsIgnoreCase(name)) {
				HomeYmlHandler.deleteHome(h);
			}
		}
		
	}

}
