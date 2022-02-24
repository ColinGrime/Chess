package me.scill.chess;

import me.scill.chess.display.Board;
import me.scill.chess.display.StretchIcon;
import me.scill.chess.display.Tile;
import me.scill.chess.enums.Side;
import me.scill.chess.utilities.ResourceUtility;

import javax.swing.*;
import java.util.List;

public abstract class Piece {

	private final Board board;
	private final Side side;
	private StretchIcon icon;

	private Tile tile;
	private int timesMoved = 0;

	public Piece(Board board, Side side) {
		this.board = board;
		this.side = side;
	}

	public List<Tile> getMoves(Tile...whitelist) {
		return getTile().getBoard().getMoves(this, getTile(), whitelist);
	}

	/**
	 * @return all moves, including blocked moves, the Piece can take.
	 */
	private List<Tile> getAllMoves() {
		return getTile().getBoard().getAllMoves(this, getTile());
	}

	/**
	 * Checks if the move is valid for the Piece.
	 * @param move any move
	 * @return true if the move is valid
	 */
	public boolean isValidMove(Tile move) {
		return getTile().getBoard().isValidMove(this, getTile(), move);
	}

	/**
	 * Checks if the move is blocked.
	 * @param move any move
	 * @return true if the path to the move is blocked
	 */
	public boolean isBlocked(Tile move) {
		return getTile().getBoard().isBlocked(this, getTile(), move);
	}

	public boolean isBlocked(Tile move, List<Tile> moves, boolean isAttemptingMove) {
		return getTile().getBoard().isBlocked(this, getTile(), move, moves, isAttemptingMove);
	}

	public abstract boolean isValidMove(Tile move, int rowDiff, int columnDiff);
	public abstract boolean isBlocked(boolean isRowBlocked, boolean isColumnBlocked, boolean hasMovedRow, boolean hasMovedColumn);

	public Board getBoard() {
		return board;
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public Side getSide() {
		return side;
	}

	public ImageIcon getIcon() {
		if (icon == null) {
			String path = side.name().toLowerCase() + "/" + getClass().getSimpleName() + ".png";
			StretchIcon icon = board.getImage(path);
			this.icon = icon != null ? icon : new StretchIcon(ResourceUtility.getImage(path, 512, 512));
		}

		return icon;
	}

	public ImageIcon getIcon(int size) {
		String path = side.name() + "/" + getClass().getSimpleName() + ".png";
		return new ImageIcon(ResourceUtility.getImage(path, size, size));
	}

	public int getTimesMoved() {
		return timesMoved;
	}

	public void moved() {
		timesMoved++;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " (" + side.name().charAt(0) + ")";
	}
}
