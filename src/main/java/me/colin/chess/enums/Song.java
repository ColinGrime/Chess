package me.colin.chess.enums;

/**
 * Holds the various songs used in the
 * program, along with their paths.
 */
public enum Song {

	DevilPiano("DevilPiano.wav"),
	BeforeTheFight("BeforeTheFight.wav"),
	Lunokhod("Lunokhod.wav"),

	ImpatientlyWaiting("ImpatientlyWaiting.wav"),
	OrNot("OrNot.wav"),

	ThePercussionist("ThePercussionist.wav"),
	ThinWallsPercussion("ThinWallsPercussion.wav");

	private final String path;

	Song(String path) {
		this.path = path;
	}

	public String path() {
		return "/music/" + path;
	}
}
