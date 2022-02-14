package me.scill.chess.enums;

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
