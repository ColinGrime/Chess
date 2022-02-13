package me.scill.chess.display;

import me.scill.chess.utilities.ResourceUtility;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;

public class Display extends JFrame {

	private final MainMenu mainMenu = new MainMenu(this);
	private final Board board = new Board();

	public Display(String title) {
		this.setup(title);
		this.add(mainMenu);
	}

	/*
	 * Sets up the menu to be displayed.
	 */
	private void setup(String title) {
		setTitle(title);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();

		Image image = ResourceUtility.getImage("Logo.png", 100, 100);

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
		add(board.display(getContentPane().getSize()));
		revalidate();
	}
}
