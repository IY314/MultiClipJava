import tkinter.Master;
import parser.Parser;
import tkinter.widgets.*;
import javax.swing.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class MultiClip {
	private static final Parser parser = new Parser();
	private static final Master master = new Master();
	private static Entry consoleInput;
	private static Text consoleOutput;

	public static void main(String[] args) {
		Button exitButton = new Button(master, "Exit", () -> System.exit(0));
		consoleInput = new Entry(master);
		consoleInput.bind("\n", MultiClip::run);
		consoleOutput = new Text(master);
		consoleOutput.setEditable();
		Button clearInputButton = new Button(master, "Clear Input", () -> ((JTextField) consoleInput.component).setText(""));
		Button clearOutputButton = new Button(master, "Clear Output", () -> ((JTextArea) consoleOutput.component).setText(""));
		Button runButton = new Button(master, "Run", MultiClip::run);

		exitButton.grid(0, 0);
		clearInputButton.grid(0, 1);
		clearOutputButton.grid(0, 2);
		consoleInput.grid(0, 3);
		runButton.grid(0, 4);
		consoleOutput.grid(1, 0, 5, 1);
		master.pack("MultiClip");
	}

	public static void run() {
		String output = null;
		try {
			output = parser.parse(((JTextField) consoleInput.component).getText());
		} catch (IOException | UnsupportedFlavorException e) {
			e.printStackTrace();
		}
		((JTextField) consoleInput.component).setText("");
		assert output != null;
		if (output.equals("nap-Clear")) {
			((JTextArea) consoleOutput.component).setText("");
		} else {
			((JTextArea) consoleOutput.component).append(output + "\n");
		}
	}
}
