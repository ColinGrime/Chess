package me.scill.chess.board;

import me.scill.chess.Side;
import me.scill.chess.display.SquareTile;
import me.scill.chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private final int SIZE = 8;
	private final List<SquareTile> tiles = new ArrayList<>();

	public Board() {}

	public void setupBoard() {
		createSide(Side.WHITE);
		createSide(Side.BLACK);
	}

	/*
	 * Constructs a side in chess.
	 */
	private void createSide(Side side) {
		// White starts at row 1
		// Black starts at row 8
		int firstRow = side == Side.WHITE ? 1 : 8;
		int secondRow = side == Side.WHITE ? 2 : 7;

		// Sets all the chest pieces to its tiles.
		getTile(firstRow, 'a').setPiece(new Rook(side));
		getTile(firstRow, 'b').setPiece(new Knight(side));
		getTile(firstRow, 'c').setPiece(new Bishop(side));
		getTile(firstRow, 'd').setPiece(new Queen(side));
		getTile(firstRow, 'e').setPiece(new King(side));
		getTile(firstRow, 'f').setPiece(new Bishop(side));
		getTile(firstRow, 'g').setPiece(new Knight(side));
		getTile(firstRow, 'h').setPiece(new Rook(side));

		// Sets all the pawns to its positions.
		fillPawns(secondRow, side);
	}

	/*
	 * Fills pawns in the A-H columns of the selected row.
	 */
	private void fillPawns(int row, Side side) {
		for (char column='a'; column<='h'; column++)
			getTile(row, column).setPiece(new Pawn(side));
	}

	/*
	 * Gets the position given the row and column.
	 */
	private SquareTile getTile(int row, char column) {
		for (SquareTile pos : tiles) {
			if (pos.getRowPos() == row && pos.getColumnPos() == column)
				return pos;
		}

		// If the tile isn't found, returns the first tile (bottom left).
		return new SquareTile(this, 1, 'a');
	}

	public int getSIZE() {
		return SIZE;
	}

	public List<SquareTile> getTiles() {
		return tiles;
	}
}
