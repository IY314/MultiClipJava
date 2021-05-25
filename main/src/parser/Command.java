package parser;

import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.*;

public class Command {
	static List<Command> commands = new ArrayList<>();

	public String name;
	public FuncInterface function;
	public String helpText;
	public int arguments;

	public Command(String name, FuncInterface function, String helpText, int arguments) {
		this.name = name;
		this.function = function;
		this.helpText = helpText;
		this.arguments = arguments;
		commands.add(this);
	}

	public Command(String name, FuncInterface function, String helpText) {
		this.name = name;
		this.function = function;
		this.helpText = helpText;
		this.arguments = 0;
		commands.add(this);
	}

	public String execute(@NotNull String[] text) throws IOException, UnsupportedFlavorException {
		if (text.length != this.arguments + 1 && this.arguments != -1) {
			return "Incorrect amount of arguments.";
		}
		if (this.arguments != 0) {
			return this.function.call(Arrays.copyOfRange(text, 1, text.length));
		} else {
			return this.function.call();
		}
	}
}
