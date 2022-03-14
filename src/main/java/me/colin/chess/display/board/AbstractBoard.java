package me.colin.chess.display.board;

import me.colin.chess.MusicController;
import me.colin.chess.display.Display;
import me.colin.chess.display.StretchIcon;
import me.colin.chess.display.tile.AbstractTile;
import me.colin.chess.display.UpgradePanel;
import me.colin.chess.display.tile.Tile;
import me.colin.chess.enums.Side;
import me.colin.chess.piece.Piece;
import me.colin.chess.piece.pieces.*;
import me.colin.chess.utils.ResourceUtility;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbstractBoard extends JPanel implements Board {

	private final Color MAIN = Color.decode("#cedef0"),
						SECONDARY = Color.decode("#6b9bd1");

	private final List<Tile> tiles = new ArrayList<>();
	private final Map<Piece, List<Tile>> movesLeft = new HashMap<>();

	private Display display;
	private Dimension size;
	private UpgradePanel whiteUpgrades, blackUpgrades;

	private Side currentTurn = Side.WHITE;
	private Piece checkedKing = null;

	public AbstractBoard() {
		init();
	}

	@Override
	public void init() {
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
				tiles.add(new AbstractTile(this, 8 - row, column, color));
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
		for (char column='a'; column<='h'; column++) {
			getTile(row, column).setPiece(new Pawn(this, side));
		}
	}

	@Override
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
			add((AbstractTile) tile, gbc);
		}
	}

	@Override
	public void display() {
		if (display == null) {
			throw new IllegalStateException("Display not setup.");
		}

		SwingUtilities.invokeLater(() -> setVisible(true));
	}

	@Override
	public UpgradePanel getUpgrades(Side side) {
		if (side == Side.WHITE)
			return whiteUpgrades;

		return blackUpgrades;
	}

	@Override
	public Side getCurrentTurn() {
		return currentTurn;
	}

	@Override
	public void nextTurn() {
		currentTurn = currentTurn == Side.WHITE ? Side.BLACK : Side.WHITE;
	}

	@Override
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

	@Override
	public void setCheck(King checkedKing, Piece assassin) {
		this.checkedKing = checkedKing;
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

		display.getMusic().setStatus(MusicController.Status.CLIMAX);
	}

	private void addMoveLeft(Piece piece, Tile tile) {
		List<Tile> moves = movesLeft.getOrDefault(piece, new ArrayList<>());
		moves.add(tile);
		movesLeft.put(piece, moves);
	}

	@Override
	public void resetCheck() {
		checkedKing = null;
		movesLeft.clear();
	}

	@Override
	public Piece getCheckedKing() {
		return checkedKing;
	}

	@Override
	public Map<Piece, List<Tile>> getMovesLeft() {
		return movesLeft;
	}

	@Override
	public List<Tile> getMovesLeft(Piece piece) {
		return movesLeft.getOrDefault(piece, new ArrayList<>());
	}

	@Override
	public List<Tile> getTiles() {
		return tiles;
	}

	@Override
	public Tile getTile(int row, char column) {
		for (Tile tile : tiles) {
			if (tile.getRow() == row && tile.getColumn() == column)
				return tile;
		}

		// If the tile isn't found, returns the first tile (bottom left).
		return new AbstractTile(this, 1, 'a');
	}

	@Override
	public StretchIcon getImage(String path) {
		if (display == null || display.getImageLoader() == null)
			return null;

		return display.getImageLoader().getImages().get(path);
	}

	@Override
	public Dimension getSize() {
		return size;
	}
}
