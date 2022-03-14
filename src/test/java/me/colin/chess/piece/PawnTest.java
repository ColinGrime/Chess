package me.colin.chess.piece;

import me.colin.chess.display.board.AbstractBoard;
import me.colin.chess.display.board.Board;
import me.colin.chess.enums.Side;
import me.colin.chess.piece.pieces.Pawn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PawnTest {

	@Test
	public void whitePawnsShouldOnlyMoveUp() {
		Board board = new AbstractBoard();
		Pawn pawn = new Pawn(board, Side.WHITE);
		board.getTile(5, 'a').setPiece(pawn);

		assertTrue(pawn.getMoves().contains(board.getTile(6, 'a')));
		assertFalse(pawn.getMoves().contains(board.getTile(4, 'a')));
	}

	@Test
	public void blackPawnsShouldOnlyMoveUp() {
		Board board = new AbstractBoard();
		Pawn pawn = new Pawn(board, Side.BLACK);
		board.getTile(5, 'a').setPiece(pawn);

		assertFalse(pawn.getMoves().contains(board.getTile(6, 'a')));
		assertTrue(pawn.getMoves().contains(board.getTile(4, 'a')));
	}

	@Test
	public void pawnsShouldHaveTwoMovesAtTheStart() {
		Board board = new AbstractBoard();
		Pawn pawn = (Pawn) board.getTile(2, 'a').getPiece();

		assertInstanceOf(Pawn.class, pawn);
		assertEquals(2, pawn.getMoves().size());
	}

	@Test
	public void pawnsShouldHaveOneMoveAfterTheStart() {
		Board board = new AbstractBoard();
		Pawn pawn = (Pawn) board.getTile(2, 'a').getPiece();

		Pawn pawnSpy = spy(pawn);
		when(pawnSpy.getTimesMoved()).thenReturn(1);

		assertEquals(1, pawnSpy.getMoves().size());
	}

	@Test
	public void pawnsShouldNotBeAbleToMoveForwardIfBlocked() {
		Board board = new AbstractBoard();
		Pawn pawn = (Pawn) board.getTile(2, 'a').getPiece();

		Piece pieceInFront = mock(AbstractPiece.class);
		board.getTile(3, 'a').setPiece(pieceInFront);

		assertEquals(0, pawn.getMoves().size());
	}
}