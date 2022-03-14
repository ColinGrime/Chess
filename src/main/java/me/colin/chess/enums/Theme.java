package me.colin.chess.enums;

import java.awt.*;

/**
 * Allows easy creation of themes for the game.
 */
public enum Theme {

	Default("#E5E5DC", "#26495C", "#C4A35A"),
	Dark("#001A38", "#CF6679", "#2069E0");

	private final Color background, primary, secondary;

	Theme(String background, String primary, String secondary) {
		this.background = Color.decode(background);
		this.primary = Color.decode(primary);
		this.secondary = Color.decode(secondary);
	}

	public Color getBackground() {
		return background;
	}

	public Color getPrimary() {
		return primary;
	}

	public Color getSecondary() {
		return secondary;
	}
}
