package me.scill.chess.pieces;

import me.scill.chess.Piece;
import me.scill.chess.Side;
import me.scill.chess.display.SquareTile;

public class Bishop extends Piece {

	public Bishop(Side side) {
		super(side);
	}

	@Override
	public boolean isValidMove(SquareTile position) {
		int rowDifference = Math.abs(position.getRowPos() - getPosition().getRowPos());
		int columnDifference = Math.abs(position.getColumnPos() - getPosition().getColumnPos());

		return (rowDifference == columnDifference) && (rowDifference >= 1);
	}
}
