
public final class GameBoard {
	protected char[][] board = new char[8][8];

	public GameBoard() {
		initializeBoard();
	}

	/**
	 * Initializes an empty board full of '-'.
	 */
	public void initializeBoard() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = '-';
			}
		}
	}

	/**
	 * Allows player to make a move from input.
	 */
	public void makeMove(char i, char j) {
		int col = (int) j - 49;
		i = Character.toUpperCase(i);
		int row = (int) i - 65;
		board[row][col] = 'O';
	}

	/**
	 * Prints game board to the screen.
	 */
	public void printBoard() {
		System.out.println("  1 2 3 4 5 6 7 8");
		for (int i = 0; i < 8; i++) {
			System.out.print((char) (i + 65) + " ");
			for (int j = 0; j < 8; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
