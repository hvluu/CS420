
import java.util.ArrayList;
import java.util.Scanner;

public class UI {

	protected Scanner sc = new Scanner(System.in);
	private final GameBoard game;
	private final AI ai;

	public UI() {
		game = new GameBoard();
		ai = new AI(game);
	}

	/**
	 * Starts the UI to prompt user for turn order, seconds for AI to make move,
	 * and moves.
	 * 
	 * @throws InterruptedException
	 */
	public void start() throws InterruptedException {
		int playerFirst = doesPlayerGoFirst(); // player is 0, ai is 1
		boolean error = true;
		int seconds = 0;

		while (error) {
			System.out.print("How long should the computer think about its moves(in seconds)?: ");
			if (sc.hasNextInt())
				seconds = sc.nextInt();
			else {
				System.out.println("Invalid input. Try again.");
				sc.next();
				continue;
			}
			if (seconds <= 0 || seconds > 30) {
				System.out.println("Seconds must be greater than 0 and equal to or less than 30.");
				continue;
			}
			ai.timeLimit = seconds * 1000;
			error = false;
		}

		System.out.println();
		game.printBoard();

		if (playerFirst == 0) {
			playerMove();
		} else {
			aiMove();
		}

		while (true) {
			if (playerFirst == 0) {
				aiMove();
				checkGameOver(game.board);
				playerMove();
				checkGameOver(game.board);
			} else {
				playerMove();
				checkGameOver(game.board);
				aiMove();
				checkGameOver(game.board);
			}
		}

	}

	/**
	 * @return 0 is player goes first, 1 if not
	 */
	public int doesPlayerGoFirst() {
		System.out.print("Would you like to go first? (y/n): ");
		String input = sc.nextLine().toLowerCase();

		while (!(input.equals("y") || input.equals("n"))) {
			System.out.print("Wrong input. \nWould you like to go first? (y/n): ");
			input = sc.nextLine().toLowerCase();
		}
		System.out.println();

		return (input.toLowerCase().equals("y")) ? 0 : 1;
	}

	/**
	 * Prompts the player to make a move that hasn't been taken on the board.
	 */
	public void playerMove() {
		System.out.print("Choose your next move: ");

		String input = sc.next().toUpperCase();
		char[] temp = input.toCharArray();

		while (temp.length != 2 || temp[0] < 65 || temp[0] > 72 || temp[1] < (char) 49 || temp[1] > (char) 56
				|| (game.board[(int) (temp[0] - 65)][(int) (temp[1] - 49)] != '-')) {
			System.out.print("Wrong input. \nChoose your next move: ");
			input = sc.next().toUpperCase();
			temp = input.toCharArray();
		}

		game.makeMove(temp[0], temp[1]);
		System.out.println();
		game.printBoard();
	}

	// Used to store AI moves at certain depths
	class Move {
		int i, j, evalNum;

		public Move(int i, int j, int evalNum) {
			this.i = i;
			this.j = j;
			this.evalNum = evalNum;
		}
	}

	/**
	 * AI makes move using alpha-beta pruning. Method will run a runnable AI
	 * process in a thread so that it may exit the alpha-beta pruning when the
	 * allotted time limit is up. Of the chosen moves, best one is picked and
	 * made on the board.
	 * 
	 * @throws InterruptedException
	 */
	public void aiMove() throws InterruptedException {
		ArrayList<Move> moves = new ArrayList<>();
		Move chosenMove = null;

		int depth = 0;
		Thread t = new Thread(ai);
		long startTime = System.currentTimeMillis();
		long endTime = startTime + ai.timeLimit;
		t.start();

		while (System.currentTimeMillis() < endTime) {
			ai.d = depth;
			Thread.sleep(1000);
			moves.add(new Move(ai.temp[0], ai.temp[1], ai.temp[2]));
			depth++;

		}
		t.interrupt();

		chosenMove = moves.get(0);
		for (Move m : moves) {
			if (chosenMove.evalNum < m.evalNum)
				chosenMove = m;
		}

		System.out.println("AI move is: " + (char) (chosenMove.i + 65) + (char) (chosenMove.j + 49));
		System.out.println();
		game.board[chosenMove.i][chosenMove.j] = 'X';
		game.printBoard();
		System.out.println();

	}

	/**
	 * Checks to see if either player or AI win the game or a draw has been
	 * made.
	 * 
	 * @param board
	 *            of the game
	 */
	public void checkGameOver(char[][] board) {
		int count = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == 'O') {
					if (((i - 3 >= 0) && (board[i - 1][j] == 'O' && board[i - 2][j] == 'O' && board[i - 3][j] == 'O'))
							|| (((j + 3) < 8)
									&& (board[i][j + 1] == 'O' && board[i][j + 2] == 'O' && board[i][j + 3] == 'O'))
							|| (((j - 3) >= 0)
									&& (board[i][j - 1] == 'O' && board[i][j - 2] == 'O' && board[i][j - 3] == 'O'))
							|| ((i + 3 < 8)
									&& (board[i + 1][j] == 'O' && board[i + 2][j] == 'O' && board[i + 3][j] == 'O'))) {
						System.out.println("Player wins.");
						System.exit(0);// you win
					}
				}
				if (board[i][j] == 'X') {
					if (((i - 3 >= 0) && (board[i - 1][j] == 'X' && board[i - 2][j] == 'X' && board[i - 3][j] == 'X'))
							|| (((j + 3) < 8)
									&& (board[i][j + 1] == 'X' && board[i][j + 2] == 'X' && board[i][j + 3] == 'X'))
							|| (((j - 3) >= 8)
									&& (board[i][j - 1] == 'X' && board[i][j - 2] == 'X' && board[i][j - 3] == 'X'))
							|| ((i + 3 < 8)
									&& (board[i + 1][j] == 'X' && board[i + 2][j] == 'X' && board[i + 3][j] == 'X'))) {
						System.out.println("AI wins.");
						System.exit(0); // ai win
					}
				}
				if (board[i][j] != '-')
					count++;
			}
		}
		if (count == 63) {
			System.out.println("Draw.");
			System.exit(0); // draw
		}
	}
}
