package me.scill.chess.display;

import me.scill.chess.enums.Font;
import me.scill.chess.utilities.ResourceUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenu extends JPanel {

	private final Display display;

	public MainMenu(Display display) {
		this.display = display;
		setup();
	}

	protected void setup() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.decode("#E5E5DC"));

//		// Creates a JLabel holding the chess logo.
//		JLabel logo = new JLabel();
//		logo.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//		// Sets the image to the icon.
//		Image image = ImageUtility.getImage("Logo.png", 200, 200);
//		if (image != null)
//			logo.setIcon(new ImageIcon(image));

		// Adds JLabel text to the screen.
		JLabel title = createText("Chess", Color.decode("#26495C"), Font.SourceSansPro, 200);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton singleplayer = createButton("Singleplayer", Color.decode("#C4A35A"), Font.Raleway, 75);
		singleplayer.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton multiplayer = createButton("Multiplayer", Color.decode("#C4A35A"), Font.Raleway, 75);
		multiplayer.setAlignmentX(Component.CENTER_ALIGNMENT);
		multiplayer.addActionListener(e -> display.displayChessboard());

		add(Box.createRigidArea(new Dimension(0, 60)));
		add(title);
		add(Box.createRigidArea(new Dimension(0, 40)));
		add(singleplayer);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(multiplayer);
	}

	private JLabel createText(String text, Color color, Font fontType, float size) {
		JLabel title = new JLabel(text);
		title.setForeground(color);
		title.setFont(ResourceUtility.createFont(fontType, size));
		return title;
	}

	private JButton createButton(String text, Color color, Font fontType, float size) {
		JButton button = new JButton(text);
		button.setForeground(color);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setOpaque(false);
		button.setMargin(new Insets(10, 10, 10, 10));
		button.setFont(ResourceUtility.createFont(fontType, size));
		button.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

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
