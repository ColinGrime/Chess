package me.scill.chess.pieces;

import me.scill.chess.board.Piece;
import me.scill.chess.enums.Side;
import me.scill.chess.display.Tile;

public class Pawn extends Piece {

	public Pawn(Side side) {
		super(side);
	}

	@Override
	public boolean isValidMove(Tile tile, int rowDiff, int columnDiff) {
		int fromRow = getTile().getRow();
		int toRow = tile.getRow();

		// The move has a piece on it.
		if (tile.getPiece() != null || tile == King.getAttemptedMove())
			// Returns true if it moves to the side in the correct direction.
			return columnDiff == 1 && isRightDirection(fromRow, toRow, true);

		// Pawns can't move to the side!
		if (columnDiff >= 1)
			return false;

		// The move is valid.
		return isRightDirection(fromRow, toRow, false);
	}

	@Override
	protected boolean isBlocked(boolean isRowBlocked, boolean isColumnBlocked, boolean hasMovedRow, boolean hasMovedColumn) {
		return false;
	}

	/*
	 * White pieces move up.
	 * Black pieces move down.
	 */
	private boolean isRightDirection(int fromRow, int toRow, boolean isAttacking) {
		// Gets how far the Pawn has moved depending on its side.
		int diff = getSide() == Side.WHITE ? toRow - fromRow : fromRow - toRow;

		// Pawns can move 2 spaces on the first turn if it's normal.
		if (!isAttacking && diff == 2 && getTimesMoved() == 0)
			return true;

		return diff == 1;
	}
}
