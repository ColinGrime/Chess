package me.colin.chess.display.board;

import me.colin.chess.display.Display;
import me.colin.chess.display.StretchIcon;
import me.colin.chess.display.UpgradePanel;
import me.colin.chess.display.tile.Tile;
import me.colin.chess.enums.Side;
import me.colin.chess.piece.Piece;
import me.colin.chess.piece.pieces.King;

import java.awt.*;
import java.util.List;
import java.util.Map;

public interface Board {

	/**
	 * Initializes the board.
	 *
	 * This method shouldn't do any display-type
	 * actions, but rather, it should get the board
	 * ready to display things.
	 */
	void init();

	/**
	 * Sets the display up.
	 *
	 * @param display the main display
	 * @param size size of the screen
	 */
	void setupDisplay(Display display, Dimension size);

	/**
	 * Displays the board.
	 *
	 * It will not work unless {@link #setupDisplay(Display, Dimension)}
	 * is called first.
	 */
	void display();

	/**
	 * Gets the upgrade panel for the specified side.
	 *
	 * @param side WHITE or BLACK
	 * @return upgrade panel for either BLACK or WHITE
	 */
	UpgradePanel getUpgrades(Side side);

	/**
	 * @return the current turn's side
	 */
	Side getCurrentTurn();

	/**
	 * Switches sides so the other side can move.
	 */
	void nextTurn();

	/**
	 * Checks if the piece's move causes a check.
	 *
	 * @param piece any piece
	 */
	void checkForCheck(Piece piece);

	/**
	 * Sets the specified king in check.
	 *
	 * In doing so, it calculates all the
	 * possible moves left of the opponent.
	 *
	 * If there are no possible moves left,
	 * it's a checkmate and the opponent loses.
	 *
	 * @param checkedKing the king that is in check
	 * @param assassin the piece doing the check
	 */
	void setCheck(King checkedKing, Piece assassin);

	/**
	 * Resets the current check.
	 */
	void resetCheck();

	/**
	 * @return king that is currently in check
	 */
	Piece getCheckedKing();

	/**
	 * The moves left to get out of check.
	 * @return moves left
	 */
	Map<Piece, List<Tile>> getMovesLeft();

	/**
	 * The moves the specified piece can
	 * take to get out of check.
	 *
	 * @param piece any piece
	 * @return moves left
	 */
	List<Tile> getMovesLeft(Piece piece);

	/**
	 * @return tiles of the board
	 */
	List<Tile> getTiles();

	/**
	 * Gets the position given the row and column.
	 *
	 * @param row row of the board
	 * @param column column of the board
	 * @return the Tile object at the given row/column, or (1, A) if not found.
	 */
	Tile getTile(int row, char column);

	/**
	 * Gets the icon of the specified path.
	 *
	 * @param path path of an image
	 * @return the StretchIcon of the image if available
	 */
	StretchIcon getImage(String path);

	/**
	 * @return size of each tile
	 */
	Dimension getSize();
}
