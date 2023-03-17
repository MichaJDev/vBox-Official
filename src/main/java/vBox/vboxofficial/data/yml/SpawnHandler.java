package vBox.vboxofficial.data.yml;

import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.handlers.Spawn.SpawnYmlHandler;
import vBox.vboxofficial.dataobjects.Spawn;
import vBox.vboxofficial.dataobjects.User;
import vBox.vboxofficial.dataobjects.handlers.DtoHandler;

public class SpawnHandler {
	@SuppressWarnings("unused")
	private Main main = Main.getInstance();

	public SpawnHandler(Main _main) {
		main = _main;
	}

	public void Create(Spawn s) {
		SpawnYmlHandler.createSpawnFile(s);
	}

	public Spawn Read() {
		return DtoHandler.ymlToSpawnDto(SpawnYmlHandler.getSpawnCfg());
	}

	public void Update(Player p) {
		User u = DtoHandler.createUserDto(p);
		Spawn s = DtoHandler.createSpawnDto(u);
		SpawnYmlHandler.updateSpawn(s);
	}

}
