package me.scill.chess.pieces;

import me.scill.chess.board.Piece;
import me.scill.chess.enums.Side;
import me.scill.chess.display.Tile;

public class King extends Piece {

	public King(Side side) {
		super(side);
	}

	@Override
	public boolean isValidMove(Tile tile, int rowDiff, int columnDiff) {
		// King can move 1 space in any direction, even diagonal.
		return rowDiff <= 1 && columnDiff <= 1;
	}

	@Override
	protected boolean isBlocked(boolean isRowBlocked, boolean isColumnBlocked, boolean hasMovedRow, boolean hasMovedColumn) {
		return false;
	}
}
