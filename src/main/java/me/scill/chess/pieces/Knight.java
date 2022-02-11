package me.scill.chess.pieces;

import me.scill.chess.board.Piece;
import me.scill.chess.enums.Side;
import me.scill.chess.display.Tile;

public class Knight extends Piece {

	public Knight(Side side) {
		super(side);
	}

	@Override
	public boolean isValidMove(Tile tile, int rowDiff, int columnDiff) {
		// The move is a "L" shape.
		return (rowDiff == 2 && columnDiff == 1) || (rowDiff == 1 && columnDiff == 2);
	}

	@Override
	public boolean isBlocked(Tile tile, int rowDiff, int columnDiff, int[] rowIndex, int[] columnIndex) {
		return false;
	}
}
