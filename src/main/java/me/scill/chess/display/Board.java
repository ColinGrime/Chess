package me.scill.chess.display;

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

	private final Display display;
	private final Dimension size;
	private final List<Tile> tiles = new ArrayList<>();

	private final UpgradePanel whiteUpgrades = new UpgradePanel(Side.WHITE);
	private final UpgradePanel blackUpgrades = new UpgradePanel(Side.BLACK);

	private Side currentTurn = Side.WHITE;
	private Piece checkedKing = null, assassin = null;
	private final Map<Piece, List<Tile>> movesLeft = new HashMap<>();

	public Board(Display display, Dimension size) {
		this.display = display;
		this.size = new Dimension((int) size.getHeight() / 8, (int) size.getHeight() / 8);

		setBackground(new Color(40, 44, 52));
		setLayout(new GridBagLayout());

		// Sets up the grid & puts the pieces on the board.
		setupGrid();
		createSide(Side.WHITE);
		createSide(Side.BLACK);

		setVisible(true);
	}

	//		try {
//			// Open an audio input stream.
//			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("./music/KissTheSky.wav");
//
//			if (inputStream == null) {
//				System.out.println("[Error] Music file location is invalid.");
//				return;
//			}
//
//			AudioInputStream audioIn = AudioSystem.getAudioInputStream(inputStream);
//			// Get a sound clip resource.
//			Clip clip = AudioSystem.getClip();
//			// Open audio clip and load samples from the audio input stream.
//			clip.open(audioIn);
//			clip.start();
//		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
//			e.printStackTrace();
//		}

	private void setupGrid() {
		GridBagConstraints gbc = new GridBagConstraints();
		int index = 0;

		for (int row=0; row<8; row++) {
			for (char column='a'; column<'a'+8; column++) {
				// Creates the Color for the Tile.
				Color color = index++ % 2 == 0 ? MAIN : SECONDARY;
				Tile position = new Tile(this, 8 - row, column, color);

				// Sets the X and Y of the grid.
				gbc.gridx = column;
				gbc.gridy = row;

				// Add the position to the board, and the panel to the display.
				getTiles().add(position);
				add(position, gbc);
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
		getTile(firstRow, 'a').setPiece(new Rook(side));
		getTile(firstRow, 'b').setPiece(new Knight(side));
		getTile(firstRow, 'c').setPiece(new Bishop(side));
		getTile(firstRow, 'd').setPiece(new Queen(side));
		getTile(firstRow, 'e').setPiece(new King(side));
		getTile(firstRow, 'f').setPiece(new Bishop(side));
		getTile(firstRow, 'g').setPiece(new Knight(side));
		getTile(firstRow, 'h').setPiece(new Rook(side));

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
			getTile(row, column).setPiece(new Pawn(side));
	}

	/**
	 * Gets the position given the row and column.
	 * @param row row of the board
	 * @param column column of the board
	 * @return the Tile object at the given row/column, or (1, A) if not found.
	 */
	public Tile getTile(int row, char column) {
		for (Tile pos : tiles) {
			if (pos.getRow() == row && pos.getColumn() == column)
				return pos;
		}

		// If the tile isn't found, returns the first tile (bottom left).
		return new Tile(this, 1, 'a');
	}

	public List<Tile> getTiles() {
		return tiles;
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

	public void setCheck(King checkedKing, Piece assassin) {
		this.checkedKing = checkedKing;
		this.assassin = assassin;
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
	}

	public void resetCheck() {
		checkedKing = null;
		assassin = null;
		movesLeft.clear();
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

	public boolean isMoveIllegal(Piece piece, Tile tile) {
		return !movesLeft.isEmpty() && !getMovesLeft(piece).contains(tile);
	}

	@Override
	public Dimension getSize() {
		return size;
	}
}
