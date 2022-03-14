package me.colin.chess;

import me.colin.chess.display.Display;

/*
 * Created by: Colin Grimes
 *
 * Play some chess! :)
 */
public class Chess {

	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		new Display("Chess", new ImageLoader(), new MusicController());
		System.out.println("Game started. Time elapsed: " + (System.currentTimeMillis() - time) + "ms");
	}
}
