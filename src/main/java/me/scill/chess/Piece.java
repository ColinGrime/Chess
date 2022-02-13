package me.scill.chess;

import me.scill.chess.display.Tile;
import me.scill.chess.enums.Side;
import me.scill.chess.pieces.King;
import me.scill.chess.utilities.ResourceUtility;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Piece {

	// The Piece currently checking a King.
	private static Piece checkedKing = null;
	private static Piece assassin = null;
	private static final Map<Piece, List<Tile>> movesLeft = new HashMap<>();

	private final Side side;
	private final ImageIcon icon;

	private Tile tile;
	private int timesMoved = 0;

	public Piece(Side side) {
		this.side = side;

		String path = side.name() + "/" + getClass().getSimpleName() + ".png";
		this.icon = new ImageIcon(ResourceUtility.getImage(path, 512, 512));
	}

	/**
	 * Gets a list of possible, valid moves.
	 * @return list of possible moves.
	 */
	public List<Tile> getPossibleMoves() {
		List<Tile> possibleMoves = new ArrayList<>();
		List<Tile> moves = getMoves();

		for (Tile tile : moves) {
			// If the move is blocked, it's invalid
			if (isBlocked(tile, moves, false))
				continue;

			// If the move has a Piece on it, make sure it's NOT an ally (unless it's an attempted King move).
			if (tile.getPiece() == null || tile.getPiece().getSide() != getSide() || tile == King.getAttemptedMove())
				possibleMoves.add(tile);
		}

		return possibleMoves;
	}

	/**
	 * Gets a list of moves a Piece can make
	 *   without accounting for it being blocked.
	 * @return list of moves.
	 */
	private List<Tile> getMoves() {
		List<Tile> moves = new ArrayList<>();

		for (Tile tile : getTile().getBoard().getTiles()) {
			// If the move is valid, you can move there.
			if (isValidMove(tile))
				moves.add(tile);
		}

		return moves;
	}

	public boolean isValidMove(Tile tile) {
		// Row and column differences
		int rowDiff = Math.abs(tile.getRow() - getTile().getRow());
		int columnDiff = Math.abs(tile.getColumn() - getTile().getColumn());

		return isValidMove(tile, rowDiff, columnDiff);
	}

	protected abstract boolean isValidMove(Tile tile, int rowDiff, int columnDiff);

	public boolean isBlocked(Tile tile) {
		return isBlocked(tile, getMoves(), false);
	}

	protected boolean isBlocked(Tile tile, List<Tile> moves, boolean canSpaceBlock) {
		// Row and column differences.
		int rowDiff = Math.abs(tile.getRow() - getTile().getRow());
		int columnDiff = Math.abs(tile.getColumn() - getTile().getColumn());

		// If the Piece moved their row/column.
		boolean hasMovedRow = rowDiff > 0;
		boolean hasMovedColumn = columnDiff > 0;

		// Min/max rows and columns.
		int minRow = Math.min(tile.getRow(), getTile().getRow());
		int maxRow = Math.max(tile.getRow(), getTile().getRow());
		int minColumn = Math.min(tile.getColumn(), getTile().getColumn());
		int maxColumn = Math.max(tile.getColumn(), getTile().getColumn());

		for (Tile t : moves) {
			// If there's no Piece, it can't be blocking.
			if (!canSpaceBlock && t.getPiece() == null)
				continue;

			// If it hasn't moved rows, don't check different rows.
			else if (!hasMovedRow && t.getRow() != tile.getRow())
				continue;

			// If it hasn't moved columns, don't check different columns.
			else if (!hasMovedColumn && t.getColumn() != tile.getColumn())
				continue;

			// Checked for blocked rows/columns.
			boolean isRowBlocked = t.getRow() > minRow && t.getRow() < maxRow;
			boolean isColumnBlocked = t.getColumn() > minColumn && t.getColumn() < maxColumn;

			// Check if the Piece is blocking the movement.
			if (isBlocked(isRowBlocked, isColumnBlocked, hasMovedRow, hasMovedColumn))
				return true;
		}

		return false;
	}

	protected abstract boolean isBlocked(boolean isRowBlocked, boolean isColumnBlocked, boolean hasMovedRow, boolean hasMovedColumn);

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
		return icon;
	}

	public ImageIcon getIcon(int size) {
		String path = side.name() + "/" + getClass().getSimpleName() + ".png";
		return new ImageIcon(ResourceUtility.getImage(path, size, size));
	}

	public void addTimeMoved() {
		timesMoved++;
	}

	public int getTimesMoved() {
		return timesMoved;
	}

	public static void setCheck(King checkedKing, Piece assassin) {
		Piece.checkedKing = checkedKing;
		Piece.assassin = assassin;
		movesLeft.clear();

		for (Tile tile : assassin.getTile().getBoard().getTiles()) {
			if (tile.getPiece() == null || tile.getPiece().getSide() == assassin.getSide())
				continue;

			for (Tile t : tile.getPiece().getPossibleMoves()) {
				// If the move kills the assassin, add it to list of moves left.
				if (t == assassin.getTile()) {
					addMoveLeft(tile.getPiece(), t);
					continue;
				}

				// Check possible escapes for the King.
				if (tile.getPiece() == checkedKing) {
					if (!assassin.isValidMove(t))
						addMoveLeft(checkedKing, t);
					continue;
				}

				// If the move is invalid for the assassin, move on...
				if (!assassin.isValidMove(t))
					continue;

				// If the assassin gets blocked by this move, add it to the list of moves left.
				if (assassin.isBlocked(checkedKing.getTile(), List.of(t), true))
					addMoveLeft(tile.getPiece(), t);
			}
		}

//		if (movesLeft.isEmpty())
//			checkedKing.getTile().getBoard();
	}

	public static void resetCheck() {
		Piece.checkedKing = null;
		Piece.assassin = null;
		movesLeft.clear();
	}

	public static Piece getCheckedKing() {
		return checkedKing;
	}

	public static Piece getAssassin() {
		return assassin;
	}

	public static List<Tile> getMovesLeft(Piece piece) {
		return movesLeft.getOrDefault(piece, new ArrayList<>());
	}

	private static void addMoveLeft(Piece piece, Tile tile) {
		List<Tile> moves = movesLeft.getOrDefault(piece, new ArrayList<>());
		moves.add(tile);
		movesLeft.put(piece, moves);
	}

	public static boolean isMoveIllegal(Piece piece, Tile tile) {
		return !movesLeft.isEmpty() && !getMovesLeft(piece).contains(tile);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " (" + side.name().charAt(0) + ")";
	}
}
