package me.colin.chess.display.tile;

import me.colin.chess.display.board.Board;
import me.colin.chess.piece.Piece;

public interface Tile {

	/**
	 * Indicates whether the tile needs a circle
	 * around it when a piece is clicked on,
	 *
	 * @param drawCircle true if the tile should draw a circle
	 */
	void setDrawCircle(boolean drawCircle);

	/**
	 * @return board of the tile
	 */
	Board getBoard();

	/**
	 * @return piece of the tile
	 */
	Piece getPiece();

	/**
	 * Sets the piece on the tile.
	 * @param piece any chess piece
	 */
	void setPiece(Piece piece);

	/**
	 * @return row of the tile
	 */
	int getRow();

	/**
	 * @return column of the tile
	 */
	char getColumn();
}
