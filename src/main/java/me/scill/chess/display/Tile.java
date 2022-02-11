package me.scill.chess.display;

import me.scill.chess.board.Piece;
import me.scill.chess.board.Board;
import me.scill.chess.utilities.SwingUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tile extends JButton implements ActionListener {


	private static Tile selectedTile = null;
	private static Color selectedColor = null;

	private final Board board;
	private final int rowPos;
	private final char columnPos;
	private Piece piece;

	public Tile(Board board, int rowPos, char columnPos) {
		this(board, rowPos, columnPos, Color.WHITE);
	}

	public Tile(Board board, int rowPos, char columnPos, Color color) {
		this.board = board;
		this.rowPos = rowPos;
		this.columnPos = columnPos;

		setBackground(color);
		setOpaque(true);
		setBorderPainted(false);
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// One of the tiles is selected.
		if (selectedTile != null) {
			boolean isAlly = false;
			boolean shouldHighlight = selectedTile != this;

			// If the clicked-on tile has a Piece that exists, check if it's an ally.
			if (getPiece() != null)
				isAlly = selectedTile.getPiece().getSide() == getPiece().getSide();

			// If's a valid move, play it.
			if (!isAlly && selectedTile.getPiece().isValidPlay(this)) {
				setPiece(selectedTile.getPiece());
				selectedTile.setPiece(null);

				board.nextTurn();
				shouldHighlight = false;
			}

			// Removes the highlight and de-selects the selected tile.
			selectedTile.setBackground(selectedColor);
			selectedTile = null;

			// Return if the clicked-on tile shouldn't be highlighted.
			if (!shouldHighlight)
				return;
		}

		// If the clicked-on tile has a Piece that exists, select it.
		if (getPiece() != null && getPiece().getSide() == board.getCurrentTurn()) {
			selectedTile = this;
			selectedColor = getBackground();
			setBackground(Color.YELLOW);
		}
	}

	public Piece getPiece() {
		return piece;
	}

	/**
	 * Sets the chess piece on the tile.
	 * @param piece any chess piece
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
		paintPiece(piece);

		// If the chess piece exists, set its tile to this tile.
		if (piece != null)
			piece.setTile(this);
	}

	/**
	 * Paints the chess icon onto the tile.
	 * @param piece any chess piece
	 */
	private void paintPiece(Piece piece) {
		if (piece == null) {
			setIcon(null);
			return;
		}

		String imagePath = piece.getSide().name().toLowerCase() + "/" + piece.getClass().getSimpleName() + ".png";
		StretchIcon icon = new StretchIcon(SwingUtility.getImage(imagePath, 100, 100));

		SwingUtilities.invokeLater(() -> setIcon(icon));
		SwingUtilities.invokeLater(() -> setPressedIcon(icon));
	}

	public Board getBoard() {
		return board;
	}

	public int getRowPos() {
		return rowPos;
	}

	public char getColumnPos() {
		return columnPos;
	}

	@Override
	public String toString() {
		return "Position (" + rowPos + ", " + columnPos + ")";
	}
}
