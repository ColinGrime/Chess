package me.scill.chess.pieces;

import me.scill.chess.Piece;
import me.scill.chess.display.Board;
import me.scill.chess.enums.Side;
import me.scill.chess.display.Tile;

public class Knight extends Piece {

	public Knight(Board board, Side side) {
		super(board, side);
	}

	@Override
	public boolean isValidMove(Tile move, int rowDiff, int columnDiff) {
		// The move is a "L" shape.
		return (rowDiff == 2 && columnDiff == 1) || (rowDiff == 1 && columnDiff == 2);
	}

	@Override
	public boolean isBlocked(boolean isRowBlocked, boolean isColumnBlocked, boolean hasMovedRow, boolean hasMovedColumn) {
		return false;
	}
}
