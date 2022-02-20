package me.scill.chess;

import me.scill.chess.display.Display;

public class Chess {

	public static void main(String[] args) {
		long time = System.currentTimeMillis();

		ImageLoader imageLoader = new ImageLoader();
		Music music = new Music();
		new Display("Chess", imageLoader, music);

		System.out.println("Game started. Time elapsed: " + (System.currentTimeMillis() - time) + "ms");
	}
}
