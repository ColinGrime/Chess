package me.scill.chess.display;

import me.scill.chess.Piece;
import me.scill.chess.pieces.King;
import me.scill.chess.pieces.Pawn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Tile extends JButton implements ActionListener {

	private static Tile selectedTile = null;
	private static Color selectedColor = null;
	private static final List<Tile> tilesInRange = new ArrayList<>();

	private final Board board;
	private final int row;
	private final char column;

	private Piece piece;
	private boolean drawCircle = false;

	public Tile(Board board, int row, char column) {
		this(board, row, column, Color.WHITE);
	}

	public Tile(Board board, int row, char column, Color color) {
		this.board = board;
		this.row = row;
		this.column = column;

		setBackground(color);
		setBorderPainted(false);
		setOpaque(true);

		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// One of the tiles is selected.
		if (selectedTile != null && selectedTile.getPiece() != null) {
			Piece piece = selectedTile.getPiece();
			boolean shouldHighlight = selectedTile != this;

			// If's a valid move, play it.
			if (piece.getMoves().contains(this) && board.isLegalMove(piece, this)) {
				if (selectedTile.getPiece() instanceof King)
					((King) piece).checkForCastle(this);

				setPiece(piece);
				getPiece().moved();
				selectedTile.setPiece(null);

				board.resetCheck();
				board.checkForCheck(piece);

				// Check if Pawn can upgrade.
				if (getPiece() instanceof Pawn)
					((Pawn) getPiece()).checkForUpgrade();

				shouldHighlight = false;
				board.nextTurn();
			}

			// Removes the highlight and de-selects the selected tile.
			selectedTile.setBackground(selectedColor);
			selectedTile = null;

			tilesInRange.forEach(t -> {
				t.setDrawCircle(false);
				t.repaint();
			});
			tilesInRange.clear();

			// Return if the clicked-on tile shouldn't be highlighted.
			if (!shouldHighlight)
				return;
		}

		// If the clicked-on tile has a Piece that exists, select it.
		if (getPiece() != null && getPiece().getSide() == board.getCurrentTurn()) {
			selectedTile = this;
			highlight();
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (!drawCircle)
			return;

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(8));
		g2.setColor(new Color(0, 0, 0, 30));

		if (getPiece() == null)
			g2.fillOval((getWidth() / 2) - 12, (getHeight() / 2) - 12, 24, 24);
		else
			g2.drawOval((getWidth() / 2) - 45, (getHeight() / 2) - 45, 90, 90);
	}

	@Override
	protected void processMouseEvent(MouseEvent e) {
		super.processMouseEvent(e);

		// Sets the cursor to default on leave.
		if (e.getID() == MouseEvent.MOUSE_EXITED)
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		else if (e.getID() != MouseEvent.MOUSE_ENTERED)
			return;

		// If you can move to this Tile, it changes into a hand cursor.
		if (tilesInRange.contains(this));

		// If the King is in checked, you can only select the pieces that can get it out of it.
		else if (getPiece() != null && board.getCheckedKing() != null && board.getMovesLeft(getPiece()).size() == 0)
			return;

		// If there's no Piece, or it's not their turn, don't switch cursor.
		else if (getPiece() == null || getPiece().getSide() != board.getCurrentTurn())
			return;

		// Sets the cursor to hand on enter.
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	private void highlight() {
		selectedColor = getBackground();
		setBackground(Color.decode("#9d9ad9"));

		for (Tile tile : selectedTile.getPiece().getMoves()) {
			if (!board.isLegalMove(selectedTile.getPiece(), tile))
				continue;

			tile.setDrawCircle(true);
			tile.repaint();
			tilesInRange.add(tile);
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

		SwingUtilities.invokeLater(() -> setIcon(piece.getIcon()));
		SwingUtilities.invokeLater(() -> setPressedIcon(piece.getIcon()));
	}

	public Board getBoard() {
		return board;
	}

	public int getRow() {
		return row;
	}

	public char getColumn() {
		return column;
	}

	public void setDrawCircle(boolean drawCircle) {
		this.drawCircle = drawCircle;
	}

	@Override
	public Dimension getPreferredSize() {
		return board.getSize();
	}

	@Override
	public String toString() {
		return piece + " @ pos(" + row + ", " + column + ")";
	}
}
