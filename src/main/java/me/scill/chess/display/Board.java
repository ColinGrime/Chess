package me.scill.chess.display;

import me.scill.chess.Player;
import me.scill.chess.enums.Side;
import me.scill.chess.pieces.*;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Board extends JPanel {

	private final Color MAIN = Color.decode("#cedef0"),
						SECONDARY = Color.decode("#6b9bd1");

	private Dimension size;
	private final List<Tile> tiles = new ArrayList<>();

	private final Player player1 = new Player("White", Side.WHITE);
	private final Player player2 = new Player("Black", Side.BLACK);

	private final UpgradePanel whiteUpgrades = new UpgradePanel(Side.WHITE);
	private final UpgradePanel blackUpgrades = new UpgradePanel(Side.BLACK);

	private Side currentTurn = Side.WHITE;

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

	public Board display(Dimension size) {
		this.size = new Dimension((int) size.getHeight() / 8, (int) size.getHeight() / 8);

		setBackground(new Color(40, 44, 52));
		setLayout(new GridBagLayout());

		// Sets up the grid & puts the pieces on the board.
		setupGrid();
		createSide(Side.WHITE);
		createSide(Side.BLACK);

		return this;
	}

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

	@Override
	public Dimension getSize() {
		return size;
	}
}
