package me.scill.chess.display;

import me.scill.chess.board.Piece;
import me.scill.chess.board.Board;
import me.scill.chess.pieces.King;
import me.scill.chess.pieces.Pawn;
import me.scill.chess.utilities.SwingUtility;

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
			boolean shouldHighlight = selectedTile != this;
			boolean illegalMove = Piece.isMoveIllegal(selectedTile.getPiece(), this);

			// If's a valid move, play it.
			if (!illegalMove && selectedTile.getPiece().getPossibleMoves().contains(this)) {
				if (selectedTile.getPiece() instanceof King)
					((King) selectedTile.getPiece()).checkForCastle(this);

				setPiece(selectedTile.getPiece());
				getPiece().addTimeMoved();
				selectedTile.setPiece(null);

				Piece.resetCheck();
				List<Tile> possibleNextMoves = getPiece().getPossibleMoves();

				// Check if Pawn can upgrade.
				if (possibleNextMoves.size() == 0 && getPiece() instanceof Pawn)
					((Pawn) getPiece()).checkForUpgrade();

				// Check if the move resulted in a check.
				for (Tile tile : possibleNextMoves) {
					if (tile.getPiece() instanceof King) {
						Piece.setCheck((King) tile.getPiece(), getPiece());
						break;
					}
				}

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
			g2.fillOval((getWidth() / 2) - 15, (getHeight() / 2) - 15, 30, 30);
		else
			g2.drawOval((getWidth() / 2) - 50, (getHeight() / 2) - 50, 100, 100);
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
		else if (getPiece() != null && Piece.getCheckedKing() != null && Piece.getMovesLeft(getPiece()).size() == 0)
			return;

		// If there's no Piece, or it's not their turn, don't switch cursor.
		else if (getPiece() == null || getPiece().getSide() != board.getCurrentTurn())
			return;

		// Sets the cursor to hand on enter.
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	private void highlight() {
		selectedColor = getBackground();
		setBackground(new Color(135, 211, 227));

		for (Tile tile : selectedTile.getPiece().getPossibleMoves()) {
			if (King.isMoveIllegal(selectedTile.getPiece(), tile))
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

		String imagePath = piece.getSide().name().toLowerCase() + "/" + piece.getClass().getSimpleName() + ".png";
		StretchIcon icon = new StretchIcon(SwingUtility.getImage(imagePath, 100, 100));

		SwingUtilities.invokeLater(() -> setIcon(icon));
		SwingUtilities.invokeLater(() -> setPressedIcon(icon));
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
	public String toString() {
		return "Position (" + row + ", " + column + ")";
	}
}
