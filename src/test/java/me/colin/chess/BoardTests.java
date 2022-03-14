package me.colin.chess;

import me.colin.chess.display.board.AbstractBoard;
import me.colin.chess.display.board.Board;
import me.colin.chess.display.tile.Tile;
import me.colin.chess.piece.pieces.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class BoardTests {

	@Test
	public void rooksShouldOnlyBeAtColumnsAAndH() {
		Board board = new AbstractBoard();
		List<Tile> tiles = board.getTiles();

		for (Tile tile : tiles) {
			if (tile.getColumn() != 'a' && tile.getColumn() != 'h')
				assertFalse(tile.getPiece() instanceof Rook);
		}
	}

	@Test
	public void knightShouldOnlyBeAtColumnsBAndG() {
		Board board = new AbstractBoard();
		List<Tile> tiles = board.getTiles();

		for (Tile tile : tiles) {
			if (tile.getColumn() != 'b' && tile.getColumn() != 'g')
				assertFalse(tile.getPiece() instanceof Knight);
		}
	}

	@Test
	public void bishopsShouldOnlyBeAtColumnsCAndF() {
		Board board = new AbstractBoard();
		List<Tile> tiles = board.getTiles();

		for (Tile tile : tiles) {
			if (tile.getColumn() != 'c' && tile.getColumn() != 'f')
				assertFalse(tile.getPiece() instanceof Bishop);
		}
	}

	@Test
	public void queenShouldOnlyBeAtColumnD() {
		Board board = new AbstractBoard();
		List<Tile> tiles = board.getTiles();

		for (Tile tile : tiles) {
			if (tile.getColumn() != 'd')
				assertFalse(tile.getPiece() instanceof Queen);
		}
	}

	@Test
	public void kingShouldOnlyBeAtColumnE() {
		Board board = new AbstractBoard();
		List<Tile> tiles = board.getTiles();

		for (Tile tile : tiles) {
			if (tile.getColumn() != 'e')
				assertFalse(tile.getPiece() instanceof King);
		}
	}

	@Test
	public void pawnsShouldFillRowsTwoAndSeven() {
		Board board = new AbstractBoard();
		List<Tile> tiles = board.getTiles();

		for (Tile tile : tiles) {
			if (tile.getRow() == 2 || tile.getRow() == 7)
				assertInstanceOf(Pawn.class, tile.getPiece());
		}
	}
}
