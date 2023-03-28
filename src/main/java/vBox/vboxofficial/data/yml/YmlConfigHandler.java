package vBox.vboxofficial.data.yml;

import org.bukkit.configuration.file.FileConfiguration;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.dtos.Config;

public class YmlConfigHandler {

	private Main main = Main.getInstance();

	public YmlConfigHandler(Main _main) {
		main = _main;
	}

	public void createConfigFile() {
		main.saveDefaultConfig();
	}

	public FileConfiguration getConfig() {
		return main.getConfig();
	}

	public void updateConfig(Config c) {
		FileConfiguration cfg = getConfig();
		cfg.set("ServerName", c.getServerName());
		cfg.set("MOTD", c.getMotd());
		cfg.set("MySql.use", c.useMysql());
		cfg.set("SqLite.use", c.UseSqlite());
		cfg.set("OracleDB.use", c.useOracle());
		cfg.set("MSSQL.use", c.useMSSql());
		cfg.set("Postgres.use", c.usePostGres());
	}

}
