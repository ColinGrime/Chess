package me.colin.chess;

import me.colin.chess.display.Display;

/**
 * Created by: Colin Grimes
 * Play some chess! :)
 *
 * --IMPORTANT NOTE TO MAKE--
 * To assist in developing, I used the {@link me.colin.chess.display.StretchIcon} class.
 * It allows you to dynamically re-size images while keeping the quality up.
 *
 * It's about 20-30 lines of actual code.
 * So instead of having choppy images (and so I don't have to do math), I decided to use it.
 *
 * I can easily remove it if needed, but I point it out since it's copied code.
 * Thanks~
 */
public class Chess {

	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		new Display("Chess", new ImageLoader(), new MusicController());
		System.out.println("Game started. Time elapsed: " + (System.currentTimeMillis() - time) + "ms");
	}
}
