package me.colin.chess.enums;

/**
 * Holds the fonts used in the program,
 * along with their paths.
 */
public enum Font {

	Raleway("Raleway.ttf"),
	SourceSansPro("SourceSansPro.ttf");

	private final String path;

	Font(String path) {
		this.path = path;
	}

	public String path() {
		return "/fonts/" + path;
	}
}
