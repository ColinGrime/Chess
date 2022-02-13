package me.scill.chess.display;

import me.scill.chess.board.Board;
import me.scill.chess.utilities.SwingUtility;

import javax.swing.*;
import java.awt.*;

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

		Image image = SwingUtility.getImage("Logo.png", 100, 100);

		// Sets up the icon for Windows.
		setIconImage(image);

		// Attempts to set the icon for macOS.
		try {
			Taskbar.getTaskbar().setIconImage(image);
		} catch (UnsupportedOperationException e) {
			System.out.println("The OS does not support: 'taskbar.setIconImage'");
		} catch (SecurityException e) {
			System.out.println("Security exception: 'taskbar.setIconImage'");
		}

		// Makes the JFrame visible.
		SwingUtilities.invokeLater(() -> setVisible(true));
	}

	public void displayChessboard() {
		remove(mainMenu);
		add(chessboard);
		revalidate();
	}
}
