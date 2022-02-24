package me.scill.chess.display;

import me.scill.chess.ImageLoader;
import me.scill.chess.Music;
import me.scill.chess.utilities.ResourceUtility;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {

	private final MainMenu mainMenu = new MainMenu(this);
	private final ImageLoader imageLoader;
	private final Music music;

	public Display(String title, ImageLoader imageLoader, Music music) {
		this.imageLoader = imageLoader;
		this.music = music;

		long time = System.currentTimeMillis();
		setup(title);
		add(mainMenu);
		displayMainMenu();
		System.out.println("Display shown. Time elapsed: " + (System.currentTimeMillis() - time) + "ms");

		imageLoader.start();
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
		long time = System.currentTimeMillis();

		mainMenu.setVisible(false);

		Board board = new Board();
		board.setupDisplay(this, getContentPane().getSize());
		board.display();

		add(board);
		revalidate();

		music.setStatus(Music.Status.NORMAL);
		System.out.println("Chessboard shown. Time elapsed: " + (System.currentTimeMillis() - time) + "ms");
	}

	public ImageLoader getImageLoader() {
		return imageLoader;
	}

	public Music getMusic() {
		return music;
	}
}
