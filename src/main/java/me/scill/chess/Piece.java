package me.scill.chess;

import me.scill.chess.display.SquareTile;

public abstract class Piece {

	private final Side side;
	private SquareTile position;

	public Piece(Side side) {
		this.side = side;
	}

	public abstract boolean isValidMove(SquareTile position);

	public SquareTile getPosition() {
		return position;
	}

	public void setPosition(SquareTile position) {
		this.position = position;
	}

	public Side getSide() {
		return side;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " (" + side.name().charAt(0) + ")";
	}
}
