package me.scill.chess.display;

import me.scill.chess.Piece;
import me.scill.chess.StretchIcon;
import me.scill.chess.board.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class SquareTile extends JButton implements ActionListener {


	private static SquareTile currentlySelected = null;

	private final Board board;
	private final int rowPos;
	private final char columnPos;
	private Piece piece;

	public SquareTile(Board board, int rowPos, char columnPos) {
		this(board, rowPos, columnPos, Color.WHITE);
	}

	public SquareTile(Board board, int rowPos, char columnPos, Color color) {
		this.board = board;
		this.rowPos = rowPos;
		this.columnPos = columnPos;

		setBackground(color);
		setOpaque(true);
		setBorderPainted(false);
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Piece piece = getPiece();

		if (currentlySelected == this)
			return;

		if (currentlySelected != null && currentlySelected.getPiece().isValidPlay(this)) {
			setPiece(currentlySelected.getPiece());
			currentlySelected.setPiece(null);
			currentlySelected = null;
		}

		else if (piece != null)
			currentlySelected = this;
	}

	public Board getBoard() {
		return board;
	}

	public int getRowPos() {
		return rowPos;
	}

	public char getColumnPos() {
		return columnPos;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;

		if (piece == null) {
			setIcon(null);
			return;
		}

		this.piece.setTile(this);
		setSize(50, 50);

		BufferedImage bufferedImage = null;
		try {
			// Retrieves the path of the image, and its InputStream.
			String imagePath = "/images/" + piece.getSide().name().toLowerCase() + "/" + piece.getClass().getSimpleName() + ".png";
			InputStream inputStream = getClass().getResourceAsStream(imagePath);

			// Returns if the InputStream is invalid.
			if (inputStream == null)
				return;

			// Reads in the InputStream and retrieves the BufferedImage.
			bufferedImage = ImageIO.read(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Returns if the BufferedImage failed to read.
		if (bufferedImage == null)
			return;

		// Scales the image, then sets the icon of the button to a StretchIcon.
		Image image = bufferedImage.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
		SwingUtilities.invokeLater(() -> setIcon(new StretchIcon(image)));
		SwingUtilities.invokeLater(() -> setPressedIcon(new StretchIcon(image)));
	}

	@Override
	public String toString() {
		return "Position (" + rowPos + ", " + columnPos + ")";
	}
}
