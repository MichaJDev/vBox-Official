package vBox.vboxofficial.data.yml;

import java.util.UUID;

import org.bukkit.entity.Player;

import vBox.vboxofficial.data.yml.handlers.Bans.BanYmlHandler;
import vBox.vboxofficial.dataobjects.Ban;
import vBox.vboxofficial.dataobjects.handlers.DtoHandler;

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
