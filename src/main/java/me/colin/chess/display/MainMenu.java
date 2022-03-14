package me.colin.chess.display;

import me.colin.chess.enums.Font;
import me.colin.chess.enums.Theme;
import me.colin.chess.utils.ResourceUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
 * TODO make this class less ugly, I was lazy...
 */
public class MainMenu extends JPanel {

	private final Theme theme = Theme.Dark;
	private final Display display;

	public MainMenu(Display display) {
		this.display = display;
		setup();
	}

	private void setup() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(theme.getBackground());

		// Adds a title.
		JLabel title = createText("Chess", theme.getPrimary(), Font.SourceSansPro, 200);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Adds a reminder that there's music!
		JLabel additionalText = createText("By the way, there's music...", theme.getPrimary(), Font.Raleway, 30);
		additionalText.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Adds a play button to play the game.
		JButton play = createButton("Play", theme.getSecondary(), Font.Raleway, 75);
		play.setAlignmentX(Component.CENTER_ALIGNMENT);
		play.addActionListener(e -> display.displayChessboard());

		// Adds an instructions button to display the instructions.
		JButton instructions = createButton("Instructions", theme.getSecondary(), Font.Raleway, 75);
		instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
		instructions.addActionListener(e -> display.displayInstructions());

		add(Box.createRigidArea(new Dimension(0, 60)));
		add(title);
		add(Box.createRigidArea(new Dimension(0, 40)));
		add(play);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(instructions);
		add(Box.createRigidArea(new Dimension(0, 80)));
		add(additionalText);
	}

	/*
	 * Creates a label for the menu.
	 */
	private JLabel createText(String text, Color color, Font fontType, float size) {
		JLabel title = new JLabel(text);
		title.setForeground(color);
		title.setFont(ResourceUtility.createFont(fontType, size));
		return title;
	}

	/*
	 * Creates a button for the menu.
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
