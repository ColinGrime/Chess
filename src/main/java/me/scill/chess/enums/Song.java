package me.scill.chess.enums;

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
