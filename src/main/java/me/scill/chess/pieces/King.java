package me.scill.chess.pieces;

import me.scill.chess.board.Piece;
import me.scill.chess.enums.Side;
import me.scill.chess.display.Tile;

public class King extends Piece {

	private static Tile attemptedMove = null;

	public King(Side side) {
		super(side);
	}

	@Override
	public boolean isValidMove(Tile tile, int rowDiff, int columnDiff) {
		// King can move 1 space in any direction, even diagonal.
		if (rowDiff > 1 || columnDiff > 1)
			return false;

		// Another King is checking for moves...
		if (attemptedMove != null)
			return true;

		attemptedMove = tile;

		// King cannot put himself in check
		for (Tile t : tile.getBoard().getTiles()) {
			// Ignore piece-less tiles and allies.
			if (t.getPiece() == null || t.getPiece().getSide() == getSide())
				continue;

			System.out.println(t);

			// King would be in check if he moves.
			if (t.getPiece().getPossibleMoves().contains(tile)) {
				attemptedMove = null;
				return false;
			}
		}

		attemptedMove = null;
		return true;
	}

	@Override
	protected boolean isBlocked(boolean isRowBlocked, boolean isColumnBlocked, boolean hasMovedRow, boolean hasMovedColumn) {
		return false;
	}

	public static Tile getAttemptedMove() {
		return attemptedMove;
	}
}
