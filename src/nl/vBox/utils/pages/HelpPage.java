package nl.vBox.utils.pages;

import java.util.List;

import org.bukkit.command.CommandSender;

import nl.vBox.utils.general.FileUtils;

public class HelpPage {
	private int id;
    private List<String> lines;

    public HelpPage(int id) {
        this.id = id;
        this.lines = FileUtils.getConfig().getStringList("pages.page-" + this.id);
    }

    public void print(final CommandSender commandSender) {
        for (final String line : this.lines)
			commandSender.sendMessage(PageHandler.formatStringVariables(line, commandSender));
    }

    public boolean hasLines() {
        return this.lines != null && this.lines.size() != 0;
    }
}
