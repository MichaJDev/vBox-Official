package nl.vBox.data.yml;

import nl.vBox.data.dataobjects.Config;
import nl.vBox.data.dataobjects.handlers.DtoHandler;
import nl.vBox.data.yml.handlers.Config.ConfigYmlHandler;

public class ConfigHandler {

	public void Create() {
		ConfigYmlHandler.createConfig();
	}

	public Config Read() {
		return DtoHandler.ymlToConfigDto(ConfigYmlHandler.getConfig());
	}

	public void UpdateMotd(String[] args) {
		StringBuilder sb = new StringBuilder();
		Config c = DtoHandler.ymlToConfigDto(ConfigYmlHandler.getConfig());
		for (int i = 0; i < args.length; i++) {
			sb.append(args[i]);
		}
		c.setMotd(sb.toString());
		ConfigYmlHandler.updateConfig(c);

	}

	public void UpdateServerName(String name) {
		Config c = DtoHandler.ymlToConfigDto(ConfigYmlHandler.getConfig());
		c.setServerName(name);
		ConfigYmlHandler.updateConfig(c);
	}
}
