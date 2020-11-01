import java.util.Scanner;
import java.util.Arrays;
import java.io.PrintStream;

public class connect4 {
	static boolean inBounds(int row, int column, int width, int height) {
		return (row >= 0 && column >= 0 && row < height && column < width);
	}

	static int locationWinner(int row, int column, int[][] board) {
		if (board[row][column] == 0) return 0;
		int compare = board[row][column];
		boolean[] shouldLose = new boolean[4];
		for (int i = 0; i < 4; i++) {
			int[][] checkPositions = new int[][] {
				{row, column + i},
				{row + i, column},
				{row + i, column + i},
				{row + i, column - i}
			};
			for (int j = 0; j < 4; j++) {
				if (!inBounds(checkPositions[j][0], checkPositions[j][1], board[row].length, board.length)) {
					shouldLose[j] = true;
					continue;
				}
				if (board[checkPositions[j][0]][checkPositions[j][1]] != compare) shouldLose[j] = true;
			}
		}
		boolean shouldWin = false;
		for (boolean i: shouldLose) {
			if (!i) shouldWin = true;
		}
		return shouldWin? compare:0;
	}

	static int winner(int[][] board) {
		boolean boardFilled = true;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 0) boardFilled = false;
				int locationWinner = locationWinner(i, j, board);
				if (locationWinner != 0) return locationWinner;
			}
		}
		return boardFilled? 4:0;
	}

	static void displayBoard(int[][] board) {
		System.out.println(" 1   2   3   4   5   6   7");
		for (int i = 0; i < board.length; i++) {
			System.out.print(" ");
			System.out.print(board[i][0]);
			for (int j = 1; j < board[i].length; j++) {
				System.out.print(" | ");
				System.out.print(board[i][j]);
			}
			System.out.println();
			System.out.println("---+---+---+---+---+---+---");
		}
	}

	static void processMove(boolean isPlayer1, int[][] board) {
		boolean hasPlaced = false;
		System.out.println("Enter in your move");
		Scanner scanner = new Scanner(System.in);
		while (!hasPlaced) {
			int move = scanner.nextInt() - 1;
			if (move >= board[0].length || move < 0) {
				System.out.println("That's not a real column.");
				continue;
			}
			for (int i = board.length - 1; i >= 0; i--) {
				if (board[i][move] == 0) {
					board[i][move] = isPlayer1? 1:2;
					hasPlaced = true;
					break;
				}
			}
			if (!hasPlaced) System.out.println("That column is already filled.");
		}
	}

	public static void main(String[] args) {
		boolean isPlayer1 = true;
		int[][] board = new int[6][7];

		int winnerState = winner(board);
		while (winnerState == 0) {
			System.out.println(winnerState);
			displayBoard(board);
			processMove(isPlayer1, board);
			winnerState = winner(board);
			isPlayer1 = !isPlayer1;
		}

		switch (winnerState) {
			case 1:case 2:
				displayBoard(board);
				System.out.printf("Player %d wins!\n", winnerState);
				break;
			case 3:
				System.out.println("It's a tie.");
				break;
		}
	}
}
