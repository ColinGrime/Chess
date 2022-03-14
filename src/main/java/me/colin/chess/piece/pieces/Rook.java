package me.colin.chess.piece.pieces;

import me.colin.chess.display.board.Board;
import me.colin.chess.display.tile.Tile;
import me.colin.chess.enums.Side;
import me.colin.chess.piece.AbstractPiece;

public class Rook extends AbstractPiece {

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
		if (hasMovedRow) {
			return isRowBlocked;
		}

		// Diagonal movement.
		return isColumnBlocked;
	}
}
