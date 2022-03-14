package me.colin.chess.display;

import me.colin.chess.enums.Font;
import me.colin.chess.enums.Theme;
import me.colin.chess.utils.ResourceUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Instructions extends JPanel {

	private final Theme theme = Theme.Dark;

	public Instructions(Display display) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(theme.getBackground());

		// Adds instructions label.
		JLabel instructions = new JLabel();
		instructions.setIcon(new ImageIcon(ResourceUtility.getImage("Instructions.jpg", 400, 600)));
		instructions.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Adds back button.
		JButton backButton = createButton("Back!", theme.getSecondary(), Font.Raleway, 50);
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.addActionListener(e -> {
			setVisible(false);
			display.displayMainMenu();
		});

		add(Box.createRigidArea(new Dimension(0, 10)));
		add(instructions);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(backButton);
	}

	/*
	 * Creates a button for the instructions' menu.
	 */
	private JButton createButton(String text, Color color, Font fontType, float size) {
		JButton button = new JButton(text);
		button.setForeground(color);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setOpaque(false);
		button.setMargin(new Insets(10, 10, 10, 10));
		button.setFont(ResourceUtility.createFont(fontType, size));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});

		return button;
	}
}
