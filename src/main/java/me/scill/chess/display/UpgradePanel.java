package me.scill.chess.display;

import me.scill.chess.Piece;
import me.scill.chess.enums.Side;
import me.scill.chess.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UpgradePanel {

	private final Piece[] pieces;
	private final JButton[] options;

	private Piece piece = null;

	public UpgradePanel(Board board, Side side) {
		// Rook, Bishop, Knight, Queen
		this.pieces = new Piece[]{
				new Rook(board, side),
				new Bishop(board, side),
				new Knight(board, side),
				new Queen(board, side)
		};

		// Make the pieces above into buttons.
		this.options = new JButton[4];
		for (int i = 0; i < options.length; i++)
			options[i] = makeButton(pieces[i]);
	}

	private JButton makeButton(Piece piece) {
		JButton button = new JButton(piece.getIcon(100));
		button.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {
				button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});

		button.addActionListener(e -> {
			JOptionPane.getRootFrame().dispose();
			this.piece = piece;
		});

		return button;
	}

	public void displayUpgrades(Pawn pawn) {
		// Prompt the user for which piece they want...
		JOptionPane.showOptionDialog(null,
				"",
				"Pick an Upgrade...",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[3]);

		// If the selection was successful, change the Pawn.
		if (piece != null) {
			pawn.getTile().setPiece(piece);
			return;
		}

		// If they force quit, randomly change the Pawn.
		int randomIndex = (int) (Math.random() * pieces.length);
		pawn.getTile().setPiece(pieces[randomIndex]);
	}
}
