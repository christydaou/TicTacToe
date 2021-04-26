package ProjectII;

import java.util.Scanner;

public class TicTacToeMain {
	public static void main(String[] args) throws Exception {

		TicTacToe game = new TicTacToe();
		Scanner scan = new Scanner(System.in);

		String userMove, userMoveOriginal = null;
		int count = 0;

		System.out.print("Enter your move>");
		userMove = scan.nextLine();
		userMove = userMove.toLowerCase();

		if (!userMove.equals("new") && !userMove.equals("dump") && !userMove.equals("quit")) {
			if (game.conditionCheck(userMove)) {
				System.out.print("Enter your move>");
				userMove = scan.nextLine();
				userMove = userMove.toLowerCase();
			}
		}

		while (!game.boardIsFull()) {

			if (!userMove.equals("new") && !userMove.equals("dump") && !userMove.equals("quit")) {
				if (game.conditionCheck(userMove)) {
					System.out.print("Enter your move>");
					userMove = scan.nextLine();
					userMove = userMove.toLowerCase();
				}
			}

			// case if it is quit
			if (userMove.equals("quit")) {
				System.out.print("Bye!");
				break;
			}

			// case if it is new
			if (userMove.equals("new")) {
				game.checkInput("new");
				count = 0;
				System.out.print("Enter your move>");
				userMove = scan.nextLine();
				userMove = userMove.toLowerCase();
			}

			// case if it is an input

			if (!game.conditionCheck(userMove))
				if ((userMove.length() == 4) && !game.NewQuitCheck(userMove)) {
					if (game.Tie()) {
						System.out.println("Game Over, its a Tie");
					}

					if ((count == 0) && !userMove.equals("dump")) {
						userMoveOriginal = userMove;
						count++;
					}
					game.checkInput(userMove);

					if (userMove.charAt(3) == 'x') {
						if (game.isAWin(0)) {
							System.out.println("AI Wins" + count);
							break;
						}

						if (game.isAWin(1)) {
							System.out.println("Human Wins");
							break;
						} 
						
						if (game.Tie()) {
							System.out.println("Game Over, its a Tie");
						}

					}

					if (userMove.charAt(3) == 'o') {
						if (game.isAWin(0)) {
							System.out.println("Human Wins");
							break;
						}

						if (game.isAWin(1)) {
							System.out.println("AI Wins");
							break;
						}
						
						if (game.Tie()) {
							System.out.println("Game Over, its a Tie");
						}

					}

					

					if (!userMove.equals("dump"))
						game.checkInput("dump");
					System.out.print("Enter your move>");
					userMove = scan.nextLine();
					userMove = userMove.toLowerCase();
				}

			while (!userMove.equals("new") && (userMove.charAt(3) != userMoveOriginal.charAt(3))
					&& !(userMove.equals("dump") || userMove.equals("quit"))) {
				System.out.println(
						"You are not allowed to use this piece. \nYou have to use " + userMoveOriginal.charAt(3));
				game.conditionCheck(userMove);
				System.out.print("Enter your move>");
				userMove = scan.nextLine();
				userMove = userMove.toLowerCase();

			}

		}

		scan.close();
	}
}