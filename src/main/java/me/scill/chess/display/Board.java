package me.scill.chess.display;

import me.scill.chess.Music;
import me.scill.chess.Piece;
import me.scill.chess.enums.Side;
import me.scill.chess.pieces.*;
import me.scill.chess.utilities.ResourceUtility;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board extends JPanel {

	private final Color MAIN = Color.decode("#cedef0"),
						SECONDARY = Color.decode("#6b9bd1");

	private final List<Tile> tiles = new ArrayList<>();
	private final Map<Piece, List<Tile>> movesLeft = new HashMap<>();

	private Display display;
	private Dimension size;
	private UpgradePanel whiteUpgrades, blackUpgrades;

	private Side currentTurn = Side.WHITE;
	private Piece checkedKing = null, assassin = null;

	public Board() {
		createTiles();
		createSide(Side.WHITE);
		createSide(Side.BLACK);
	}

	/*
	 * Creates all the Tiles.
	 */
	private void createTiles() {
		int index = 0;

		for (int row=0; row<8; row++) {
			for (char column='a'; column<'a'+8; column++) {
				// Creates the Color for the Tile.
				Color color = index++ % 2 == 0 ? MAIN : SECONDARY;
				tiles.add(new Tile(this, 8 - row, column, color));
			}
			index++;
		}
	}

	/**
	 * Creates one of the sides of the board.
	 * @param side the white or black side
	 */
	private void createSide(Side side) {
		// White starts at row 1
		// Black starts at row 8
		int firstRow = side == Side.WHITE ? 1 : 8;
		int secondRow = side == Side.WHITE ? 2 : 7;

		// Sets all the chest pieces to its tiles.
		getTile(firstRow, 'a').setPiece(new Rook(this, side));
		getTile(firstRow, 'b').setPiece(new Knight(this, side));
		getTile(firstRow, 'c').setPiece(new Bishop(this, side));
		getTile(firstRow, 'd').setPiece(new Queen(this, side));
		getTile(firstRow, 'e').setPiece(new King(this, side));
		getTile(firstRow, 'f').setPiece(new Bishop(this, side));
		getTile(firstRow, 'g').setPiece(new Knight(this, side));
		getTile(firstRow, 'h').setPiece(new Rook(this, side));

		// Sets all the pawns to its positions.
		fillPawns(secondRow, side);
	}

	/**
	 * Fills pawns in the A-H columns of the selected row.
	 * @param row 2 or 7
	 * @param side the white or black side
	 */
	private void fillPawns(int row, Side side) {
		for (char column='a'; column<='h'; column++)
			getTile(row, column).setPiece(new Pawn(this, side));
	}

	public void setupDisplay(Display display, Dimension size) {
		this.display = display;
		this.size = new Dimension((int) size.getHeight() / 8, (int) size.getHeight() / 8);

		setBackground(new Color(40, 44, 52));
		setLayout(new GridBagLayout());

		// Sets up the grid & puts the pieces on the board.
		setupGrid();

		whiteUpgrades = new UpgradePanel(this, Side.WHITE);
		blackUpgrades = new UpgradePanel(this, Side.BLACK);
	}

	/**
	 * Sets up the 8x8 chessboard.
	 */
	private void setupGrid() {
		GridBagConstraints gbc = new GridBagConstraints();

		for (Tile tile : tiles) {
			// Sets the X and Y of the grid.
			gbc.gridx = tile.getColumn();
			gbc.gridy = Math.abs(tile.getRow() - 8);

			// Add the Tile to the board.
			add(tile, gbc);
		}
	}

	public void display() {
		if (display == null)
			throw new IllegalStateException("Display not setup.");

		SwingUtilities.invokeLater(() -> setVisible(true));
	}

	/**
	 * Gets a list of possible, valid moves.
	 * @return list of possible moves.
	 */
	public List<Tile> getMoves(Piece piece, Tile posToCheck, Tile...whitelist) {
		List<Tile> possibleMoves = new ArrayList<>();
		List<Tile> moves = getAllMoves(piece, posToCheck);

		for (Tile move : moves) {
			// If the move is blocked, it's invalid
			if (isBlocked(piece, posToCheck, move, moves, false, whitelist))
				continue;

			// If the move has a Piece on it, make sure it's NOT an ally (unless it's an attempted King move).
			if (move.getPiece() == null || move.getPiece().getSide() != piece.getSide() || move == King.getAttemptedMove())
				possibleMoves.add(move);
		}

		return possibleMoves;
	}

	/**
	 * Gets a list of moves a Piece can make
	 *   without accounting for it being blocked.
	 * @return list of moves.
	 */
	public List<Tile> getAllMoves(Piece piece, Tile posToCheck) {
		List<Tile> validMoves = new ArrayList<>();

		for (Tile move : getTiles()) {
			// If the move is valid, you can move there.
			if (isValidMove(piece, posToCheck, move))
				validMoves.add(move);
		}

		return validMoves;
	}

	public boolean isValidMove(Piece piece, Tile posToCheck, Tile move) {
		// Row and column differences
		int rowDiff = Math.abs(move.getRow() - posToCheck.getRow());
		int columnDiff = Math.abs(move.getColumn() - posToCheck.getColumn());

		return piece.isValidMove(move, rowDiff, columnDiff);
	}

	public boolean isLegalMove(Piece piece, Tile move) {
		if (!movesLeft.isEmpty())
			return getMovesLeft(piece).contains(move);

		// Iterates over each tile, only caring about those with pieces.
		for (Tile tile : tiles) {
			if (tile.getPiece() == null)
				continue;

			// This Piece wouldn't check your King since you are attempting to kill it.
			if (move.getPiece() != null && move.getPiece() == tile.getPiece())
				continue;

			// Iterates over each possible move, only caring about those who effect a King.
			for (Tile t : tile.getPiece().getMoves(piece.getTile())) {
				if (t.getPiece() == null || !(t.getPiece() instanceof King))
					continue;

				// Check has to come from the other side.
				// You CANNOT check your own King!
				if (piece.getSide() != t.getPiece().getSide() || tile.getPiece().getSide() == t.getPiece().getSide())
					continue;

				// Attacker can't be blocked by a Tile it can't even move to...
				if (!tile.getPiece().isValidMove(move) || !tile.getPiece().isBlocked(t, List.of(move), true))
					return false;
			}
		}

		return true;
	}

	public boolean isBlocked(Piece piece, Tile posToCheck, Tile move) {
		return isBlocked(piece, posToCheck, move, getAllMoves(piece, posToCheck), false);
	}

	public boolean isBlocked(Piece piece, Tile posToCheck, Tile move, List<Tile> moves, boolean isAttemptingMove, Tile...whitelist) {
		// Row and column differences.
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
			if (piece.isBlocked(isRowBlocked, isColumnBlocked, hasMovedRow, hasMovedColumn))
				return true;
		}

		return false;
	}

	public void checkForCheck(Piece piece) {
		// Iterates over each possible move, only caring about those who effect a King.
		for (Tile tile : piece.getMoves()) {
			if (tile.getPiece() == null || !(tile.getPiece() instanceof King))
				continue;

			// If move results in check, set the check.
			if (piece.getSide() != tile.getPiece().getSide()) {
				setCheck((King) tile.getPiece(), piece);
				return;
			}
		}
	}

	public void setCheck(King checkedKing, Piece assassin) {
		this.checkedKing = checkedKing;
		this.assassin = assassin;
		movesLeft.clear();

		for (Tile tile : getTiles()) {
			if (tile.getPiece() == null || tile.getPiece().getSide() == assassin.getSide())
				continue;

			for (Tile t : tile.getPiece().getMoves()) {
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

		if (movesLeft.isEmpty()) {
			Side winningSide = assassin.getSide();
			JOptionPane.showConfirmDialog(new JPanel(),
					null,
					winningSide.name() + " WINS!!!",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					new ImageIcon(ResourceUtility.getImage(winningSide + "/Winner.png", 256, 256)));

			setVisible(false);
			display.displayMainMenu();
		}

		display.getMusic().setStatus(Music.Status.CLIMAX);
	}

	public void resetCheck() {
		checkedKing = null;
		assassin = null;
		movesLeft.clear();
	}

	/**
	 * Gets the position given the row and column.
	 * @param row row of the board
	 * @param column column of the board
	 * @return the Tile object at the given row/column, or (1, A) if not found.
	 */
	public Tile getTile(int row, char column) {
		for (Tile tile : tiles) {
			if (tile.getRow() == row && tile.getColumn() == column)
				return tile;
		}

		// If the tile isn't found, returns the first tile (bottom left).
		return new Tile(this, 1, 'a');
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public int getPiecesLeft() {
		int piecesLeft = 0;

		for (Tile tile : tiles) {
			if (tile.getPiece() != null)
				piecesLeft++;
		}

		return piecesLeft;
	}

	public UpgradePanel getUpgrades(Side side) {
		if (side == Side.WHITE)
			return whiteUpgrades;

		return blackUpgrades;
	}

	public Side getCurrentTurn() {
		return currentTurn;
	}

	public void nextTurn() {
		currentTurn = currentTurn == Side.WHITE ? Side.BLACK : Side.WHITE;
	}

	public Piece getCheckedKing() {
		return checkedKing;
	}

	public Piece getAssassin() {
		return assassin;
	}

	public List<Tile> getMovesLeft(Piece piece) {
		return movesLeft.getOrDefault(piece, new ArrayList<>());
	}

	private void addMoveLeft(Piece piece, Tile tile) {
		List<Tile> moves = movesLeft.getOrDefault(piece, new ArrayList<>());
		moves.add(tile);
		movesLeft.put(piece, moves);
	}

	@Override
	public Dimension getSize() {
		return size;
	}

	public StretchIcon getImage(String image) {
		if (display == null || display.getImageLoader() == null)
			return null;

		return display.getImageLoader().getImages().get(image);
	}
}
