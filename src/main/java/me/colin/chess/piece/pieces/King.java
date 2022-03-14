package me.colin.chess.piece.pieces;

import me.colin.chess.display.board.Board;
import me.colin.chess.display.tile.Tile;
import me.colin.chess.enums.Side;
import me.colin.chess.piece.AbstractPiece;
import me.colin.chess.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class King extends AbstractPiece {

	private static Tile attemptedMove = null;

	public King(Board board, Side side) {
		super(board, side);
	}

	@Override
	public boolean isValidMove(Tile move, int rowDiff, int columnDiff) {
		// Check if the King is attempting to castle.
		boolean isCastling = isValidCastling(move, rowDiff, columnDiff);

		// King can move 1 space in any direction, even diagonal.
		if ((rowDiff > 1 || columnDiff > 1) && !isCastling) {
			return false;
		}

		// Another King is checking for moves...
		if (attemptedMove != null)
			return true;

		attemptedMove = move;

		// King cannot put himself in check
		for (Tile t : move.getBoard().getTiles()) {
			// Ignore piece-less tiles and allies.
			if (t.getPiece() == null || t.getPiece().getSide() == getSide())
				continue;

			// King would be in check if he moves.
			if (move != t && t.getPiece().getMoves().contains(move)) {
				attemptedMove = null;
				return false;
			}
		}

		attemptedMove = null;
		return true;
	}

	@Override
	public boolean isBlocked(boolean isRowBlocked, boolean isColumnBlocked, boolean hasMovedRow, boolean hasMovedColumn) {
		return false;
	}

	/**
	 * Checks if the king can castle.
	 *
	 * @param tile tile to check
	 * @param rowDiff difference in rows
	 * @param columnDiff difference in columns
	 * @return true if the king can castle
	 */
	private boolean isValidCastling(Tile tile, int rowDiff, int columnDiff) {
		if (rowDiff > 0 || columnDiff != 2 || getTimesMoved() > 0) {
			return false;
		}

		boolean isLeft = tile.getColumn() <= 'd';
		Rook rook = getRook(isLeft);
		List<Tile> getTilesInBetween = getTilesInBetween(isLeft);

		if (rook != null && rook.getTimesMoved() == 0 && rook.getSide() == getSide()) {
			return !isBlocked(rook.getTile(), getTilesInBetween, false);
		}

		return false;
	}

	/**
	 * Gets the rook on either side of the king.
	 *
	 * @param isLeft true if the rook is on the left
	 * @return the Rook on either the left or right
	 */
	private Rook getRook(boolean isLeft) {
		char column = isLeft ? 'a' : 'h';
		Tile tile = getTile().getBoard().getTile(getTile().getRow(), column);

		if (tile.getPiece() == null) {
			return null;
		}

		Piece piece = tile.getPiece();
		if (piece.getSide() != getSide() || !(piece instanceof Rook)) {
			return null;
		}

		return ((Rook) piece);
	}

	/**
	 * Gets the tiles in between the king and rook.
	 *
	 * @param isLeft true if the rook is on the left
	 * @return list of tiles
	 */
	private List<Tile> getTilesInBetween(boolean isLeft) {
		List<Tile> tilesInBetween = new ArrayList<>();
		Board board = getTile().getBoard();
		int row = getTile().getRow();

		if (isLeft) {
			tilesInBetween.add(board.getTile(row, 'b'));
			tilesInBetween.add(board.getTile(row, 'c'));
			tilesInBetween.add(board.getTile(row, 'd'));
		} else {
			tilesInBetween.add(board.getTile(row, 'f'));
			tilesInBetween.add(board.getTile(row, 'g'));
		}

		return tilesInBetween;
	}

	/**
	 * Checks for castling.
	 * @param tile piece to check against
	 */
	public void checkForCastle(Tile tile) {
		if (Math.abs(getTile().getColumn() - tile.getColumn()) != 2) {
			return;
		}

		boolean isLeft = tile.getColumn() == 'c';
		Rook rook = getRook(isLeft);
		Tile tileToMove = getTile().getBoard().getTile(tile.getRow(), isLeft ? 'd' : 'f');

		if (rook == null)
			return;

		Tile oldTile = rook.getTile();
		tileToMove.setPiece(rook);
		oldTile.setPiece(null);
	}

	public static Tile getAttemptedMove() {
		return attemptedMove;
	}
}
