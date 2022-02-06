package me.scill.chess.display;

import me.scill.chess.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class SquareTile extends JButton implements ActionListener {

	private static SquareTile currentlySelected = null;

	private final int rowPos;
	private final char columnPos;
	private Piece piece;

	public SquareTile(int rowPos, char columnPos) {
		this(rowPos, columnPos, Color.WHITE);
	}

	public SquareTile(int rowPos, char columnPos, Color color) {
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

		if (currentlySelected != null && currentlySelected.getPiece().isValidMove(this)) {
			setPiece(currentlySelected.getPiece());
			currentlySelected.setPiece(null);
			currentlySelected = null;
		}

		else if (piece != null)
			currentlySelected = this;
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

		this.piece.setPosition(this);
		setSize(50, 50);

		BufferedImage img = null;
		try {
			img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/" + piece.getClass().getSimpleName() + ".png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Image image = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
		SwingUtilities.invokeLater(() -> setIcon(new ImageIcon(image)));
		revalidate();


			// todo FIGURE OUT HOW THIS WORKS
//			try {
//				Image img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/Rook.png")));
//				setIcon(new ImageIcon(img));
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			//setIcon(new ImageIcon("images/" + piece.getClass().getSimpleName() + ".png"));
//			System.out.println("images/" + piece.getClass().getSimpleName() + ".png");
	}

	@Override
	public String toString() {
		return "Position (" + rowPos + ", " + columnPos + ")";
	}
}
