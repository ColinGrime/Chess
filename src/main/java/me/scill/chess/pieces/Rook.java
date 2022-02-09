package me.scill.chess.pieces;

import me.scill.chess.Piece;
import me.scill.chess.Side;
import me.scill.chess.display.SquareTile;

import java.util.List;

public class Rook extends Piece {

	public Rook(Side side) {
		super(side);
	}

	@Override
	public boolean isValidMove(SquareTile tile, int rowDiff, int columnDiff) {
		// Rook either moves its row OR column.
		return !(rowDiff >= 1 && columnDiff >= 1);
	}

	@Override
	public boolean isBlocked(SquareTile tile, int rowDiff, int columnDiff, int[] rowIndex, int[] columnIndex) {
		List<SquareTile> tiles = tile.getBoard().getTiles();
		int row = tile.getRowPos();
		int column = tile.getColumnPos();

		// The Rook moved its row.
		if (rowDiff > 1) {
			// Returns true if a space in between has a piece on it.
			return tiles.stream()
					.filter(t -> t.getRowPos() > rowIndex[0] && t.getRowPos() < rowIndex[1])
					.anyMatch(t -> t.getColumnPos() == column && t.getPiece() != null);
		}

		// The Rook moved its column.
		else if (columnDiff > 1) {
			// Returns true if a space in between has a piece on it.
			return tiles.stream()
					.filter(t -> t.getColumnPos() > columnIndex[0] && t.getColumnPos() < columnIndex[1])
					.anyMatch(t -> t.getRowPos() == row && t.getPiece() != null);
		}

		// Path isn't blocked.
		return false;
	}
}
