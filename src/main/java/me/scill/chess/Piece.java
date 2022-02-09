package me.scill.chess;

import me.scill.chess.display.SquareTile;

public abstract class Piece {

	private final Side side;
	private SquareTile tile;

	public Piece(Side side) {
		this.side = side;
	}

	public boolean isValidPlay(SquareTile tile) {
		// Row and column differences
		int rowDiff = Math.abs(tile.getRowPos() - getTile().getRowPos());
		int columnDiff = Math.abs(tile.getColumnPos() - getTile().getColumnPos());

		// Row lowest and highest indexes.
		int[] rowIndex = new int[] {
				Math.min(tile.getRowPos(), getTile().getRowPos()),
				Math.max(tile.getRowPos(), getTile().getRowPos())
		};

		// Column lowest and highest indexes.
		int[] columnIndex = new int[] {
				Math.min(tile.getColumnPos(), getTile().getColumnPos()),
				Math.max(tile.getColumnPos(), getTile().getColumnPos())
		};

		// Check if the Piece is blocked.
		if (isBlocked(tile, rowDiff, columnDiff, rowIndex, columnIndex))
			return false;

		// Checks if the Piece can move to that square.
		if (!isValidMove(tile, rowDiff, columnDiff))
			return false;

		// Does the square have a Piece on it?
		if (tile.getPiece() != null)
			// If so, make sure they're on different sides.
			return side != tile.getPiece().getSide();

		// The play is valid.
		return true;
	}

	public abstract boolean isValidMove(SquareTile tile, int rowDiff, int columnDiff);
	public abstract boolean isBlocked(SquareTile tile, int rowDiff, int columnDiff, int[] rowIndex, int[] columnIndex);

	public SquareTile getTile() {
		return tile;
	}

	public void setTile(SquareTile tile) {
		this.tile = tile;
	}

	public Side getSide() {
		return side;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " (" + side.name().charAt(0) + ")";
	}
}
