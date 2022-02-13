package me.scill.chess.display;

import me.scill.chess.board.Piece;
import me.scill.chess.pieces.*;
import me.scill.chess.utilities.SwingUtility;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class UpgradePanel extends JPanel {

	private Piece piece = null;

	public UpgradePanel(Pawn pawn) {
		// Rook, Bishop, Knight, Queen
		Piece[] pieces = new Piece[] {
				new Rook(pawn.getSide()),
				new Bishop(pawn.getSide()),
				new Knight(pawn.getSide()),
				new Queen(pawn.getSide())
		};

		// Make the pieces above into buttons.
		JButton[] options = new JButton[4];
		for (int i=0; i<options.length; i++)
			options[i] = makeButton(pieces[i]);

		// Prompt the user for which piece they want...
		JOptionPane.showOptionDialog(this,
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

	private JButton makeButton(Piece piece) {
		String path = piece.getSide() + "/" + piece.getClass().getSimpleName() + ".png";
		ImageIcon icon = new ImageIcon(SwingUtility.getImage(path, 100, 100));

		JButton button = new JButton(icon);
		button.addActionListener(e -> {
			JOptionPane.getRootFrame().dispose();
			this.piece = piece;
		});

		return button;
	}
}
