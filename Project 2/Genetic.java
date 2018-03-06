
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Genetic Class implements methods that will emulate the Genetic Algorithm.
 */
public class Genetic {

    //initialize random variable
    private static final Random random = new Random();

    //does the crossover between two random nodes by using a random point
    static Node[] crossOver(Node n1, Node n2) {
        final int BOARD_SIZE = n1.BOARD.length;
        final int crossOver = random.nextInt(BOARD_SIZE - 1);

        //crossover with 1 - 2
        int[] crossBoard1 = new int[BOARD_SIZE];
        System.arraycopy(n1.BOARD, 0, crossBoard1, 0, crossOver);
        System.arraycopy(n2.BOARD, crossOver, crossBoard1, crossOver,
                BOARD_SIZE - crossOver);
        Node n12 = new Node(crossBoard1);

        //crossover with 2 - 1
        int[] crossBoard2 = new int[BOARD_SIZE];
        System.arraycopy(n2.BOARD, 0, crossBoard2, 0, crossOver);
        System.arraycopy(n1.BOARD, crossOver, crossBoard2, crossOver,
                BOARD_SIZE - crossOver);
        Node n21 = new Node(crossBoard2);

        return new Node[]{n12, n21};
    }

    // select the best 
    static List<Node> select(List<Node> nodes) {
        //List contains best elements
        Collections.sort(nodes, (Node n1, Node n2) -> {
            return n1.COST - n2.COST;
        });

        List<Node> select = new ArrayList<>();
        for (int j = 0; j <= nodes.size() / 2; j++) {
            select.add(nodes.get(j));
        }
        return select;
    }

    //does mutation on the node provided with chance to mutate also provided
    static Node mutation(Node n, double mutateChance) {
        if (mutateChance < random.nextDouble()) {
            return n;
        }
        int[] mutatedNodes = Arrays.copyOf(n.BOARD, n.BOARD.length);
        final int MUTATE = random.nextInt(mutatedNodes.length);
        mutatedNodes[MUTATE] = random.nextInt(mutatedNodes.length);

        return new Node(mutatedNodes);
    }

}
