package me.scill.chess.display;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public abstract class Display extends JFrame {

	public Display(String title) {
		this.setup(title);
		this.display();
	}

	/*
	 * Runs before the menu gets displayed.
	 */
	protected abstract void init();

	/*
	 * Sets up the menu to be displayed.
	 */
	private void setup(String title) {
		setTitle(title);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/*
	 * Displays the board after a slight delay.
	 */
	private void display() {
		SwingUtilities.invokeLater(() -> {
			init();
			setVisible(true);
		});
	}

	public void setFullScreen() {
		Arrays.stream(Window.getWindows())
				.findFirst()
				.ifPresent(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()::setFullScreenWindow);
	}
}
