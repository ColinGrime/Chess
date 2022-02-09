package me.scill.chess;

import me.scill.chess.board.Board;
import me.scill.chess.display.Chessboard;

public class Chess {

	/*
	 * Pawns can move 1/2 spaces in the beginning, 1 otherwise.
	 * Each turn, switch board around to let the other player go.
	 * Player can click and move, and has option to see all possible moves.
	 */
	public static void main(String[] args) {
//		MainMenu menu = new MainMenu("Chess");
		new Chessboard(new Board());
	}
}
