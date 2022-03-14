package me.colin.chess.piece;

import me.colin.chess.display.StretchIcon;
import me.colin.chess.display.board.Board;
import me.colin.chess.display.tile.Tile;
import me.colin.chess.enums.Side;
import me.colin.chess.piece.pieces.King;
import me.colin.chess.utils.ResourceUtility;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPiece implements Piece {

	private final Board board;
	private final Side side;
	private StretchIcon icon;

	private Tile tile;
	private int timesMoved = 0;

	public AbstractPiece(Board board, Side side) {
		this.board = board;
		this.side = side;
	}

	@Override
	public List<Tile> getMoves(Tile...whitelist) {
		List<Tile> possibleMoves = new ArrayList<>();
		List<Tile> moves = getAllMoves();

		for (Tile move : moves) {
			// If the move is blocked, it's invalid
			if (isBlocked(move, moves, false, whitelist)) {
				continue;
			}

			// If the move has a Piece on it, make sure it's NOT an ally (unless it's an attempted King move).
			if (move.getPiece() == null || move.getPiece().getSide() != getSide() || move == King.getAttemptedMove()) {
				possibleMoves.add(move);
			}
		}

		return possibleMoves;
	}

	/**
	 * Gets a list of moves a Piece can make
	 * without accounting for it being blocked.
	 *
	 * @return list of moves.
	 */
	private List<Tile> getAllMoves() {
		List<Tile> validMoves = new ArrayList<>();

		for (Tile move : getTile().getBoard().getTiles()) {
			// If the move is valid, you can move there.
			if (isValidMove(move)) {
				validMoves.add(move);
			}
		}

		return validMoves;
	}

	@Override
	public boolean isValidMove(Tile move) {
		// Row and column differences
		int rowDiff = Math.abs(move.getRow() - getTile().getRow());
		int columnDiff = Math.abs(move.getColumn() - getTile().getColumn());

		return isValidMove(move, rowDiff, columnDiff);
	}

	@Override
	public boolean isLegalMove(Tile move) {
		if (!getBoard().getMovesLeft().isEmpty())
			return getBoard().getMovesLeft(this).contains(move);

		// Iterates over each tile, only caring about those with pieces.
		for (Tile tile : getBoard().getTiles()) {
			if (tile.getPiece() == null)
				continue;

			// This Piece wouldn't check your King since you are attempting to kill it.
			if (move.getPiece() != null && move.getPiece() == tile.getPiece())
				continue;

			// Iterates over each possible move, only caring about those who effect a King.
			for (Tile t : tile.getPiece().getMoves(getTile())) {
				if (t.getPiece() == null || !(t.getPiece() instanceof King))
					continue;

				// Check has to come from the other side.
				// You CANNOT check your own King!
				if (getSide() != t.getPiece().getSide() || tile.getPiece().getSide() == t.getPiece().getSide())
					continue;

				// Attacker can't be blocked by a Tile it can't even move to...
				if (!tile.getPiece().isValidMove(move) || !tile.getPiece().isBlocked(t, List.of(move), true)) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean isBlocked(Tile move, List<Tile> moves, boolean isAttemptingMove, Tile...whitelist) {
		// Row and column differences.
		Tile posToCheck = getTile();
		int rowDiff = Math.abs(move.getRow() - posToCheck.getRow());
		int columnDiff = Math.abs(move.getColumn() - posToCheck.getColumn());

		// If the Piece moved their row/column.
		boolean hasMovedRow = rowDiff > 0;
		boolean hasMovedColumn = columnDiff > 0;

		// Min/max rows and columns.
		int minRow = Math.min(move.getRow(), posToCheck.getRow());
		int maxRow = Math.max(move.getRow(), posToCheck.getRow());
		int minColumn = Math.min(move.getColumn(), posToCheck.getColumn());
		int maxColumn = Math.max(move.getColumn(), posToCheck.getColumn());

		for (Tile tile : moves) {
			// Don't check whitelisted pieces.
			boolean isWhitelisted = false;
			for (Tile t : whitelist) {
				if (tile == t) {
					isWhitelisted = true;
					break;
				}
			}

			// If whitelisted, move on.
			if (isWhitelisted)
				continue;

			// If there's no Piece, it can't be blocking.
			if (!isAttemptingMove && tile.getPiece() == null)
				continue;

			// If it hasn't moved rows, don't check different rows.
			else if (!hasMovedRow && tile.getRow() != move.getRow())
				continue;

			// If it hasn't moved columns, don't check different columns.
			else if (!hasMovedColumn && tile.getColumn() != move.getColumn())
				continue;

			// Checked for blocked rows/columns.
			boolean isRowBlocked = tile.getRow() > minRow && tile.getRow() < maxRow;
			boolean isColumnBlocked = tile.getColumn() > minColumn && tile.getColumn() < maxColumn;

			// Check if the Piece is blocking the movement.
			if (isBlocked(isRowBlocked, isColumnBlocked, hasMovedRow, hasMovedColumn))
				return true;
		}

		return false;
	}

	@Override
	public int getTimesMoved() {
		return timesMoved;
	}

	@Override
	public void moved() {
		timesMoved++;
	}

	@Override
	public Board getBoard() {
		return board;
	}

	@Override
	public Side getSide() {
		return side;
	}

	@Override
	public Tile getTile() {
		return tile;
	}

	@Override
	public void setTile(Tile tile) {
		this.tile = tile;
	}

	@Override
	public ImageIcon getIcon() {
		if (icon == null) {
			String path = side.name().toLowerCase() + "/" + getClass().getSimpleName() + ".png";
			StretchIcon icon = board.getImage(path);
			this.icon = icon != null ? icon : new StretchIcon(ResourceUtility.getImage(path, 512, 512));
		}

		return icon;
	}

	@Override
	public ImageIcon getIcon(int size) {
		String path = side.name() + "/" + getClass().getSimpleName() + ".png";
		return new ImageIcon(ResourceUtility.getImage(path, size, size));
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " (" + side.name().charAt(0) + ")";
	}
}
