package me.scill.chess.board;

import me.scill.chess.enums.Side;
import me.scill.chess.display.Tile;
import me.scill.chess.pieces.King;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

	private final Side side;
	private Tile tile;
	private int timesMoved = 0;

	public Piece(Side side) {
		this.side = side;
	}

	/**
	 * Gets a list of possible, valid moves.
	 * @return list of possible moves.
	 */
	public List<Tile> getPossibleMoves() {
		List<Tile> possibleMoves = new ArrayList<>();
		List<Tile> moves = getMoves();

		for (Tile tile : moves) {
			// If the move is blocked, it's invalid
			if (isBlocked(tile, moves))
				continue;

			// If the move has a Piece on it, make sure it's NOT an ally (unless it's an attempted King move).
			if (tile.getPiece() == null || tile.getPiece().getSide() != getSide() || tile == King.getAttemptedMove())
				possibleMoves.add(tile);
		}

		return possibleMoves;
	}

	/**
	 * Gets a list of moves a Piece can make
	 *   without accounting for it being blocked.
	 * @return list of moves.
	 */
	private List<Tile> getMoves() {
		List<Tile> moves = new ArrayList<>();

		for (Tile tile : getTile().getBoard().getTiles()) {
			// If the move is valid, you can move there.
			if (isValidMove(tile))
				moves.add(tile);
		}

		return moves;
	}

	public boolean isValidMove(Tile tile) {
		// Row and column differences
		int rowDiff = Math.abs(tile.getRow() - getTile().getRow());
		int columnDiff = Math.abs(tile.getColumn() - getTile().getColumn());

		return isValidMove(tile, rowDiff, columnDiff);
	}

	protected abstract boolean isValidMove(Tile tile, int rowDiff, int columnDiff);

	public boolean isBlocked(Tile tile) {
		return isBlocked(tile, getMoves());
	}

	private boolean isBlocked(Tile tile, List<Tile> moves) {
		// Row and column differences.
		int rowDiff = Math.abs(tile.getRow() - getTile().getRow());
		int columnDiff = Math.abs(tile.getColumn() - getTile().getColumn());

		// If the Piece moved their row/column.
		boolean hasMovedRow = rowDiff > 0;
		boolean hasMovedColumn = columnDiff > 0;

		// Min/max rows and columns.
		int minRow = Math.min(tile.getRow(), getTile().getRow());
		int maxRow = Math.max(tile.getRow(), getTile().getRow());
		int minColumn = Math.min(tile.getColumn(), getTile().getColumn());
		int maxColumn = Math.max(tile.getColumn(), getTile().getColumn());

		for (Tile t : moves) {
			// If there's no Piece, it can't be blocking.
			if (t.getPiece() == null)
				continue;

			// If it hasn't moved rows, don't check different rows.
			else if (!hasMovedRow && t.getRow() != tile.getRow())
				continue;

			// If it hasn't moved columns, don't check different columns.
			else if (!hasMovedColumn && t.getColumn() != tile.getColumn())
				continue;

			// Checked for blocked rows/columns.
			boolean isRowBlocked = t.getRow() > minRow && t.getRow() < maxRow;
			boolean isColumnBlocked = t.getColumn() > minColumn && t.getColumn() < maxColumn;

			// Check if the Piece is blocking the movement.
			if (isBlocked(isRowBlocked, isColumnBlocked, hasMovedRow, hasMovedColumn))
				return true;
		}

		return false;
	}

	protected abstract boolean isBlocked(boolean isRowBlocked, boolean isColumnBlocked, boolean hasMovedRow, boolean hasMovedColumn);

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public Side getSide() {
		return side;
	}

	public void addTimeMoved() {
		timesMoved++;
	}

	public int getTimesMoved() {
		return timesMoved;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " (" + side.name().charAt(0) + ")";
	}
}
