package me.scill.chess.pieces;

import me.scill.chess.Piece;
import me.scill.chess.display.Board;
import me.scill.chess.enums.Side;
import me.scill.chess.display.Tile;

public class Rook extends Piece {

	public Rook(Board board, Side side) {
		super(board, side);
	}

	@Override
	public boolean isValidMove(Tile move, int rowDiff, int columnDiff) {
		// Rook either moves its row OR column, but not both.
		return rowDiff >= 1 ^ columnDiff >= 1;
	}

	@Override
	public boolean isBlocked(boolean isRowBlocked, boolean isColumnBlocked, boolean hasMovedRow, boolean hasMovedColumn) {
		// Row movement.
		if (hasMovedRow)
			return isRowBlocked;

		// Diagonal movement.
		return isColumnBlocked;
	}
}
