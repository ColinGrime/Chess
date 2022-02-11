package me.scill.chess.display;

import me.scill.chess.enums.Fonts;
import me.scill.chess.utilities.SwingUtility;

import javax.swing.*;
import java.awt.*;

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
		JLabel title = createText("Chess", Color.decode("#26495C"), Fonts.SourceSansPro, 200);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton singleplayer = createButton("Singleplayer", Color.decode("#C4A35A"), Fonts.Raleway, 75);
		singleplayer.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton multiplayer = createButton("Multiplayer", Color.decode("#C4A35A"), Fonts.Raleway, 75);
		multiplayer.setAlignmentX(Component.CENTER_ALIGNMENT);
		multiplayer.addActionListener(e -> display.displayChessboard());

		add(Box.createRigidArea(new Dimension(0, 60)));
		add(title);
		add(Box.createRigidArea(new Dimension(0, 40)));
		add(singleplayer);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(multiplayer);
	}

	private JLabel createText(String text, Color color, Fonts fontType, float size) {
		JLabel title = new JLabel(text);
		title.setForeground(color);
		title.setFont(SwingUtility.createFont(fontType, size));
		return title;
	}

	private JButton createButton(String text, Color color, Fonts fontType, float size) {
		JButton button = new JButton(text);
		button.setForeground(color);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setOpaque(false);
		button.setMargin(new Insets(10, 10, 10, 10));
		button.setFont(SwingUtility.createFont(fontType, size));
		return button;
	}
}
