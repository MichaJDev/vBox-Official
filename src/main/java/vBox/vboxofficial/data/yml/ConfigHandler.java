package vBox.vboxofficial.data.yml;

import vBox.vboxofficial.data.yml.handlers.Config.ConfigYmlHandler;
import vBox.vboxofficial.dataobjects.Config;
import vBox.vboxofficial.dataobjects.handlers.DtoHandler;

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
