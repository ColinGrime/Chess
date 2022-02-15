package me.scill.chess.pieces;

import me.scill.chess.Piece;
import me.scill.chess.display.Board;
import me.scill.chess.display.Tile;
import me.scill.chess.enums.Side;

public class Queen extends Piece {

	public Queen(Board board, Side side) {
		super(board, side);
	}

	@Override
	public boolean isValidMove(Tile move, int rowDiff, int columnDiff) {
		// The move is diagonal.
		if (rowDiff == columnDiff)
			return true;

		// Queen either moves its row OR column, but not both.
		return rowDiff >= 1 ^ columnDiff >= 1;
	}

	@Override
	public boolean isBlocked(boolean isRowBlocked, boolean isColumnBlocked, boolean hasMovedRow, boolean hasMovedColumn) {
		// Diagonal movement.
		if (hasMovedRow && hasMovedColumn)
			return isRowBlocked && isColumnBlocked;

		// Row movement.
		else if (hasMovedRow)
			return isRowBlocked;

		// Column movement.
		return isColumnBlocked;
	}
}
