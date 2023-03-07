package nl.vBox.utils.pages;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import nl.vBox.utils.general.FileUtils;

public class PageHandler {
	 private static ArrayList<HelpPage> helpPages;

	    public static void init() {
	        helpPages = new ArrayList<HelpPage>();
	        loadPages();
	    }

	    public static void loadPages() {
	        for (final String pageid : FileUtils.getConfig().getConfigurationSection("pages").getKeys(false))
	            if (FileUtils.getConfig().getStringList("pages." + pageid) != null && FileUtils.getConfig().getStringList("pages." + pageid).size() != 0)
	                helpPages.add(new HelpPage(Integer.parseInt(pageid.substring(5)))); //Substring to cut off "page-"
	    }

	    public static String formatStringVariables(String inputString, CommandSender commandSender) {
	        inputString = ChatColor.translateAlternateColorCodes('&', inputString); //Replace color and formatting codes.

	        if (inputString.contains("%playername%")) {
	            if (commandSender instanceof Player) {
	                final Player playerCommandSender = (Player) commandSender;
	                inputString.replace("%playername%", playerCommandSender.getName());
	            } else if (commandSender instanceof ConsoleCommandSender)
	                inputString.replace("%playername%", "Console");
	            else if (commandSender instanceof CommandBlock)
	                inputString.replace("%playername%", "Commandblock");
	            else
	                inputString.replace("%playername%", "Unknown");
	        }

	        if (inputString.contains("%playeruuid%")) {
	            if (commandSender instanceof Player) {
	                final Player playerCommandSender = (Player) commandSender;
	                inputString.replace("%playeruuid%", playerCommandSender.getUniqueId().toString());
	            } else if (commandSender instanceof ConsoleCommandSender)
	                inputString.replace("%playeruuid%", "Console");
	            else if (commandSender instanceof CommandBlock)
	                inputString.replace("%playeruuid%", "Commandblock");
	            else
	                inputString.replace("%playeruuid%", "Unknown");
	        }

	        return inputString;
	    }

	    public static ArrayList<HelpPage> getHelpPages() {
	        return PageHandler.helpPages;
	    }

}
