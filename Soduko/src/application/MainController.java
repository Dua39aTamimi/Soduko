package application;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


import javafx.event.ActionEvent;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextField;


public class MainController {

	@FXML
	private GridPane grid;
	@FXML
	private Button newgame;
	@FXML
	private Button show;
	@FXML
	private Button close;
	@FXML
	private TextField[][] tf = new TextField[9][9];
	@FXML
	private Button check;

	@FXML
	private Button myGame;

	int min = 0;
	int max = 9;
	ArrayList<Game> game;
	private static int[][] board;
	private static int[][] play;
	private static int[][] solution;

	public void initialize() {
		game = new ArrayList<>();
		readFile("src\\application\\game.txt");

		int index = (int) (Math.random() * (max - min + 1) + min);
//		System.out.println(index);
		board = game.get(index).getBoard();
		solution = clone(board);
		play = clone(board);

	}

	@FXML
	void checkButton(ActionEvent event) {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				TextField tf = new TextField();
				tf.setAlignment(Pos.CENTER);
				tf.setMaxSize(35, 8);
				tf.setFont(Font.font(null, FontWeight.BOLD, 14));
				GridPane.setConstraints(tf, j, i);
				GridPane.setHalignment(tf, HPos.CENTER);
				grid.getChildren().add(tf);
				int r = i;
				int k = j;

				if (play[i][j] == 0) {
					tf.setText("");
				} else
					tf.setText("" + play[i][j]);
				
				tf.textProperty().addListener((obs, oldText, newText) -> {
					if (newText.matches(""))
						play[r][k] = 0;
					else
						play[r][k] = Integer.parseInt(newText);

					// ...
				});
				if (play[i][j] != solution[i][j]) {
					tf.setStyle("-fx-text-inner-color: RED ");
				}

			}
		}
	}

	@FXML
	void myGameButton(ActionEvent event) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				TextField tf = new TextField();
				tf.setAlignment(Pos.CENTER);
				tf.setMaxSize(35, 8);
				tf.setFont(Font.font(null, FontWeight.BOLD, 14));
				GridPane.setConstraints(tf, j, i);
				GridPane.setHalignment(tf, HPos.CENTER);
				grid.getChildren().add(tf);
				int r = i;
				int k = j;

				if (play[i][j] == 0) {
					tf.setText("");
				} else
					tf.setText("" + play[i][j]);

				tf.textProperty().addListener((obs, oldText, newText) -> {
					if (newText.matches(""))
						play[r][k] = 0;
					else
						play[r][k] = Integer.parseInt(newText);
					// ...
				});
			}
		}
	}

	@FXML
	void newgamebutton(ActionEvent event) {
		int index = (int) (Math.random() * (max - min + 1) + min);
		System.out.println(index);
		board = game.get(index).getBoard();
		solution = clone(board);
		play = clone(board);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				TextField tf = new TextField();
				tf.setAlignment(Pos.CENTER);
				tf.setMaxSize(35, 8);
				tf.setFont(Font.font(null, FontWeight.BOLD, 14));
				GridPane.setConstraints(tf, j, i);
				GridPane.setHalignment(tf, HPos.CENTER);
				grid.getChildren().add(tf);
				int r = i;
				int k = j;
				if (board[i][j] == 0) {
					tf.setText("");
				} else
					tf.setText("" + board[i][j]);

				tf.textProperty().addListener((obs, oldText, newText) -> {
					if (newText.matches(""))
						play[r][k] = 0;
					else
						play[r][k] = Integer.parseInt(newText);
					// ...
				});
				/*
				 * if (tf.getText().matches("")) { play[i][j] = 0;
				 * 
				 * } else { play[i][j] = Integer.parseInt(tf.getText());
				 * System.out.println(play[i][j] + ""); }
				 */

			}
		}

	}

	@FXML
	void showbutton(ActionEvent event) {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				TextField tf = new TextField();
				tf.setAlignment(Pos.CENTER);
				tf.setMaxSize(35, 8);
				tf.setFont(Font.font(null, FontWeight.BOLD, 14));
				GridPane.setConstraints(tf, j, i);
				GridPane.setHalignment(tf, HPos.CENTER);
				grid.getChildren().add(tf);

				if (solveSudoku(solution)) {
					tf.setText("" + solution[i][j]);
				} else {
					tf.setText("");
				}
			}
		}
//		int k=Integer.parseInt(tf.getText());  
//		while(solveSudoku(board) == false) {
//			tf.setStyle("-fx-text-inner-color: RED" );
//			}	
	}

	@FXML
	void closebutton(ActionEvent event) {

		Stage stage = (Stage) close.getScene().getWindow();
		stage.close();
	}

	public static boolean isSafe(int[][] board, int row, int col, int num) {
		// Row has the unique (row-clash)
		for (int d = 0; d < board.length; d++) {
			// Check if the number we are trying to place is already present in that row,
			// return false;
			if (board[row][d] == num) {
				return false;
			}
		}

		// Column has the unique numbers (column-clash)
		for (int r = 0; r < board.length; r++) {
			// Check if the number we are trying to place is already present in that column,
			// return false;
			if (board[r][col] == num) {
				return false;
			}
		}

		// Corresponding square has unique number (box-clash)
		int sqrt = (int) Math.sqrt(board.length);
		int boxRowStart = row - row % sqrt;
		int boxColStart = col - col % sqrt;

		for (int r = boxRowStart; r < boxRowStart + sqrt; r++) {
			for (int d = boxColStart; d < boxColStart + sqrt; d++) {
				if (board[r][d] == num) {
					return false;
				}
			}
		}
		// if there is no clash, it's safe
		return true;
	}

	public static boolean solveSudoku(int[][] solution) {
		int row = -1;
		int col = -1;
		boolean isEmpty = true;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (solution[i][j] == 0) {
					row = i;
					col = j;
					// We still have some remaining missing values in Sudoku
					isEmpty = false;
					break;
				}
			}
			if (!isEmpty) {
				break;
			}
		} // No empty space left
		if (isEmpty) {
			return true;
		}

		// Else for each-row backtrack
		for (int num = 1; num <= 9; num++) {

			if (isSafe(solution, row, col, num)) {
				solution[row][col] = num;
				if (solveSudoku(solution)) { // print(board, n);
					return true;
				} else { // replace it
					solution[row][col] = 0;
				}
			}
		}
		return false;
	}

	public static void print(int[][] board) {

		// We got the answer, just print it
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(board[i][j]);
				System.out.print(" ");
			}
			System.out.print("\n");
			if ((i + 1) % (int) Math.sqrt(9) == 0) {
				System.out.print("");
			}
		}
	}

	public void readFile(String path) {
		File file = new File(path);
		try {
			Scanner in = new Scanner(file);
			while (in.hasNext()) {

				int[][] board = new int[9][9];
				for (int i = 0; i < 9; i++) {
					String[] line = in.nextLine().split(" ");
					for (int j = 0; j < line.length; j++) {
						board[i][j] = Integer.parseInt(line[j]);
//						System.out.print(board[i][j]+" ");
					}
				}
//				System.out.println();
				Game g = new Game(board);
				game.add(g);
				in.nextLine();
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public int[][] clone(int[][] array) {
		int[][] newArray = new int[array.length][array[0].length];
		for (int i = 0; i < array.length; i++)
			for (int j = 0; j < array[i].length; j++)
				newArray[i][j] = array[i][j];
		return newArray;
	}
}
