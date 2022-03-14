package me.colin.chess.piece.pieces;

import me.colin.chess.display.board.Board;
import me.colin.chess.display.tile.Tile;
import me.colin.chess.enums.Side;
import me.colin.chess.piece.AbstractPiece;

public class Bishop extends AbstractPiece {

	public Bishop(Board board, Side side) {
		super(board, side);
	}

	@Override
	public boolean isValidMove(Tile move, int rowDiff, int columnDiff) {
		// The move is diagonal.
		return rowDiff == columnDiff;
	}

	@Override
	public boolean isBlocked(boolean isRowBlocked, boolean isColumnBlocked, boolean hasMovedRow, boolean hasMovedColumn) {
		// Diagonal movement.
		return isRowBlocked && isColumnBlocked;
	}
}
