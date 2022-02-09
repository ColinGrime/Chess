package me.scill.chess.pieces;

import me.scill.chess.Piece;
import me.scill.chess.Side;
import me.scill.chess.display.SquareTile;

import java.util.List;

public class Bishop extends Piece {

	public Bishop(Side side) {
		super(side);
	}

	@Override
	public boolean isValidMove(SquareTile tile, int rowDiff, int columnDiff) {
		// The move is diagonal.
		return rowDiff == columnDiff;
	}

	@Override
	public boolean isBlocked(SquareTile tile, int rowDiff, int columnDiff, int[] rowIndex, int[] columnIndex) {
		List<SquareTile> tiles = tile.getBoard().getTiles();
		int row = tile.getRowPos();
		int column = tile.getColumnPos();

		// Returns true if a space in between has a piece on it.
		return tiles.stream()
				.filter(t -> t.getRowPos() > rowIndex[0] && t.getRowPos() < rowIndex[1])
				.filter(t -> t.getColumnPos() > columnIndex[0] && t.getColumnPos() < columnIndex[1])
				.anyMatch(t -> Math.abs(t.getRowPos() - row) == Math.abs(t.getColumnPos() - column) && t.getPiece() != null);
	}
}
