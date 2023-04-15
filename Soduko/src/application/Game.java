package application;

import java.util.Arrays;

public class Game {
	int[][] board = new int[9][9];

	public Game(int[][] board) {
		super();
		this.board = board;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	@Override
	public String toString() {
		return "Game [board=" + Arrays.toString(board) + "]";
	}
	
	
}
