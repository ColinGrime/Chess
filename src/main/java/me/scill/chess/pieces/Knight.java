package me.scill.chess.pieces;

import me.scill.chess.Piece;
import me.scill.chess.Side;
import me.scill.chess.display.SquareTile;

public class Knight extends Piece {

	public Knight(Side side) {
		super(side);
	}

	@Override
	public boolean isValidMove(SquareTile tile, int rowDiff, int columnDiff) {
		// The move is a "L" shape.
		return (rowDiff == 2 && columnDiff == 1) || (rowDiff == 1 && columnDiff == 2);
	}

	@Override
	public boolean isBlocked(SquareTile tile, int rowDiff, int columnDiff, int[] rowIndex, int[] columnIndex) {
		return false;
	}
}
