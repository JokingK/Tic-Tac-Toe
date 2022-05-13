import java.util.*;

/**
 * TicTacToe Game
 * 
 * 
 */
public class aMain {

	/**
	 * Main Method - Executes the Game. Player X goes first
	 * 
	 */
	public static void main(String[] args) {
		int turns = 1;
		String another = "yes";
		// initialize board, first player and scanner
		// loop through the game: player chooses position to play, board prints,
		// check for win with break out condition, then change player if game
		// continues
		// Prints congratulations message to winner or tie message if no win
		// exists
		Scanner input = new Scanner(System.in);
		System.out.println("Do you want to play against another human or against the computer?");
		String comp = input.next();
		while (comp.equals("human")) {
			String[][] a = { { "0", "1", "2" }, { "3", "4", "5" }, { "6", "7", "8" } };
			System.out.println("Turn : 1");
			printBoard(a);
			String currPlayer = "x";
			while (!another.equals("no")) {
				turns++;
				getMove(currPlayer, a, input);
				System.out.println("Turn : " + turns);
				printBoard(a);
				if (checkWin(a)) {
					System.out.println(currPlayer + " wins! :D");
					turns = 1;
					break;
				} else if (turns == 10) {
					System.out.println("No one win. It's a tie! :D");
					break;
				}
				currPlayer = changePlayer(currPlayer);
			}
			System.out.println("Do you want to play another game? If so, against human or against the computer?");
			comp = input.next();
		}
		while (comp.equals("computer")) {
			String[][] a = { { "0", "1", "2" }, { "3", "4", "5" }, { "6", "7", "8" } };
			System.out.println("Do you want to be x or o");
			String currPlayer = input.next();
			printBoard(a);
		}
	}

	/**
	 * Prints TicTacToe board in 3 by 3 grid
	 * 
	 * @param board
	 */
	public static void printBoard(String[][] board) {

		// Print board in 3 by 3 grid. First 3 positions go into the first row, etc.
		int line = 0;
		int dash = 0;
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				System.out.print(board[row][col]);
				if (line < 2) {
					System.out.print("|");
					line++;
				}
			}
			line = 0;
			System.out.println("");
			if (dash == row && dash < 2) {
				for (int i = 0; i < board.length; i++)
					System.out.print("- ");
			}
			dash++;
			System.out.println("");
		}
	}

	/**
	 * Changes player for new turn
	 * 
	 * @param currPlayer
	 *            Current player, X or O
	 * @return Changed player, X or O
	 */
	public static String changePlayer(String currPlayer) {

		// Check current player: if x, then switch to o. If o, then switch to x.
		// Return new player.
		if (currPlayer.equals("x")) {
			currPlayer = "o";
		} else if (currPlayer.equals("o")) {
			currPlayer = "x";
		}
		return currPlayer;
	}

	/**
	 * Current Player selects position for next move
	 * 
	 * @param currPlayer
	 *            Current player, X or O
	 * @param board
	 *            TicTacToe board represented by a 3x3 array
	 * @param s
	 *            Scanner for input
	 */
	public static void getMove(String currPlayer, String[][] board, Scanner s) {

		// Ask for next move.

		System.out.println("Please type in where you want to go from 0-8");
		int position = s.nextInt();
		board[position / 3][position % 3] = currPlayer;
	}

	/**
	 * Checks position of next move to determine if input is valid or position
	 * is already filled. Allows player to choose another position if position
	 * is invalid
	 * 
	 * @param position
	 *            Chosen position of current player
	 * @param board
	 *            TicTacToe board represented by a 3x3 array
	 * @param s
	 *            Scanner for input
	 * @return Chosen position of current player
	 */
	public static boolean checkMove(int position, String[][] board, Scanner s) {	
		boolean filled = false;
		// Determine if position input is an integer 0-8. Player is directed to
		// choose again if choice is invalid.
		if (board[position / 3][position % 3].equals(" ")) {
			filled = false;
		}
		// Determine if position is already filled. Player is directed to choose
		// again if choice is invalid.
		if (board[position / 3][position % 3].equals("x") || board[position / 3][position % 3].equals("o")) {
			filled = true;
		}
		return filled;
	}

	/**
	 * Checks rows, columns, and diagonals for all X's or all O's - win
	 * condition
	 * 
	 * @param board
	 *            TicTacToe board represented by a 3x3 array
	 * @return true if win exists, false if no win exists
	 */
	public static boolean checkWin(String[][] board) {
		boolean win = false;
		// Check rows for win condition - all X's or all O's.
		for (int col = 0; col < board.length; col++) {
			if (board[0][col].equals("x") && board[1][col].equals("x") && board[2][col].equals("x")) {
				win = true;
			}
			if (board[0][col].equals("o") && board[1][col].equals("o") && board[2][col].equals("o")) {
				win = true;
			}
		}
		// Check columns for win condition - all X's or all O's.
		for (int row = 0; row < board.length; row++) {
			if (board[row][0].equals("x") && board[row][1].equals("x") && board[row][2].equals("x")) {
				win = true;
			}
			if (board[row][0].equals("o") && board[row][1].equals("o") && board[row][2].equals("o")) {
				win = true;
			}
		}
		// Check diagonals for win condition - all X's or all O's.
		if (board[0][2].equals("x") && board[1][1].equals("x") && board[2][0].equals("x")) {
			win = true;
		}
		if (board[2][0].equals("x") && board[1][1].equals("x") && board[0][2].equals("x")) {
			win = true;
		}
		if (board[0][2].equals("o") && board[1][1].equals("o") && board[2][0].equals("o")) {
			win = true;
		}
		if (board[2][0].equals("o") && board[1][1].equals("o") && board[0][2].equals("o")) {
			win = true;
		}
		return win;
	}

	public static int computer() {
		int random = (int) (Math.random() * 9);
		return random;
	}

	public static void compMove(String currPlayer, String[][] board, String computer) {
		for (int row = 0; row < board.length; row++) {
			if (board[row][0].equals(currPlayer) && board[row][1].equals(currPlayer) && board[row][2].equals(" ")) {
				board[row][2].equals(currPlayer);
			}
			if (board[row][0].equals(currPlayer) && board[row][1].equals(" ") && board[row][2].equals(currPlayer)) {
				board[row][1].equals(currPlayer);
			}
			if (board[row][0].equals(" ") && board[row][1].equals(currPlayer) && board[row][2].equals(currPlayer)) {
				board[row][0].equals(currPlayer);
			}
		}
		for (int col = 0; col < board[0].length; col++) {
			if (board[0][col].equals(currPlayer) && board[1][col].equals(currPlayer) && board[2][col].equals(" ")) {
				board[2][col].equals(currPlayer);
			}
			if (board[0][col].equals(currPlayer) && board[1][col].equals(" ") && board[2][col].equals(currPlayer)) {
				board[1][col].equals(currPlayer);
			}
			if (board[0][col].equals(" ") && board[1][col].equals(currPlayer) && board[2][col].equals(currPlayer)) {
				board[0][col].equals(currPlayer);
			}
		}
		for (int row = 0; row < board.length; row++) {
			if (board[row][0].equals(" ") || board[row][2].equals(" ") || board[0][2].equals(" ") || board[2][2].equals(" ")) {
				
			}
		}
	}
}