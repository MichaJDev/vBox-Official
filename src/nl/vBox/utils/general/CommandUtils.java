package nl.vBox.utils.general;

import java.lang.reflect.Method;

import org.bukkit.command.Command;
import org.bukkit.plugin.Plugin;

public class CommandUtils {
	public static void registerFakeCommand(Command whatCommand, Plugin plugin) throws ReflectiveOperationException {
		// Getting command map from CraftServer
		Method commandMap = plugin.getServer().getClass().getMethod("getCommandMap", null);
		// Invoking the method and getting the returned object (SimpleCommandMap)
		Object cmdmap = commandMap.invoke(plugin.getServer(), null);
		// getting register method with parameters String and Command from
		// SimpleCommandMap
		Method register = cmdmap.getClass().getMethod("register", String.class, Command.class);
		// Registering the command provided above
		register.invoke(cmdmap, whatCommand.getName(), whatCommand);
		// All the exceptions thrown above are due to reflection, They will be thrown if
		// any of the above methods
		// and objects used above change location or turn private. IF they do, let me
		// know to update the thread!
	}
}
