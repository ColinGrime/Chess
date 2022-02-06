package me.scill.chess.display;

import me.scill.chess.board.Board;

import java.awt.*;

public class Chessboard extends Display {

	private final Board board;

	public Chessboard(Board board) {
		super("Chess");
		this.board = board;
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

				// Even = Black, Odd = White
				if (column % 2 == 0) panelColor = Color.WHITE;
				else panelColor = Color.GREEN;

				// Switch colors every row
				if (doSwitchColor)
					panelColor = panelColor == Color.WHITE ? Color.GREEN : Color.WHITE;

				// Creates a position, and adds a click listener to it
				SquareTile position = new SquareTile(board.getSIZE() - row, column, panelColor);

				// Add the position to the board, and the panel to the display
				board.getTiles().add(position);
				add(position);
			}
		}
	}
}
