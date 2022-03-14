package me.colin.chess.piece.pieces;

import me.colin.chess.display.board.Board;
import me.colin.chess.display.tile.Tile;
import me.colin.chess.enums.Side;
import me.colin.chess.piece.AbstractPiece;

public class Queen extends AbstractPiece {

	public Queen(Board board, Side side) {
		super(board, side);
	}

	@Override
	public boolean isValidMove(Tile move, int rowDiff, int columnDiff) {
		// The move is diagonal.
		if (rowDiff == columnDiff) {
			return true;
		}

		// Queen either moves its row OR column, but not both.
		return rowDiff >= 1 ^ columnDiff >= 1;
	}

	@Override
	public boolean isBlocked(boolean isRowBlocked, boolean isColumnBlocked, boolean hasMovedRow, boolean hasMovedColumn) {
		// Diagonal movement.
		if (hasMovedRow && hasMovedColumn) {
			return isRowBlocked && isColumnBlocked;
		}

		// Row movement.
		else if (hasMovedRow) {
			return isRowBlocked;
		}

		// Column movement.
		return isColumnBlocked;
	}
}
