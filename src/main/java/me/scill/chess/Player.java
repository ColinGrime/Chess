package me.scill.chess;

import me.scill.chess.enums.Side;

public class Player {

	// add data into it later
	// as well as a leaderboard...

	private final String name;
	private final Side side;

	public Player(String name, Side side) {
		this.name = name;
		this.side = side;
	}
}
