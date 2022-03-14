package me.colin.chess.display;

import me.colin.chess.ImageLoader;
import me.colin.chess.MusicController;
import me.colin.chess.display.board.AbstractBoard;
import me.colin.chess.utils.ResourceUtility;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {

	private final MainMenu mainMenu = new MainMenu(this);
	private final ImageLoader imageLoader;
	private final MusicController music;

	public Display(String title, ImageLoader imageLoader, MusicController music) {
		this.imageLoader = imageLoader;
		this.music = music;

		timeAction(() -> {
			setup(title);
			add(mainMenu);
			displayMainMenu();
		}, "Display shown.");

		imageLoader.start();
		music.start();
	}

	/**
	 * Sets up the menu to be displayed.
	 * @param title title of the menu
	 */
	private void setup(String title) {
		// Setup frame.
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
		} catch (UnsupportedOperationException | SecurityException ex) {
			ex.printStackTrace();
		}

		// Makes the JFrame visible.
		SwingUtilities.invokeLater(() -> setVisible(true));
	}

	/**
	 * Displays the main menu.
	 */
	public void displayMainMenu() {
		mainMenu.setVisible(true);
		music.setStatus(MusicController.Status.HOME);
		revalidate();
	}

	/**
	 * Displays the chessboard.
	 */
	public void displayChessboard() {
		timeAction(() -> {
			mainMenu.setVisible(false);
			music.setStatus(MusicController.Status.NORMAL);

			AbstractBoard board = new AbstractBoard();
			board.setupDisplay(this, getContentPane().getSize());
			board.display();

			add(board);
			revalidate();
		}, "Chessboard shown.");
	}

	/**
	 * Displays the instructions.
	 */
	public void displayInstructions() {
		mainMenu.setVisible(false);
		add(new Instructions(this));
		revalidate();
	}

	public ImageLoader getImageLoader() {
		return imageLoader;
	}

	public MusicController getMusic() {
		return music;
	}

	/*
	 * Times the action given.
	 */
	private void timeAction(Action action, String complete) {
		long time = System.currentTimeMillis();
		action.run();
		System.out.printf(complete + " Time elapsed: %s", System.currentTimeMillis() - time);
	}

	@FunctionalInterface
	private interface Action {
		void run();
	}
}
