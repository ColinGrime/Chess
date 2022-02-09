package me.scill.chess.display;

import me.scill.chess.board.Board;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class Chessboard extends Display {

	private final Board board;
	private final Color WHITE = new Color(Integer.parseInt("E4E9F7", 16)),
						BLUE = new Color(Integer.parseInt("77AADC", 16));

	public Chessboard(Board board) {
		super("Chess");
		this.board = board;

		try {
			// Open an audio input stream.
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("./music/KissTheSky.wav");

			if (inputStream == null) {
				System.out.println("[Error] Music file location is invalid.");
				return;
			}

			AudioInputStream audioIn = AudioSystem.getAudioInputStream(inputStream);
			// Get a sound clip resource.
			Clip clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void init() {
		// Tiles x Tiles
		setLayout(new GridLayout(board.getSIZE(), board.getSIZE()));

		// Sets up the grid & puts the pieces on the board.
		this.setupGrid();
		board.setupBoard();

		// Re-sizes the pieces to fit the board.
		pack();
	}

	private void setupGrid() {
		for (int row=0; row<board.getSIZE(); row++) {
			for (char column='a'; column<'a'+board.getSIZE(); column++) {
				Color panelColor;
				boolean doSwitchColor = row % 2 == 0;

				// Even = White, Odd = Blue
				if (column % 2 == 0) panelColor = WHITE;
				else panelColor = BLUE;

				// Switch colors every row
				if (doSwitchColor)
					panelColor = panelColor == WHITE ? BLUE : WHITE;

				// Creates a position, and adds a click listener to it
				SquareTile position = new SquareTile(board, board.getSIZE() - row, column, panelColor);

				// Add the position to the board, and the panel to the display
				board.getTiles().add(position);
				add(position);
			}
		}
	}
}
