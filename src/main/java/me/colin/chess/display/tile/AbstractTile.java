package me.colin.chess.display.tile;

import me.colin.chess.display.board.Board;
import me.colin.chess.piece.Piece;
import me.colin.chess.piece.pieces.King;
import me.colin.chess.piece.pieces.Pawn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class AbstractTile extends JButton implements ActionListener, Tile {

	private static AbstractTile selectedTile = null;
	private static Color selectedColor = null;
	private static final List<AbstractTile> tilesInRange = new ArrayList<>();

	private final Board board;
	private final int row;
	private final char column;

	private Piece piece;
	private boolean drawCircle = false;

	public AbstractTile(Board board, int row, char column) {
		this(board, row, column, Color.WHITE);
	}

	public AbstractTile(Board board, int row, char column, Color color) {
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
			if (piece.getMoves().contains(this) && piece.isLegalMove(this)) {
				if (selectedTile.getPiece() instanceof King) {
					((King) piece).checkForCastle(this);
				}

				piece.moved();
				setPiece(piece);
				selectedTile.setPiece(null);

				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				selectedTile.setBackground(selectedColor);
				clearTilesInRange();

				// Check if Pawn can upgrade.
				if (getPiece() instanceof Pawn) {
					((Pawn) getPiece()).checkForUpgrade();
				}

				board.resetCheck();
				board.checkForCheck(getPiece());
				board.nextTurn();

				shouldHighlight = false;
			}

			else {
				// Removes the highlight and de-selects the selected tile.
				selectedTile.setBackground(selectedColor);
				clearTilesInRange();
			}

			selectedTile = null;

			// Return if the clicked-on tile shouldn't be highlighted.
			if (!shouldHighlight) {
				return;
			}
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

		if (!drawCircle) {
			return;
		}

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(8));
		g2.setColor(new Color(0, 0, 0, 30));

		if (getPiece() == null) {
			g2.fillOval((getWidth() / 2) - 12, (getHeight() / 2) - 12, 24, 24);
		} else {
			g2.drawOval((getWidth() / 2) - 45, (getHeight() / 2) - 45, 90, 90);
		}
	}

	@Override
	protected void processMouseEvent(MouseEvent e) {
		super.processMouseEvent(e);

		// Sets the cursor to default on leave.
		if (e.getID() == MouseEvent.MOUSE_EXITED) {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			return;
		}

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
			if (!selectedTile.getPiece().isLegalMove(tile)) {
				continue;
			}

			AbstractTile abstractTile = (AbstractTile) tile;
			abstractTile.setDrawCircle(true);
			abstractTile.repaint();
			tilesInRange.add(abstractTile);
		}
	}

	private void clearTilesInRange() {
		tilesInRange.forEach(t -> {
			t.setDrawCircle(false);
			t.repaint();
		});
		tilesInRange.clear();
	}

	@Override
	public void setDrawCircle(boolean drawCircle) {
		this.drawCircle = drawCircle;
	}

	@Override
	public Board getBoard() {
		return board;
	}

	@Override
	public Piece getPiece() {
		return piece;
	}

	@Override
	public void setPiece(Piece piece) {
		this.piece = piece;
		paintPiece();

		// If the chess piece exists, set its tile to this tile.
		if (piece != null) {
			piece.setTile(this);
		}
	}

	/**
	 * Paints the chess icon onto the tile.
	 */
	private void paintPiece() {
		if (piece == null) {
			setIcon(null);
			return;
		}

		SwingUtilities.invokeLater(() -> setIcon(piece.getIcon()));
		SwingUtilities.invokeLater(() -> setPressedIcon(piece.getIcon()));
	}

	@Override
	public int getRow() {
		return row;
	}

	@Override
	public char getColumn() {
		return column;
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
