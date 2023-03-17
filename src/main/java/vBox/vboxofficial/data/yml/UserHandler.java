package vBox.vboxofficial.data.yml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.handlers.Users.UserYmlHandler;
import vBox.vboxofficial.dataobjects.User;
import vBox.vboxofficial.dataobjects.handlers.DtoHandler;
import vBox.vboxofficial.utils.enums.LogSeverity;

public class UserHandler {

	Main main = Main.getInstance();

	public void Create(Player p) {
		main.log("Shaping User Dto", LogSeverity.INFO);
		User u = DtoHandler.createUserDto(p);
		main.log("Creating new User", LogSeverity.INFO);
		UserYmlHandler.createUserFile(u);
	}

	public User Read(Player p) {
		User u = DtoHandler.createUserDto(p);
		return UserYmlHandler.getUser(u);
	}

	public void Update(Player p) {
		User u = DtoHandler.createUserDto(p);
		UserYmlHandler.updateUser(u);
	}

	public List<User> getAll() {
		List<User> users = new ArrayList<User>();
		for (File f : UserYmlHandler.getUserMainFolder().listFiles()) {
			File userFile = new File(f, "user.yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(userFile);
			users.add(DtoHandler.YmlUserToDto(cfg));
		}
		return users;
	}

}
