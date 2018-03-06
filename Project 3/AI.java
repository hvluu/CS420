

public class AI implements Runnable {

	protected int timeLimit;
	protected int[] temp = new int[3];
	protected GameBoard gb;
	protected int d;

	public AI(GameBoard b) {
		gb = b;
	}

	public void run() {
		makeMove(gb, d);
	}

	/**
	 * AI's move is determined through a minimax function with alpha-beta
	 * pruning to determine its best move. The method stores the best move's
	 * location and evaluation in an array.
	 * 
	 * @param gb
	 *            board of the game
	 * @param depth
	 *            current depth to decrement from
	 */
	public void makeMove(GameBoard gb, int depth) { // alpha-beta pruning
		int alpha = -500000, beta = 500000, score = 0;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (gb.board[i][j] == '-') {
					gb.board[i][j] = 'X';
					score = minVal(gb, depth - 1, alpha, beta);
					if (score > alpha) {
						temp[0] = i;
						temp[1] = j;
						temp[2] = evaluate(gb.board);
						alpha = score;
					}
					gb.board[i][j] = '-';
				}

			}
		}
	}

	/**
	 * Finds the minimum value of all max valued successors.
	 * 
	 * @param gb
	 *            board of the game
	 * @param depth
	 *            current depth to decrement
	 * @param alpha
	 *            the value of the best choice found so far at any choice point
	 *            along path for maxVal
	 * @param beta
	 *            the value of the best choice found so far at any choice point
	 *            along the path for minVal
	 * @return utility value of state
	 */
	public int minVal(GameBoard gb, int depth, int alpha, int beta) {
		int v = 500000, score;

		if (depth < 0)
			return evaluate(gb.board);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				if (gb.board[i][j] == '-') {
					gb.board[i][j] = 'O';
					score = maxVal(gb, depth - 1, alpha, beta);
					v = min(v, score);
					gb.board[i][j] = '-';

					if (v <= alpha)
						return v;

					beta = min(beta, v);
				}

			}
		}
		return v;
	}

	/**
	 * Finds the maximum value of all min valued successors.
	 * 
	 * @param gb
	 *            board of the game
	 * @param depth
	 *            current depth to decrement
	 * @param alpha
	 *            the value of the best choice found so far at any choice point
	 *            along path for maxVal
	 * @param beta
	 *            the value of the best choice found so far at any choice point
	 *            along the path for minVal
	 * @return utility value of state
	 */
	public int maxVal(GameBoard gb, int depth, int alpha, int beta) {
		int v = -500000, score;

		if (depth < 0)
			return evaluate(gb.board);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				if (gb.board[i][j] == '-') {
					gb.board[i][j] = 'X';
					score = minVal(gb, depth - 1, alpha, beta);
					v = max(v, score);
					gb.board[i][j] = '-';

					if (v >= beta)
						return v;

					alpha = max(alpha, v);

				}
			}
		}
		return v;
	}

	/**
	 * @param num1
	 *            first number to compare
	 * @param num2
	 *            second number to compare
	 * @return the smaller number
	 */
	public int min(int num1, int num2) {
		return (num1 > num2) ? num2 : num1;
	}

	/**
	 * @param num1
	 *            first number to compare
	 * @param num2
	 *            second number to compare
	 * @return the larger number
	 */
	public int max(int num1, int num2) {
		return (num1 > num2) ? num1 : num2;
	}

	/**
	 * Function determines moves for the AI. Possible AI moves are prioritized
	 * differently with the win condition having the most priority. If it is the
	 * AI's turn, it will move to space D4, because that space has the maximum
	 * amount of moves for any player to make along with 3 other spaces.
	 * 
	 * @param board
	 *            of game
	 * @return evaluation value of given state of the board
	 */
	public int evaluate(char[][] board) {
		int score = 0;
		int count = 0;
		int firstTurnScore = 0;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == 'X') {
					if ((i + 3 < 8) && (board[i + 1][j] == 'X' && board[i + 2][j] == 'X' && board[i + 3][j] == 'X'))
						score += 10000000;
					if ((j + 3 < 8) && (board[i][j + 1] == 'X' && board[i][j + 2] == 'X' && board[i][j + 3] == 'X'))
						score += 10000000;
					if ((i - 3 >= 0) && (board[i - 1][j] == 'X' && board[i - 2][j] == 'X' && board[i - 3][j] == 'X'))
						score += 10000000;
					if ((j - 3 >= 0) && (board[i][j - 1] == 'X' && board[i][j - 2] == 'X' && board[i][j - 3] == 'X'))
						score += 10000000;

					if ((i + 3 < 8) && (board[i + 1][j] == 'O' && board[i + 2][j] == 'O' && board[i + 3][j] == 'O'))
						score += 100000;
					if ((j + 3 < 8) && (board[i][j + 1] == 'O' && board[i][j + 2] == 'O' && board[i][j + 3] == 'O'))
						score += 100000;
					if ((i - 3 >= 0) && (board[i - 1][j] == 'O' && board[i - 2][j] == 'O' && board[i - 3][j] == 'O'))
						score += 100000;
					if ((j - 3 >= 0) && (board[i][j - 1] == 'O' && board[i][j - 2] == 'O' && board[i][j - 3] == 'O'))
						score += 100000;

					if ((i + 2 < 8) && (board[i + 1][j] == 'O' && board[i + 2][j] == 'O'))
						score += 1000;
					if ((j + 2 < 8) && (board[i][j + 1] == 'O' && board[i][j + 2] == 'O'))
						score += 1000;
					if ((i - 2 >= 0) && (board[i - 1][j] == 'O' && board[i - 2][j] == 'O'))
						score += 1000;
					if ((j - 2 >= 0) && (board[i][j - 1] == 'O' && board[i][j - 2] == 'O'))
						score += 1000;

					if ((i + 2 < 8) && (board[i + 1][j] == 'X' && board[i + 2][j] == 'X'))
						score += 100;
					if ((j + 2 < 8) && (board[i][j + 1] == 'X' && board[i][j + 2] == 'X'))
						score += 100;
					if ((i - 2 >= 0) && (board[i - 1][j] == 'X' && board[i - 2][j] == 'X'))
						score += 100;
					if ((j - 2 >= 0) && (board[i][j - 1] == 'X' && board[i][j - 2] == 'X'))
						score += 100;

					if ((i + 1 < 8) && (board[i + 1][j] == 'O'))
						score += 1;
					if ((j + 1 < 8) && (board[i][j + 1] == 'O'))
						score += 1;
					if ((i - 1 >= 0) && (board[i - 1][j] == 'O'))
						score += 1;
					if ((j - 1 >= 0) && (board[i][j - 1] == 'O'))
						score += 1;

					if (count == 27)
						firstTurnScore = 1000000;
				}
				if (board[i][j] == '-')
					count++;
			}
		}
		if (count == 63)
			return firstTurnScore;
		return score;
	}
}
