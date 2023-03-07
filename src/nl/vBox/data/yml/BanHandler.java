package nl.vBox.data.yml;

import java.util.UUID;

import org.bukkit.entity.Player;

import nl.vBox.data.dataobjects.Ban;
import nl.vBox.data.dataobjects.User;
import nl.vBox.data.dataobjects.handlers.DtoHandler;
import nl.vBox.data.yml.handlers.Bans.BanYmlHandler;

public class BanHandler {

	public void Create(Player p, Player tp, int time, String unit, String reason) {
		String hash = UUID.randomUUID().toString().substring(0, 5);
		DtoHandler.createBanDto(p, tp, time, unit, reason, hash);
	}

	public Ban Read(Player tp, Player p, String hash) {
		User u = DtoHandler.createUserDto(tp);
		Ban b = BanYmlHandler.getBan(u, hash);
		return b;
	}
	public void Update(Player p, Player tp, int time, String unit, String reason, String hash) {
		
		BanYmlHandler.updateGeneralBanFile(DtoHandler.createBanDto(p, tp, time, unit, reason, hash), false);
	}
}
