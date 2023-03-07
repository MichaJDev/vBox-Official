package nl.vBox.data.yml;

import org.bukkit.entity.Player;

import nl.vBox.Main;
import nl.vBox.data.dataobjects.User;
import nl.vBox.data.dataobjects.handlers.DtoHandler;
import nl.vBox.data.yml.handlers.Users.UserYmlHandler;
import nl.vBox.utils.enums.LogSeverity;

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

}
