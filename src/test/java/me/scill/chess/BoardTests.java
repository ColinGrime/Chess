package me.scill.chess;

import me.scill.chess.display.Board;
import me.scill.chess.display.Tile;
import me.scill.chess.pieces.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {

	@Test
	public void thirtyTwoPiecesShouldBeCreated() {
		Board board = new Board();
		assertEquals(32, board.getPiecesLeft());
	}

	@Test
	public void rooksShouldOnlyBeAtColumnsAAndH() {
		Board board = new Board();
		List<Tile> tiles = board.getTiles();

		for (Tile tile : tiles) {
			if (tile.getColumn() != 'a' && tile.getColumn() != 'h')
				assertFalse(tile.getPiece() instanceof Rook);
		}
	}

	@Test
	public void knightShouldOnlyBeAtColumnsBAndG() {
		Board board = new Board();
		List<Tile> tiles = board.getTiles();

		for (Tile tile : tiles) {
			if (tile.getColumn() != 'b' && tile.getColumn() != 'g')
				assertFalse(tile.getPiece() instanceof Knight);
		}
	}

	@Test
	public void bishopsShouldOnlyBeAtColumnsCAndF() {
		Board board = new Board();
		List<Tile> tiles = board.getTiles();

		for (Tile tile : tiles) {
			if (tile.getColumn() != 'c' && tile.getColumn() != 'f')
				assertFalse(tile.getPiece() instanceof Bishop);
		}
	}

	@Test
	public void queenShouldOnlyBeAtColumnD() {
		Board board = new Board();
		List<Tile> tiles = board.getTiles();

		for (Tile tile : tiles) {
			if (tile.getColumn() != 'd')
				assertFalse(tile.getPiece() instanceof Queen);
		}
	}

	@Test
	public void kingShouldOnlyBeAtColumnE() {
		Board board = new Board();
		List<Tile> tiles = board.getTiles();

		for (Tile tile : tiles) {
			if (tile.getColumn() != 'e')
				assertFalse(tile.getPiece() instanceof King);
		}
	}

	@Test
	public void pawnsShouldFillRowsTwoAndSeven() {
		Board board = new Board();
		List<Tile> tiles = board.getTiles();

		for (Tile tile : tiles) {
			if (tile.getRow() == 2 || tile.getRow() == 7)
				assertInstanceOf(Pawn.class, tile.getPiece());
		}
	}

	@Test
	public void pawnsShouldHaveTwoMovesAtTheStart() {
		Board board = new Board();
		Piece pawn = board.getTile(2, 'a').getPiece();
		assertEquals(2, pawn.getMoves().size());
	}
}
