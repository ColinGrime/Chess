package me.scill.chess.display;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainMenu extends Display {

	public MainMenu(String title) {
		super(title);
	}

	@Override
	protected void init() {
		// Creates a JPanel with the BorderLayout.
		JPanel panel = new JPanel(new BorderLayout(100, 100));
		panel.setBackground(Color.BLACK);

		// Adds JLabel text to the screen.
		JLabel title = createText("Chess", Color.WHITE, "SourceSansPro", 200);
		title.setHorizontalAlignment(JLabel.CENTER);

		panel.add(title, BorderLayout.NORTH);
		panel.add(createButton("Click to Start!", Color.BLUE, "Raleway", 75));

		// Adds the JPanel to the current JFrame instance.
		add(panel);
	}

	private JLabel createText(String text, Color color, String fontName, float size) {
		JLabel title = new JLabel(text);
		title.setForeground(color);

		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/" + fontName + ".ttf"));
			title.setFont(font.deriveFont(size));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		return title;
	}

	private JButton createButton(String text, Color color, String fontName, float size) {
		JButton title = new JButton(text);
		title.setForeground(color);
		title.setOpaque(true);
		title.setPreferredSize(new Dimension(40, 40));

		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/" + fontName + ".ttf"));
			title.setFont(font.deriveFont(size));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		return title;
	}
}
