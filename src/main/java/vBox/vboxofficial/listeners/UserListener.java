package vBox.vboxofficial.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlUserHandler;
import vBox.vboxofficial.dtos.User;
import vBox.vboxofficial.dtos.handlers.DtoHandler;
import vBox.vboxofficial.utils.LogSeverity;

public class UserListener implements Listener {

	private Main main = Main.getInstance();
	private YmlUserHandler uh;

	public UserListener(Main _main) {
		main = _main;
		uh = new YmlUserHandler(main);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		User u = DtoHandler.createUserDto(e.getPlayer());
		if (!uh.exists(u)) {
			main.log("Player:" + u.getName() + " not found in data.. Creating!", LogSeverity.INFO);
			uh.createNew(u);
		} else {
			main.log("Player:" + u.getName() + " found, updating data!", LogSeverity.INFO);
			uh.update(u);
		}
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		User u = DtoHandler.createUserDto(e.getPlayer());
		main.log("Player:" + u.getName() + " left, updating data!", LogSeverity.INFO);
		uh.update(u);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		User u = DtoHandler.createUserDto(e.getPlayer());
		uh.update(u);

	}
}
