package me.scill.chess.board;

import me.scill.chess.Player;
import me.scill.chess.enums.Side;
import me.scill.chess.display.Tile;
import me.scill.chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private final int SIZE = 8;
	private final List<Tile> tiles = new ArrayList<>();

	private final Player player1 = new Player("White", Side.WHITE);
	private final Player player2 = new Player("Black", Side.BLACK);

	private Side currentTurn = Side.WHITE;

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
	public Tile getTile(int row, char column) {
		for (Tile pos : tiles) {
			if (pos.getRow() == row && pos.getColumn() == column)
				return pos;
		}

		// If the tile isn't found, returns the first tile (bottom left).
		return new Tile(this, 1, 'a');
	}

	public int getSIZE() {
		return SIZE;
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public Side getCurrentTurn() {
		return currentTurn;
	}

	public void nextTurn() {
		currentTurn = currentTurn == Side.WHITE ? Side.BLACK : Side.WHITE;
	}
}
