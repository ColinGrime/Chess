package me.scill.chess.pieces;

import me.scill.chess.Piece;
import me.scill.chess.Side;
import me.scill.chess.display.SquareTile;

public class Pawn extends Piece {

	public Pawn(Side side) {
		super(side);
	}

	@Override
	public boolean isValidMove(SquareTile position) {
		return position.getRowPos() - getPosition().getRowPos() == 1;
	}
}
