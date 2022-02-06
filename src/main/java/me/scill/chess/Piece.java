package me.scill.chess;

import me.scill.chess.display.SquareTile;

public abstract class Piece {

	private final Side side;
	private SquareTile position;

	public Piece(Side side) {
		this.side = side;
	}

	public boolean isValidPlay(SquareTile position) {
		// Checks if the Piece can move to that square.
		if (!isValidMove(position))
			return false;

		// Does the square have a Piece on it?
		if (position.getPiece() != null)
			// If so, make sure they're on different sides.
			return side != position.getPiece().getSide();

		// Square is valid.
		return true;
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
