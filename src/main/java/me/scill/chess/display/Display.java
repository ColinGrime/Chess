package me.scill.chess.display;

import me.scill.chess.Music;
import me.scill.chess.utilities.ResourceUtility;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {

	private final MainMenu mainMenu = new MainMenu(this);
	private final Music music = new Music();

	public Display(String title) {
		this.setup(title);
		add(mainMenu);
		displayMainMenu();
		music.start();
	}

	/*
	 * Sets up the menu to be displayed.
	 */
	private void setup(String title) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);
		pack();

		// Default background.
		UIManager.put("OptionPane.background", new Color(40, 44, 52));
		UIManager.put("Panel.background", new Color(40, 44, 52));

		// Sets up the icon for Windows.
		Image image = ResourceUtility.getImage("Logo.png", 512, 512);
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

	public void displayMainMenu() {
		mainMenu.setVisible(true);
		revalidate();

		music.setStatus(Music.Status.HOME);
	}

	public void displayChessboard() {
		mainMenu.setVisible(false);
		add(new Board(this, getContentPane().getSize()));
		revalidate();

		music.setStatus(Music.Status.NORMAL);
	}

	public Music getMusic() {
		return music;
	}
}
