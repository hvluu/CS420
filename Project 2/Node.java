import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Node class implements methods that will emulate a node used in a queen
 * problem
 */
public class Node {
    //board simulating the chess board

    final int BOARD[];
    //cost of the board
    final int COST;

//------------------------------------------------------------------------------     
    //contructors for the Node class
    Node(int[] board, int cost) {
        this.BOARD = board;
        this.COST = cost;
    }

    Node(int[] board) {
        this(board, numAttQueens(board));
    }

//------------------------------------------------------------------------------
    //returns # of attacking queens
    static int numAttQueens(final int[] BOARD) {
        int count = 0;
        for (int j = 0; j < BOARD.length; j++) {
            count += numAttQueensMaxIndex(BOARD, j);
        }
        return count;
    }

    int numAttQueens() {
        return numAttQueens(BOARD);
    }

//------------------------------------------------------------------------------     
    //returns # of attacking queens on a board with index indicating max
    //to check
    static int numAttQueensMaxIndex(final int[] BOARD, int i) {
        int count = 0;
        final int MAX = BOARD[i];
        for (int j = 0; j < i; j++) {
            if (BOARD[j] == MAX) {
                count++;
            }
            if (Math.abs(BOARD[j] - MAX) == Math.abs(i - j)) {
                count++;
            }
        }
        return count;
    }

    int numAttQueensMaxIndex(int i) {
        return numAttQueensMaxIndex(BOARD, i);
    }

//------------------------------------------------------------------------------     
    //will generate successors for the steepest algorithm 
    List<Node> successorGenerator() {
        List<Node> successors = new ArrayList<>((BOARD.length * BOARD.length)
                - BOARD.length);
        int[] copy;
        int[] board = Arrays.copyOf(BOARD, BOARD.length);
        int value;
        int cost;

        for (int j = 0; j < board.length; j++) {
            value = board[j];
            for (int k = 0; k < board.length; k++) {
                if (k == value) {
                    continue;
                }
                cost = numAttQueens(board);
                copy = Arrays.copyOf(board, board.length);
                board[j] = k;
                successors.add(new Node(copy, cost));
            }
            board[j] = value;
        }
        if (successors.size() != (BOARD.length * BOARD.length)
                - BOARD.length) {
            System.out.println("This is not right.");
        }

        Collections.sort(successors, (Node n1, Node n2) -> {
            return n1.COST - n2.COST;
        });
        return successors;
    }

//------------------------------------------------------------------------------     
    //returns a string to represent the node
    //board path
    @Override
    public String toString() {
        //used for easier input
        StringBuilder strB = new StringBuilder();
        //layout of the board
        for (int j = 0; j < BOARD.length; j++) {
            for (int k = 0; k < BOARD.length; k++) {
                if (BOARD[j] == k) {
                    strB.append('Q');
                } else {
                    strB.append(',');
                }
                strB.append(' ');
            }
            //make room for the next line
            strB.append('\n');
        }
        strB.deleteCharAt(strB.length() - 1);
        return strB.toString();
    }

}
