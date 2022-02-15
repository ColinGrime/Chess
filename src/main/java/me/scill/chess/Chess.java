package me.scill.chess;

import me.scill.chess.display.Display;

public class Chess {

	/*
	 * account for check on pawn upgrade
	 * make it so it doesn't turn to hand once you do a move
	 * movement animation
	 * sound effect when taking a piece
	 * try again with asynchronous resource loading
	 * make it so you can take pawn in 2 places if it goes 2 up
	 * a-h 1-8
	 * draw system
	 * keep track of all moves done on the right side
	 */
	public static void main(String[] args) {
		long time = System.currentTimeMillis();

		ImageLoader imageLoader = new ImageLoader();
		Music music = new Music();
		new Display("Chess", imageLoader, music);

		System.out.println("Game started. Time elapsed: " + (System.currentTimeMillis() - time) + "ms");
	}
}
