
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NQueenProblem {
    //This method calculates all the row conflicts for a queen placed in a particular cell.

    public static int rowCollisions(int a[], int n) {
        int collisions = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    if (a[i] == a[j]) //Calculates whether the queen is in the same row.
                    {
                        collisions++;
                    }
                }
            }
        }
        return collisions;
    }
    //This method calculates all the diagonal conflicts for a particular position of the queen

    public static int diagonalCollisions(int a[], int n) {
        int collisions = 0, d = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    d = Math.abs(i - j);
                    if ((a[i] == a[j] + d) || (a[i] == a[j] - d)) //This verifies whether there are any diagonal collisions
                    {
                        collisions++;
                    }
                }
            }
        }
        return collisions;
    }
    //This method returns total number of collisions for a particular queen position

    public static int totalCollisions(int a[], int n) {
        int collisions = 0;
        collisions = rowCollisions(a, n) + diagonalCollisions(a, n);
        return collisions;
    }

    /*This method calculates the conflicts for the current state of the board and quits whenever finds a better state.
	 Note: This function is used for Hill Climbing algorithm*/
    public static boolean bestSolution(int a[], int n) {
        int min, collisions = 0, row = -1, col = -1, m;
        boolean checkBetter = false;
        int[] best;
        //Sets min variable to the collisions of current board so that if finds better than this it will quit.
        min = totalCollisions(a, n);
        best = Arrays.copyOf(a, n); //Create a duplicate array for handling different operations
        for (int i = 0; i < n; i++) //This iteration is for each column
        {
            if (checkBetter) //If it finds and better state than the current, it will quit
            {
                break;
            }
            m = best[i];
            for (int j = 0; j < n; j++) //This iteration is for each row in the selected column
            {
                if (j != m) //This condition ensures that, current queen position is not taken into consideration.
                {
                    best[i] = j;		//Assigning the queen to each position and then calculating the collisions
                    collisions = totalCollisions(best, n);
                    if (min > collisions) //If a better state is found, that particular column and row values are stored
                    {
                        col = i;
                        row = j;
                        min = collisions;
                        checkBetter = true;
                        break;
                    }
                }
                best[i] = m;		//Restoring the array to the current board position
            }
        }
        if (col == -1 || row == -1) //If there is no better state found
        {
            System.out.println("Reached Local Maxima with " + collisions + " Regenerating randomly");
            return false;
        }
        a[col] = row;
        return true;		//Returns true to the main function if there is any better state found
    }
    //Below function generates a random state of the board

    public static void randomGenerate(int[] a, int n) {
        Random gen = new Random();
        for (int i = 0; i < n; i++) {
            a[i] = gen.nextInt(n) + 0;
        }
    }

    public static int[] randomBoardGenerator(final int SIZE) {
        int[] board = new int[SIZE];
        Random random = new Random();
        for (int j = 0; j < board.length; j++) {
            board[j] = random.nextInt(board.length);
        }
        return board;
    }
    
//Below function verifies whether the current state of the board is the solution(I.e with zero conflicts)

    public static boolean isSolution(int a[], int n) {
        if (totalCollisions(a, n) == 0) {
            return true;
        }
        return false;
    }
    //Below method finds the solution for the n-queens problem with Min-Conflicts algorithm

    public static Solution GeneticAlg(List<Node> iStates, double mut) {
        //number of generations done
        int numGenerations = 0;
        final long START_TIME = System.nanoTime();

        Node highestFitnessFunction;
        Node[] crossOver;
        List<Node> crossedOver = new ArrayList(iStates.size());
        List<Node> bestCurrentGeneration;
        List<Node> nextGeneration = iStates;

        while (true) {
            bestCurrentGeneration = Genetic.select(nextGeneration);
            //Go through best list from the current generation to get solution
            for (Node n : bestCurrentGeneration) {
                if (n.COST == 0) {
                    return new Solution(numGenerations, 0, START_TIME, n);
                }
            }
            //clear the crossed over list of nodes
            crossedOver.clear();
            highestFitnessFunction = bestCurrentGeneration.get(0);
            //loop through best list 
            for (int j = 1; j < bestCurrentGeneration.size(); j++) {
                crossOver = Genetic.crossOver(highestFitnessFunction,
                        bestCurrentGeneration.get(j));
                crossedOver.add(crossOver[0]);
                crossedOver.add(crossOver[1]);
            }
            //clean up list 
            nextGeneration.clear();
            crossedOver.forEach((n) -> {
                nextGeneration.add(Genetic.mutation(n, mut));
            });
            numGenerations++;
        }

    }
    //Below function fills the Array List with numbers 0 to n-1

    public static void fillList(ArrayList<Integer> store, int n) {
        for (int i = 0; i < n; i++) {
            store.add(i);
        }
        return;
    }
    //Main function

    public static void main(String[] args) {
        int a[], b[];
        int n, totalRestart = 0, movesTotal = 0, movesSolution = 0, choice;
        System.out.println("Please enter the value of n:");
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        if ((n > 1 && n < 4) || n < 1) {
            System.out.println("*Please choose n value either greater than 3 or equals to 1 - Program Terminated");
            return;
        }
        if (n == 1) {
            System.out.println("There is no choice of algorithm for this value of 'n':");
            System.out.println("Q");
            return;
        }
        System.out.println("Please select one from the below options:");
        System.out.println("1. Solve n queens with Hill Climbing and Random Restart");
        System.out.println("2. Min Conflict method with random restart");
        System.out.println("3. Both methods and their results");
        choice = sc.nextInt();
        if (choice < 1 || choice > 3) {
            System.out.println("*Program terminated - Wrong option selected");
            return;
        }
        a = new int[n];
        b = new int[n];
        randomGenerate(a, n);	//Randomly generate the board
        b = Arrays.copyOf(a, n);
        //The below code will be executed if the user chooses he options 1 or 3(n-queen with Hill Climbing method)
        if (choice == 1 || choice == 3) {
            System.out.println("**********Hill Climbing with Random Restart*********");
            long startTime = System.currentTimeMillis();
            while (!isSolution(a, n)) //Executes until a solution is found
            {
                if (bestSolution(a, n)) //If a better state for a board is found
                {
                    movesTotal++;
                    movesSolution++;
                    continue;
                } else //If a better state is not found
                {
                    movesSolution = 0;
                    randomGenerate(a, n);	//Board is generated Randomly
                    totalRestart++;
                }
            }
            long endTime = System.currentTimeMillis();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (j == a[i]) {
                        System.out.print("Q ");
                    } else {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }
            System.out.println("Number of Restarts: " + totalRestart);
            System.out.println("Total number of moves taken: " + movesTotal);	//Gives the total number of moves from starting point
            System.out.println("Number of moves in the solution set: " + movesSolution); //Gives number of steps in the solution set.
            System.out.println("Time Taken in milli seconds: " + (endTime - startTime));
        }
        //If the Min-Conflict algorithm is selected
        if (choice == 2 || choice == 3) {

            boolean ans = true;
            int ITERATIONS;
            long totalRunTime = 0;
            long averageRunTime;
            System.out.println("GENERATION!!! \n");
            System.out.format("How many 21-Queen problems will be generated? ("
                    + "n > 100 is ideal) \n" + ">>> ");

            ITERATIONS = sc.nextInt();
            System.out.format("How many elements do you want with each "
                    + "generation? \n" + ">>> ");
            final int ELEMENTS_IN_GENERATION = sc.nextInt();
            final double MUTATION_MOD = 1.0;

            Solution s;

            for (int j = 0; j < ITERATIONS; j++) {
                List<Node> initStates = new ArrayList(ELEMENTS_IN_GENERATION);

                for (int k = 0; k < ELEMENTS_IN_GENERATION; k++) {
                    int BOARD_SIZE = 21;
                    initStates.add(new Node(randomBoardGenerator(BOARD_SIZE)));
                }

                s = GeneticAlg(initStates, MUTATION_MOD);
                long elapsedTime
                        = TimeUnit.NANOSECONDS.toNanos(s.END_TIME - s.START_TIME);
                totalRunTime += elapsedTime;
                TimeUnit.NANOSECONDS.toNanos(s.END_TIME - s.START_TIME);
                System.out.println("\nIterate #" + (j + 1));
                System.out.println(s.FINAL_STATE);
                System.out.println("Time Passed: "
                        + elapsedTime);
                System.out.format("Generated nodes: %d %n", s.TOTAL_MOVES);
            }
            averageRunTime = totalRunTime / ITERATIONS;
            System.out.println("Average Runtime (nanoseconds)= "
                    + averageRunTime);
        }
    }

}
