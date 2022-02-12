package me.scill.chess.board;

import me.scill.chess.enums.Side;
import me.scill.chess.display.Tile;

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

			// If the move has a Piece on it, make sure it's NOT an ally.
			if (tile.getPiece() == null || tile.getPiece().getSide() != getSide())
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

		System.out.println(moves);
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
			if (t.getPiece() == null)
				continue;

			boolean isRowBlocked = t.getRow() > minRow && t.getRow() < maxRow;
			boolean isColumnBlocked = t.getColumn() > minColumn && t.getColumn() < maxColumn;

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
