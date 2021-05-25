import javax.swing.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import parser.Parser;

public class MultiClip {
	private final Parser parser = new Parser();

	private JPanel main;
	private JPanel top;
	private JButton exitButton;
	private JButton clearInputButton;
	private JButton clearOutputButton;
	private JTextField consoleInput;
	private JButton runButton;
	private JTextArea consoleOutput;

	public MultiClip() {
		exitButton.addActionListener(e -> {
			if ("exit".equals(e.getActionCommand())) {
				System.exit(0);
			}
		});
		clearInputButton.addActionListener(e -> {
			if ("clearInput".equals(e.getActionCommand())) {
				consoleInput.setText("");
			}
		});
		clearOutputButton.addActionListener(e -> {
			if ("clearOutput".equals(e.getActionCommand())) {
				consoleOutput.setText("");
			}
		});
		consoleInput.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if ("\n".equals(String.valueOf(e.getKeyChar()))) {
					try {
						run();
					} catch (IOException | UnsupportedFlavorException exception) {
						exception.printStackTrace();
					}
				}
			}
		});
		runButton.addActionListener(e -> {
			try {
				run();
			} catch (IOException | UnsupportedFlavorException exception) {
				exception.printStackTrace();
			}
		});
	}

	private void run() throws IOException, UnsupportedFlavorException {
		String output = parser.parse(consoleInput.getText());
		consoleInput.setText("");
		if (output.equals("nap-Clear")) {
			consoleOutput.setText("");
		} else {
			consoleOutput.append(output + "\n");
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("MultiClip 1.0");
		frame.setContentPane(new MultiClip().main);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
}
