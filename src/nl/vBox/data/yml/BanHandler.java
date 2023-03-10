package nl.vBox.data.yml;

import java.util.UUID;

import org.bukkit.entity.Player;

import nl.vBox.data.dataobjects.Ban;
import nl.vBox.data.dataobjects.handlers.DtoHandler;
import nl.vBox.data.yml.handlers.Bans.BanYmlHandler;

public class BanHandler {

	public void Create(Player p, Player tp, int time, String unit, String reason) {
		if(!IsBanned(tp)) {
			String hash = UUID.randomUUID().toString().substring(0, 5);
			BanYmlHandler.createBanFile(DtoHandler.createBanDto(p, tp, time, unit, reason, hash));	
		}
		
	}

	public Ban Read(Player tp, Player p, String hash) {
		Ban b = BanYmlHandler.getBan(DtoHandler.createUserDto(tp), hash);
		return b;
	}

	public void Update(Player p, Player tp, int time, String unit, String reason, String hash) {
		BanYmlHandler.updateGeneralBanFile(DtoHandler.createBanDto(p, tp, time, unit, reason, hash), false);
	}

	public boolean IsBanned(Player p) {
		return BanYmlHandler.isBanned(DtoHandler.createUserDto(p));
	}
}
