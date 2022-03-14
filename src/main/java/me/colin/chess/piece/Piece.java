package me.colin.chess.piece;

import me.colin.chess.display.board.Board;
import me.colin.chess.display.tile.Tile;
import me.colin.chess.enums.Side;

import javax.swing.*;
import java.util.List;

public interface Piece {

	/**
	 * Gets the list of unblocked moves.
	 * @return list of possible moves.
	 */
	List<Tile> getMoves(Tile... whitelist);

	/**
	 * Checks if the move is valid.
	 *
	 * This just checks if the move goes in the
	 * direction that the piece can go.
	 *
	 * For special rulings / to check if it's
	 * actually legal, refer to {@link #isLegalMove(Tile)}.
	 *
	 * @param move any move
	 * @return true if the move is valid
	 */
	boolean isValidMove(Tile move);

	/**
	 * Checks if the move is legal.
	 *
	 * @param move any move
	 * @return true if the move is legal
	 */
	boolean isLegalMove(Tile move);

	/**
	 * Checks if the piece is blocked.
	 *
	 * @param move tile the piece wants to move to
	 * @param moves moves to check against
	 * @param isAttemptingMove is the piece attempting to move
	 * @param whitelist ignore these tiles
	 * @return true if the piece is blocked
	 */
	boolean isBlocked(Tile move, List<Tile> moves, boolean isAttemptingMove, Tile...whitelist);

	/**
	 * Checks if the move is valid.
	 *
	 * @param move tile the piece wants to move to
	 * @param rowDiff row difference between piece tile and move tile
	 * @param columnDiff column difference between pile tile and move tile
	 * @return true if the move is valid
	 */
	boolean isValidMove(Tile move, int rowDiff, int columnDiff);

	/**
	 * Checks if the move is blocked by another piece.
	 *
	 * @param isRowBlocked whether the row is blocked
	 * @param isColumnBlocked whether the column is blocked
	 * @param hasMovedRow whether the piece has moved its row
	 * @param hasMovedColumn whether the piece has moved its column
	 * @return true if the move is blocked
	 */
	boolean isBlocked(boolean isRowBlocked, boolean isColumnBlocked, boolean hasMovedRow, boolean hasMovedColumn);

	/**
	 * Gets the number of times the piece has moved.
	 * @return amount of times moved
	 */
	int getTimesMoved();

	/**
	 * Called when the piece has moved.
	 */
	void moved();

	/**
	 * @return board the piece is on
	 */
	Board getBoard();

	/**
	 * @return side the piece is on
	 */
	Side getSide();

	/**
	 * Gets the tile the piece is on.
	 * @return tile the piece is now on
	 */
	Tile getTile();

	/**
	 * Sets the tile the piece is on.
	 * @param tile any tile
	 */
	void setTile(Tile tile);

	/**
	 * @return the icon of the piece
	 */
	ImageIcon getIcon();

	/**
	 * Gets the piece's icon at a specific size.
	 *
	 * @param size size of the icon
	 * @return the icon with the specified size
	 */
	ImageIcon getIcon(int size);
}
