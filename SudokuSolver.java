import java.util.Stack;


public class SudokuSolver {

    ///////////////////
    // INNER CLASSES //
    ///////////////////

    public class Choice {
        int row, col, num;

        public Choice() {  // a "first" choice, num from 1-9
            int[][] mg = mergedGrid();
            boolean flag = false;
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    if (mg[r][c] == 0) {
                        this.row = r;
                        this.col = c;
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
            this.num = 1;
        }

        public Choice(int num, int row, int col) {
            this.num = num;
            this.row = row;
            this.col = col;
        }

        public Choice nextChoice() {
            return new Choice(this.num + 1, row, col);
        }

        public boolean unconsideredChoicesExist() {
            return this.num < 9;
        }

        public boolean couldLeadToSolution() {
            int[][] mg = mergedGrid();
            if (num > 9) return false;
            for (int i = 0; i < 9; i++) {
                if (num == mg[i][col]) {
                    return false;
                }
                if (num == mg[row][i]) {
                    return false;
                }
            }
            int boxRow, boxCol;
            boxRow = row - (row % 3);
            boxCol = col - (col % 3);
            for (int r = boxRow; r < boxRow + 3; r++) {
                for (int c = boxCol; c < boxCol + 3; c++) {
                    if (mg[r][c] == num) {
                        return false;
                    }
                }
            }
            return true;
        }

        public String toString() {
            return num + "" + row + "" + col;
        }

    }

    ////////////////////////
    // INSTANCE VARIABLES //
    ////////////////////////

    int[][] grid;
    Stack<Choice> stack = new Stack<Choice>();
    // DO NOT ADD ANY ADDITIONAL INSTANCE VARIABLES TO THIS SudokuSolver CLASS!

    //////////////////
    // CONSTRUCTORS //
    //////////////////

    public SudokuSolver(int[][] grid) {
        this.grid = new int[9][9];
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                this.grid[r][c] = grid[r][c];
            }
        }
    }


    //////////////////////
    // INSTANCE METHODS //
    //////////////////////

    public boolean solutionFound() {
        int[][] mg = mergedGrid();
        for(int r= 0 ; r < 9; r++){
            for(int c = 0; c < 9; c++){
                if (mg[r][c] == 0){
                    return false;
                }
            }
        }
        return true;
    }

    private void printSolution() {
        int[][]mg = mergedGrid();
        String s ="";
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++ ) {
                s += mg[r][c];
            }
            s += System.lineSeparator();
        }
        System.out.println(s);
    }

    private int[][] mergedGrid() {
        int[][] mergedGrid = new int[9][9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                mergedGrid[r][c] = grid[r][c];
            }
        }

        for (Choice choice : stack) {            /* <-- notice here
that we use a for-each loop */
            mergedGrid[choice.row][choice.col] = choice.num;   /*     this is only
possible as java.util.Stack */
        }                                                      /*     implements
Iterable<Item> */

        return mergedGrid;
    }



    public int solve(){  // DO NOT MODIFY THIS METHOD!

        int numSolutions = 0;

        Choice choice = new Choice();                 /* no-arg constructor gives a "first" choice */

        while (true) {

            if (choice.couldLeadToSolution()) {
                stack.push(choice);
                choice = new Choice();                /* no-arg constructor gives a "first" choice */
            } else if (choice.unconsideredChoicesExist()) {
                choice = choice.nextChoice();
            } else {
                if (stack.isEmpty())
                    break;           /* here, you want to pop to backtrack (is it safe? if not, you are done!) */
                choice = stack.pop().nextChoice();    /* otherwise, pick up where you left off with your last choice.. */
            }

            if (solutionFound()) {
                printSolution();
                numSolutions++;
                choice = stack.pop();                 /* normally we check to make sure -- but it's safe to pop.. why? */
                choice = choice.nextChoice();         /* keep going to try to find other solutions */
            }
        }

        return numSolutions;
    }

    ///////////////////////////////////
    // TESTING VIA THE main() METHOD //
    ///////////////////////////////////

    public static void main(String[] args){   // DO NOT MODIFY THIS METHOD!

        int[][] solvableGrid =
                        {{0, 3, 9, 0, 0, 0, 7, 0, 0},
                        {0, 0, 0, 7, 0, 0, 1, 0, 0},
                        {6, 0, 0, 0, 8, 0, 0, 0, 4},
                        {8, 0, 4, 0, 0, 7, 0, 0, 6},
                        {0, 0, 0, 8, 0, 0, 4, 0, 0},
                        {0, 5, 0, 2, 0, 6, 8, 1, 0},
                        {0, 0, 0, 0, 0, 0, 0, 7, 0},
                        {5, 8, 0, 0, 0, 3, 9, 4, 0},
                        {7, 2, 3, 4, 0, 8, 6, 0, 0}};

        int[][] unsolvableGrid =
                {{3, 1, 6, 5, 7, 8, 4, 2, 0},
                        {5, 2, 0, 0, 0, 0, 0, 0, 0},
                        {0, 8, 7, 0, 0, 0, 0, 3, 1},
                        {0, 0, 3, 0, 1, 0, 0, 8, 0},
                        {9, 0, 0, 8, 6, 3, 0, 0, 5},
                        {0, 5, 0, 0, 9, 0, 6, 0, 0},
                        {1, 3, 0, 0, 0, 0, 2, 5, 0},
                        {0, 0, 0, 0, 0, 0, 0, 7, 4},
                        {0, 0, 5, 2, 0, 6, 3, 0, 0}};

        SudokuSolver solver;
        int numSolutions;

        System.out.println("Testing solvable puzzle..." + System.lineSeparator());
        solver = new SudokuSolver(solvableGrid);
        numSolutions = solver.solve();
        if (numSolutions == 0) System.out.println(System.lineSeparator() + "No solution exists");

        System.out.println("=================" + System.lineSeparator());

        System.out.println("Testing unsolvable puzzle...");
        solver = new SudokuSolver(unsolvableGrid);
        numSolutions = solver.solve();
        if (numSolutions == 0) System.out.println(System.lineSeparator() + "No solution exists");

    }
}