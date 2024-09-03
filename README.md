# N-Puzzle Solver

## Overview

This project implements an N-Puzzle solver using the A* algorithm with various heuristics to solve puzzles of different sizes. The project is structured into several classes, each responsible for a specific aspect of the solver.

## Classes and Their Responsibilities

### 1. PuzzleBoardReader

The `PuzzleBoardReader` class is responsible for reading the puzzle from a text file and storing it in a 2D array.

- **Constructor:** Takes a filename as input and reads the board size and layout using a buffered reader.
- **2D Array Representation:** The puzzle is stored as a 2D array, with `0` representing the empty tile.
- **Whitespace Handling:** Extra white space is scanned and replaced with `0`.
- **Getter:** Provides access to the 2D array for use in other classes.

### 2. PuzzleBoard

The `PuzzleBoard` class encapsulates all the essential information about each puzzle state, including the board's 2D array, costs, heuristic values, and move methods.

- **Constructor and Variables:** 
  - Stores the 2D array, size, empty row/column, costs, parent board, and state as a string.
  - Costs are initialized to 0, and the parent board is set to `null`.
  - Identifies the empty tile's position.

- **Move Methods and String Representation:** 
  - Includes methods to move the empty tile in four directions.
  - Updates the board's state as a string after each move.
  - Uses a `HashMap` to store closed states efficiently.

- **Heuristics:** 
  - Implements various heuristics used in the A* algorithm, including Manhattan distance, Hamming distance, and a combination of linear conflict with Manhattan distance.
  - Tested different linear combinations of these heuristics for solving puzzles of varying sizes (3x3, 4x4, 5x5, 6x6, 7x7).

- **Utility Methods:** 
  - Implements the `Comparable` interface to compare nodes based on their f-score (actual cost + heuristic cost).
  - `expandNode` method creates new `PuzzleBoard` objects representing all possible moves from the current state.

### 3. PuzzleBoardWriter

The `PuzzleBoardWriter` class is responsible for writing the solution to a file.

- **Constructor:** Takes a `PuzzleBoard` object (the solved board) and an output filename as input.
- **Backtracking and Writing:** 
  - Backtracks from the solved board to the root node, identifying the moves made at each step.
  - Reverses the order of moves and writes them to the output file.

### 4. PuzzleSolver

The `PuzzleSolver` class is where the A* algorithm is implemented.

- **A* Algorithm:** 
  - Two versions of A* were implemented: regular A* (optimal solution) and greedy A* (better performance on larger puzzles).
  - Uses a priority queue for the open list and a `HashMap` for the closed list.

- **Heuristic Selection:** 
  - Regular A* is used for 3x3 puzzles, while greedy A* is used for larger puzzles.

### 5. Solver

The `Solver` class ties everything together.

- **Execution:** 
  - Reads the puzzle from a file using `PuzzleBoardReader`.
  - Stores the puzzle in a `PuzzleBoard` object.
  - Runs the appropriate solver depending on the puzzle size.
  - Writes the solution to a file using `PuzzleBoardWriter`.

## Final Thoughts

Overall, creating an N-Puzzle solver was a valuable learning experience. We encountered challenges, particularly with the linear conflict heuristic and implementing the A* algorithm. However, these challenges helped us improve our programming skills and deepen our understanding of Java.

This project was a significant learning opportunity, especially as our first project in Java.

## Usage

To use the N-Puzzle Solver:

1. Prepare a text file representing the N-Puzzle board. The empty tile should be represented by `0`.
2. Run the program with the input file and output file as arguments.
3. The solution will be written to the output file, detailing the moves required to solve the puzzle.

## Contributors

- Sean Wotherspoon
- Nolan Whittaker
