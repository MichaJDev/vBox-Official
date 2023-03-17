package nl.vBox.utils.general;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import nl.vBox.Main;
import nl.vBox.utils.enums.LogSeverity;

public class LogHandler {

	private static Main main = Main.getInstance();

	public LogHandler(Main _main) {
		main = _main;
	}

	public static void createNewLogFile() {
		File file = new File(main.getDataFolder(), "log.txt");
		if (!file.exists()) {
			main.log("Log file not found", LogSeverity.WARN);
			try {
				file.createNewFile();
				main.log("New Log file has been created", LogSeverity.INFO);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static File getLogFile() {
		File f = new File(main.getDataFolder(), "log.txt");
		if (!f.exists()) {
			createNewLogFile();
			return f;
		}
		return f;
	}

	public static void writeToLog(String msg) {
		File f = getLogFile();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
		LocalDate date = LocalDate.now();
		try {
			FileWriter writer = new FileWriter(f);
			writer.write("[" + dtf.format(date) + "] " + msg);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
