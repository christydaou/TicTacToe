package ProjectII;

import java.util.Random;

public class TicTacToe {
	public static final int HUMAN = 0;
	public static final int COMPUTER = 1;
	public static final int EMPTY = 2;
	public static final int HUMAN_WIN = 0;
	public static final int DRAW = 1;
	public static final int UNCLEAR = 2;
	public static final int COMPUTER_WIN = 3;

	private Best AIMove;

	private int row1;
	private int column1;
	
	private int[][] board = new int[3][3];
	

	public Best chooseMove(int side) {
		int opp; // The other side
		Best reply; // Opponent's best reply
		int simpleEval; // Result of an immediate evaluation
		int bestRow = 0;
		int bestColumn = 0;
		int value;
		if ((simpleEval = positionValue()) != UNCLEAR)
			return new Best(simpleEval);
		if (side == COMPUTER) {
			opp = HUMAN;
			value = HUMAN_WIN;
		} else {
			opp = COMPUTER;
			value = COMPUTER_WIN;
		}
		for (int row = 0; row < 3; row++)
			for (int column = 0; column < 3; column++)
				if (squareIsEmpty(row, column)) {
					place(row, column, side);
					reply = chooseMove(opp);
					place(row, column, EMPTY);
					// Update if side gets better position
					if (side == COMPUTER && reply.val > value || side == HUMAN && reply.val < value) {
						value = reply.val;
						bestRow = row;
						bestColumn = column;
					}
				}
		return new Best(value, bestRow, bestColumn);
	}

	// Compute static value of current position (win, draw, etc.)
	private int positionValue() {
		return isAWin(COMPUTER) ? COMPUTER_WIN : isAWin(HUMAN) ? HUMAN_WIN : boardIsFull() ? DRAW : UNCLEAR;
	}

// Constructor
	public TicTacToe() {

		clearBoard();
	}

	public int[][] getBoard() {
		return board;
	}

// Play move, including checking legality
	public boolean playMove(int side, int row, int column) {
		if (row < 0 || row >= 3 || column < 0 || column >= 3 || board[row][column] != EMPTY)
			return false;
		board[row][column] = side;
		return true;
	}

// Simple supporting routines
	public void clearBoard() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				board[i][j] = EMPTY;
	}

	public boolean boardIsFull() {
		for (int row = 0; row < 3; row++)
			for (int column = 0; column < 3; column++)
				if (board[row][column] == EMPTY)
					return false;
		return true;
	}

	public boolean isAWin(int side) {
		int row, column;
		/* Look for all in a row */
		for (row = 0; row < 3; row++) {
			for (column = 0; column < 3; column++)
				if (board[row][column] != side)
					break;
			if (column >= 3)
				return true;
		}
		/* Look for all in a column */
		for (column = 0; column < 3; column++) {
			for (row = 0; row < 3; row++)
				if (board[row][column] != side)
					break;
			if (row >= 3)
				return true;
		}
		/* Look on diagonals */
		if (board[1][1] == side && board[2][2] == side && board[0][0] == side)
			return true;
		if (board[0][2] == side && board[1][1] == side && board[2][0] == side)
			return true;
		return false;
	}

// Play a move, possibly clearing a square
	private void place(int row, int column, int piece) {
		board[row][column] = piece;
	}

	private boolean squareIsEmpty(int row, int column) {
		return board[row][column] == EMPTY;
	}

	// Evaluating user input based on new, a move, dump, or quit
	public void checkInput(String input) {

		if (input.equals("new")) {
			System.out.println("Game reset.");
			clearBoard();
		}

		else if ((input.length() == 4) && !input.equals("dump")) {
			
			Random gen = new Random();

			switch (input.charAt(0)) {
			case 'a':
				row1 = 0;
				break;
			case 'b':
				row1 = 1;
				break;
			case 'c':
				row1 = 2;
				break;

			}
			switch (input.charAt(1)) {
			case '1':
				column1 = 0;
				break;
			case '2':
				column1 = 1;
				break;
			case '3':
				column1 = 2;
				break;
			}

			boolean condition = true;
			
			// Random Generator Implementation
			/* switch (input.charAt(3)) {

			case 'x':
				if (squareIsEmpty(row1, column1)) {
					if (playMove(COMPUTER, row1, column1)) {
						place(row1, column1, COMPUTER);

						while (condition)
							if (playMove(HUMAN, row1 = gen.nextInt(3), column1 = gen.nextInt(3))) {
								place(row1, column1, HUMAN);
								condition = false;
							}
					}
				} else
					System.out.println("Invalid move, try again!");
				break;

			case 'o':
				if (squareIsEmpty(row1, column1)) {
					if (playMove(HUMAN, row1, column1)) {
						place(row1, column1, HUMAN);

						while (condition)
							if (playMove(COMPUTER, row1 = gen.nextInt(3), column1 = gen.nextInt(3))) {
								place(row1, column1, COMPUTER);
								condition = false;
							}
					}
				} else
					System.out.println("Invalid move, try again!");
				break;
			}*/
			
			// Unbeatable AI
			 switch (input.charAt(3)) {

			case 'x':
				if (squareIsEmpty(row1, column1)) {
					if (playMove(COMPUTER, row1, column1)) {
						place(row1, column1, COMPUTER);

						while (condition) {
							AIMove = chooseMove(HUMAN);
							if (playMove(HUMAN, AIMove.row, AIMove.column)) {
								condition = false;
							}
						}
					}
				} else
					System.out.println("Invalid move, try again!");
				break;

			case 'o':
				if (squareIsEmpty(row1, column1)) {
					if (playMove(HUMAN, row1, column1)) {
						place(row1, column1, HUMAN);

						while (condition) {
							AIMove = chooseMove(COMPUTER);
							if (playMove(COMPUTER, AIMove.row, AIMove.column)) {
								condition = false;
							}
						}
					}
				} else
					System.out.println("Invalid move, try again!");
				break;
			}

		}

		if (input.equals("dump")) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					switch (board[i][j]) {
					case 0:
						System.out.print("o");
						break;
					case 1:
						System.out.print("x");
						break;
					case 2:
						System.out.print("-");
						break;
					}
				}
				System.out.println();
			}
		}

	}

	// added method to evaluate if the userMove is valid or not
	public boolean conditionCheck(String input) {

		int i = 0;

		if (!input.equals("new") && !input.equals("dump") && !input.equals("quit")) {

			if ((input.charAt(0) != 'a') && (input.charAt(0) != 'b') && (input.charAt(0) != 'c')) {
				System.out.println("You should use a,b,c for rows 1,2,3 respectively.");
				i++;
			}
			if ((input.charAt(1) != '1') && (input.charAt(1) != '2') && (input.charAt(1) != '3')) {
				System.out.println("You should use 1,2,3 for columns 1,2,3 respectively.");
				i++;
			}

			if ((input.charAt(3) != 'x') && (input.charAt(3) != 'o')) {
				System.out.println("you should choose 'x' or 'o' ");
				i++;
			}
		}

		if (i != 0)
			return true;
		else
			return false;

	}

	// added method to check if userMove is New or Quit
	public boolean NewQuitCheck(String input) {
		if (input.equals("new") || input.equals("quit"))
			return true;
		else
			return false;
	}
	
	public boolean Tie() {
		if (boardIsFull())
			return true;
		else 
			return false;
	}
	

}
