package me.scill.chess.pieces;

import me.scill.chess.board.Piece;
import me.scill.chess.Side;
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
	public boolean isBlocked(Tile tile, int rowDiff, int columnDiff, int[] rowIndex, int[] columnIndex) {
		return false;
	}
}
