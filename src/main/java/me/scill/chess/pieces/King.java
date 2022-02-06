package me.scill.chess.pieces;

import me.scill.chess.Piece;
import me.scill.chess.Side;
import me.scill.chess.display.SquareTile;

public class King extends Piece {

	public King(Side side) {
		super(side);
	}

	@Override
	public boolean isValidMove(SquareTile position) {
		int rowDifference = Math.abs(position.getRowPos() - getPosition().getRowPos());
		int columnDifference = Math.abs(position.getColumnPos() - getPosition().getColumnPos());

		return rowDifference <= 1 && columnDifference <= 1;
	}
}
