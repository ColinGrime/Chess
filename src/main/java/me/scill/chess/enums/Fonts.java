package me.scill.chess.enums;

public enum Fonts {

	Raleway("Raleway.ttf"),
	SourceSansPro("SourceSansPro.ttf");

	private final String path;

	Fonts(String path) {
		this.path = path;
	}

	public String path() {
		return "/fonts/" + path;
	}
}
