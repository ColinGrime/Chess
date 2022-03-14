package me.colin.chess.piece.pieces;

import me.colin.chess.display.board.Board;
import me.colin.chess.display.tile.Tile;
import me.colin.chess.enums.Side;
import me.colin.chess.piece.AbstractPiece;

public class Knight extends AbstractPiece {

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
