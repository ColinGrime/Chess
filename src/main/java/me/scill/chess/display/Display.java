package me.scill.chess.display;

import me.scill.chess.board.Board;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Display extends JFrame {

	private final MainMenu mainMenu = new MainMenu(this);
	private final Chessboard chessboard = new Chessboard(new Board());

	public Display(String title) {
		this.setup(title);
		add(mainMenu);
	}

	/*
	 * Sets up the menu to be displayed.
	 */
	private void setup(String title) {
		setTitle(title);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SwingUtilities.invokeLater(() -> setVisible(true));
	}

	public void displayChessboard() {
		remove(mainMenu);
		add(chessboard);
		revalidate();
	}

	public void setFullScreen() {
		Arrays.stream(Window.getWindows())
				.findFirst()
				.ifPresent(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()::setFullScreenWindow);
	}
}
