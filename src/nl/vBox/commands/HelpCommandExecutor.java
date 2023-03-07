package nl.vBox.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import nl.vBox.utils.general.FileUtils;
import nl.vBox.utils.pages.HelpPage;
import nl.vBox.utils.pages.PageHandler;

public class HelpCommandExecutor extends BukkitCommand {
	
	public HelpCommandExecutor() {
		super(FileUtils.getConfig().getString("command.name"), FileUtils.getConfig().getString("command.description"),
				FileUtils.getConfig().getString("command.usage"),
				FileUtils.getConfig().getStringList("command.aliases"));
		this.setPermission(FileUtils.getConfig().getString("command.permission"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] arguments) {
		if (!commandSender.hasPermission(this.getPermission())) {
			commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',
					FileUtils.getConfig().getString("messages.nopermission")));
			return true; // Does not have the permission
		}
		if (PageHandler.getHelpPages().size() == 0) {
			commandSender.sendMessage(
					ChatColor.translateAlternateColorCodes('&', FileUtils.getConfig().getString("messages.nopages")));
			return true; // No pages were added
		}
		if (arguments.length >= 1) {
			if (isInteger(arguments[0])) {
				int pageNumber = Integer.parseInt(arguments[0]);
				if (Integer.parseInt(arguments[0]) <= 0)
					pageNumber = 1;
				HelpPage helpPage = null;
				if (PageHandler.getHelpPages().size() >= pageNumber)
					helpPage = PageHandler.getHelpPages().get(pageNumber - 1);
				if (helpPage != null && helpPage.hasLines()) {
					helpPage.print(commandSender);
				} else {
					commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							FileUtils.getConfig().getString("messages.notfound")));
					return true;
				}
			} else {
				commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						FileUtils.getConfig().getString("messages.invalid")));
				return true;
			}
		} else
			PageHandler.getHelpPages().get(0).print(commandSender);

		return true;
	}

	private boolean isInteger(final String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
}
