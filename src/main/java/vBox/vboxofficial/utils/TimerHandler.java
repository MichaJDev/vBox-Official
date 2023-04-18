package vBox.vboxofficial.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.entity.Player;

import vBox.vboxofficial.Main;
import vBox.vboxofficial.data.yml.YmlTpHandler;
import vBox.vboxofficial.dtos.Teleport;

public class TimerHandler {

	private Main main = Main.getInstance();

	public TimerHandler(Main _main){
		main = _main;
	}
	public boolean isTimeUp(String banTime) {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
		Date banDate = null;
		LocalDateTime now = LocalDateTime.now();
		try {
			banDate = sdFormat.parse(banTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Instant banInstant = banDate.toInstant();
		Instant nowInstant = now.toInstant(ZoneOffset.UTC);
		return banInstant.isBefore(nowInstant);
	}

	public String getDate(String timeString) {
		LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-hh:mm:ss");
		long time = 0;
		if (timeString.contains("s")) {
			time = Integer.parseInt(timeString.replace("s", ""));
			now = now.plusSeconds(time);
		} else if (timeString.contains("m")) {
			time = Integer.parseInt(timeString.replace("m", ""));
			now = now.plusMinutes(time);
		} else if (timeString.contains("h")) {
			time = Integer.parseInt(timeString.replace("h", ""));
			now = now.plusHours(time);
		} else if (timeString.contains("d")) {
			time = Integer.parseInt(timeString.replace("d", ""));
			now = now.plusDays(time);
		} else if (timeString.contains("w")) {
			time = Integer.parseInt(timeString.replace("w", ""));
			now = now.plusWeeks(time);
		} else if (timeString.contains("M")) {
			time = Integer.parseInt(timeString.replace("M", ""));
			now = now.plusMonths(time);
		} else if (timeString.contains("y")) {
			time = Integer.parseInt(timeString.replace("y", ""));
			now = now.plusYears(time);
		} else {
			return timeString;
		}
		String formattedDate = dtf.format(now);
		return formattedDate;
	}

	public boolean isValidTimeFormat(String timeString) {
		if (timeString == null || timeString.isEmpty()) {
			return false;
		}
		int count = 0;
		for (char c : timeString.toCharArray()) {
			if (c == 's' || c == 'm' || c == 'h' || c == 'd' || c == 'w' || c == 'M' || c == 'y') {
				count++;
				if (count > 1) {
					return false; // more than one time parameter
				}
			} else if (!Character.isDigit(c)) {
				return false; // invalid character
			}
		}
		return count == 1; // only one time parameter
	}

	public void startDeleteTimer(Teleport tp) {
		String dateString = tp.getCoolDownTime();
		Date d1 = null;
		try {
			d1 = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss").parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Timer t = new Timer();
		TimerTask tt = new TimerTask() {
			@Override
			public void run() {
				YmlTpHandler th = new YmlTpHandler(main);
				th.delete(tp);
				Player p = main.getPlayer(tp.getTeleporter());
				Player target = main.getPlayer(tp.getTarget());
				p.sendMessage(main.colorize("&cTeleport request has ran out time!"));
				target.sendMessage(main.colorize("&cTpa request automatically denied due to time exceeding!"));
			}
		};
		t.schedule(tt, d1);
	}
}
