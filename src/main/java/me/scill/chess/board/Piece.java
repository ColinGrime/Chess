package me.scill.chess.board;

import me.scill.chess.enums.Side;
import me.scill.chess.display.Tile;

public abstract class Piece {

	private final Side side;
	private Tile tile;

	public Piece(Side side) {
		this.side = side;
	}

	public boolean isValidPlay(Tile tile) {
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
		return isValidMove(tile, rowDiff, columnDiff);
	}

	public abstract boolean isValidMove(Tile tile, int rowDiff, int columnDiff);
	public abstract boolean isBlocked(Tile tile, int rowDiff, int columnDiff, int[] rowIndex, int[] columnIndex);

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
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
