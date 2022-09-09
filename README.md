# sudokuSolver

Introduction:

Sudoku is a logic-based number-placement game that has the objective of filling a 9x9 grid 
so that each 3x3 grid, 1x9 column, and 1x9 row all contain all the numbers 1-9. The puzzles 
are unique as there are grid slots that are already filled in with a number. Sudoku puzzles
can contain zero to multiple solutions. While Sudoku may contain lots of numbers, it is not
a math game and requires logic and strategy.

About:

By filling in the 2d array in the main, the program will return a printed out visualization 
of a completed board along with the number soltuions possible for the given puzzle.

Working:

After being given a puzzle in the main, the program will attempt to solve the puzzle using a
stack backtracking method that uses a depth-first search approach(DFS). This is done by 
checking if every row, column, and grid is solvable while inputing a new numebr 1-9. It goes 
through each number in a numerically increasing order.
