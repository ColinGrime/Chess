package me.scill.chess.pieces;

import me.scill.chess.board.Piece;
import me.scill.chess.enums.Side;
import me.scill.chess.display.Tile;

public class Bishop extends Piece {

	public Bishop(Side side) {
		super(side);
	}

	@Override
	public boolean isValidMove(Tile tile, int rowDiff, int columnDiff) {
		// The move is diagonal.
		return rowDiff == columnDiff;
	}

	@Override
	public boolean isBlocked(boolean isRowBlocked, boolean isColumnBlocked, boolean hasMovedRow, boolean hasMovedColumn) {
		// Diagonal movement.
		return isRowBlocked && isColumnBlocked;
	}
}
