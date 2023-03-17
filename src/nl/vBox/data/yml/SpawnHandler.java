package nl.vBox.data.yml;

import org.bukkit.entity.Player;

import nl.vBox.Main;
import nl.vBox.data.dataobjects.Spawn;
import nl.vBox.data.dataobjects.User;
import nl.vBox.data.dataobjects.handlers.DtoHandler;
import nl.vBox.data.yml.handlers.Spawn.SpawnYmlHandler;

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
