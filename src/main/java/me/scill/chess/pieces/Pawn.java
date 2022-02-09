package me.scill.chess.pieces;

import me.scill.chess.Piece;
import me.scill.chess.Side;
import me.scill.chess.display.SquareTile;

public class Pawn extends Piece {

	public Pawn(Side side) {
		super(side);
	}

	@Override
	public boolean isValidMove(SquareTile tile, int rowDiff, int columnDiff) {
		int fromRow = getTile().getRowPos();
		int toRow = tile.getRowPos();

		// The move has a piece on it.
		if (tile.getPiece() != null)
			// Returns true if it moves to the side in the correct direction.
			return columnDiff == 1 && isRightDirection(fromRow, toRow);

		// Pawns can't move to the side!
		if (columnDiff >= 1)
			return false;

		// The move is valid.
		return isRightDirection(fromRow, toRow);
	}

	@Override
	public boolean isBlocked(SquareTile tile, int rowDiff, int columnDiff, int[] rowIndex, int[] columnIndex) {
		return false;
	}

	/*
	 * White pieces move up.
	 * Black pieces move down.
	 */
	private boolean isRightDirection(int fromRow, int toRow) {
		if (getSide() == Side.WHITE)
			return toRow - fromRow == 1;
		else
			return fromRow - toRow == 1;
	}
}
