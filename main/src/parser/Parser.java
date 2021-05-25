package parser;

import java.util.*;
import java.awt.datatransfer.*;
import java.awt.Toolkit;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class Parser {
	public Map<String, String> clipDict = new HashMap<>();
	public Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

	public Parser() {
		new Command("exit", this::exit, "Quits this application");
		new Command("get", this::get, "Copies item at specified key to clipboard", 1);
		new Command("add", this::add, "Adds current clipboard to MultiClipboard", 1);
		new Command("remove", this::remove, "Removes item at specified key from MultiClipboard", 1);
		new Command("reset", this::reset, "Resets the MultiClipboard");
		new Command("list", this::list, "Lists the MultiClipboard");
		new Command("help", this::help, "Shows a help menu", -1);
	}

	public void save() {
		// This method intentionally left blank
	}

	public String exit(@NotNull String[] s) {
		System.exit(0);
		return null;
	}

	public String get(@NotNull String[] s) {
		String key = s[0];
		String value = this.clipDict.get(key);
		if (value == null) {
			return "Unknown key";
		} else {
			StringSelection stringSelection = new StringSelection(value);
			this.clipboard.setContents(stringSelection, null);
			return "Copied '" + value + "' to clipboard";
		}
	}

	public String add(@NotNull String[] s) throws IOException, UnsupportedFlavorException {
		String key = s[0];
		String item = (String) this.clipboard.getContents(null).getTransferData(DataFlavor.stringFlavor);
		if (item.equals("")) {
			return "Copy something to clipboard first";
		}
		for (String k : this.clipDict.keySet()) {
			String v = this.clipDict.get(k);
			if (item.equals(v)) {
				return "Item '" + v + "' is already in the MultiClipboard at key '" + k + "'";
			}
		}
		this.clipDict.put(key, item);
		save();
		return "Added '" + item + "' at '" + key + "'";
	}

	public String remove(@NotNull String[] s) {
		String key = s[0];
		String r = this.clipDict.remove(key);
		save();
		if (r == null) {
			return "Unknown key";
		}
		return "Removed item";
	}

	public String reset(@NotNull String[] s) {
		this.clipDict.clear();
		this.clipboard.setContents(new StringSelection(""), null);
		save();
		return "Reset MultiClipboard";
	}

	public String list(@NotNull String[] s) {
		if (this.clipDict.isEmpty()) {
			return "No items";
		}
		List<String> list = new ArrayList<>();
		for (String k : this.clipDict.keySet()) {
			String v = this.clipDict.get(k);
			list.add(k + ": " + v);
		}
		return String.join("\n", list);
	}

	public String help(@NotNull String[] s) {
		if (s.length == 0) {
			List<String> commands = new ArrayList<>();
			for (Command command : Command.commands) {
				commands.add(command.name + " - " + command.helpText);
			}
			return String.join("\n", commands);
		} else if (s.length > 1) {
			return "Incorrect amount of arguments";
		} else {
			for (Command command : Command.commands) {
				if (s[0].equals(command.name)) {
					return command.helpText;
				}
			}
			return "Unknown command";
		}
	}

	public String parse(@NotNull String raw) throws IOException, UnsupportedFlavorException {
		List<String> returnString = new ArrayList<>();

		if (raw.equals("")) {
			return "No arguments passed";
		}

		for (String text : raw.split(";")) {
			if (Arrays.equals(text.split(" "), new String[]{})) {
				return "Statement expected before ';'";
			}
			forLoop: {
				for (Command command : Command.commands) {
					if (text.split(" ")[0].equals(command.name)) {
						returnString.add(command.execute(text.split(" ")));
						break forLoop;
					}
				}
				return "Unknown command";
			}
		}
		return String.join("\n", returnString);
	}
}