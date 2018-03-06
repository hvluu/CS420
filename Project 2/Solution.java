
/**
 * Solution class that holds the solution to a queen problem, with
 * additional data measurements
 */
class Solution {

    final int TOTAL_MOVES;
    final int SEARCH_COST;
    final long START_TIME;
    final long END_TIME;
    final Node FINAL_STATE;

    //contructor with measurement variables
    Solution(int tm, int sc, long st, Node fs) {
        this.TOTAL_MOVES = tm;
        this.SEARCH_COST = sc;
        this.FINAL_STATE = fs;
        this.START_TIME = st;
        this.END_TIME = System.nanoTime();
    }

}
