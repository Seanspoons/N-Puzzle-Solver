package fifteenpuzzle;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the contents of the N-Puzzle board and it's characteristics
 * 
 * @author Sean Wotherspoon & Nolan Whittaker
 */

public class PuzzleBoard implements Comparable<PuzzleBoard> {

    // Variables
	private int[][] board;
	private int size;
	private int row;
	private int col;
    private int actualCost;
    private int heuristicCost;
    private int totalCost;
    private PuzzleBoard parentBoard;
    private String state;

    // Constructor
	public PuzzleBoard(int[][] board) {
		this.board = board;
		this.size = board.length;
		this.heuristicCost = 0;
		this.totalCost = 0;
		this.actualCost = 0;
        this.parentBoard = null;
        this.state = arrayToString(board);

		// Find blank space
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 0) {
					row = i;
					col = j;
					break;
				}
			}
		}
	}

    // Move Methods
    public void moveUp() {
        if (row == 0) {
            return;
        } else {
            int temp = board[row - 1][col];
            board[row][col] = temp;
            board[row - 1][col] = 0;
            row = row - 1;
            updateState();
        }
    }

    public void moveDown() {
        if (row == board.length - 1) {
            return;
        } else {
            int temp = board[row + 1][col];
            board[row][col] = temp;
            board[row + 1][col] = 0;
            row = row + 1;
            updateState();
        }
    }

    public void moveLeft() {
        if (col == 0) {
            return;
        } else {
            int temp = board[row][col - 1];
            board[row][col] = temp;
            board[row][col - 1] = 0;
            col = col - 1;
            updateState();
        }
    }

    public void moveRight() {
        if (col == size - 1) {
            return;
        } else {
            int temp = board[row][col + 1];
            board[row][col] = temp;
            board[row][col + 1] = 0;
            col = col + 1;
            updateState();
        }
    }

    // Getters
	public int[][] getBoard() {
		return board;
	}

	public int getTotalCost() {
        totalCost = actualCost + heuristicCost;
		return totalCost;
	}

	public int getActualCost() {
		return actualCost;
	}

	public int getHeuristicCost() {
		return heuristicCost;
	}

    public PuzzleBoard getParent() {
        return parentBoard;
    }

    public String getState() {
        return state;
    }

    public int getManhattanDistance() {
        int distance = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    int p = ((board.length * board.length) - 1) / board.length;
                    int g = ((board.length * board.length) - 1) % board.length;
                    distance = distance + Math.abs(i-p) + Math.abs(j-g);
                } else {
                    int p = (board[i][j] - 1) / board.length;
                    int g = (board[i][j] - 1) % board.length;
                    distance = distance + Math.abs(i-p) + Math.abs(j-g);
                }

            }
        }
        return distance;
    }

    public int getLinearConflict() {
        int conflictCount = 0;
        conflictCount += getLinearConflictForRow();
        conflictCount += getLinearConflictForColumn();
        return conflictCount;
    }

    public int getCombinationHeuristic() {
        if(size <= 4){
            return (1*getHammingDistance()) + (7*getLinearConflict()) + (4*getManhattanDistance());
        } else if(size == 5){
            return (5*getHammingDistance()) + (1*getLinearConflict()) + (1*getManhattanDistance());
        } else if(size == 6) {
            return (4*getHammingDistance()) + (6*getLinearConflict()) + (1*getManhattanDistance());
        } else if(size == 7) {
            return (4*getHammingDistance()) + (7*getLinearConflict()) + (1*getManhattanDistance());
        } else {
            return (4*getHammingDistance()) + (7*getLinearConflict()) + (1*getManhattanDistance());
        }
    }

    public int getLinearConflictForRow() {
        int conflictCount = 0;

        for (int i = 0; i < size; i++) { // for each row
            for (int j = 0; j < size; j++) { // to go down
                if (board[i][j] != 0) {
                    int goalRow = (board[i][j] - 1) / size;
                    if (goalRow == i) {
                        for (int m = j + 1; m < size; m++) {
                            if (goalRow == (board[i][m] - 1) / size && board[i][m] != 0
                                    && (board[i][m] - 1) / size == i) {
                                if (board[i][m] < board[i][j]) {
                                    conflictCount++;
                                }
                            }
                        }
                    }
                }

            }
        }
        // System.out.println("There are " + conflictCount + " linear conflicts in
        // rows");
        return conflictCount;
    }

    public int getLinearConflictForColumn() {
        int conflictCount = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[j][i] != 0) {
                    int goalCol = (board[j][i] - 1) % size;
                    if (goalCol == i) {
                        for (int m = j + 1; m < size; m++) {
                            if ((board[m][i] - 1) % size == goalCol && board[m][i] != 0
                                    && (board[m][i] - 1) % size == i) {
                                if (board[m][i] < board[j][i]) {
                                    conflictCount++;
                                }
                            }
                        }
                    }
                }

            }
        }
        // System.out.println("Row conflicts" + conflictCount);

        return conflictCount;
    }

    public int getHammingDistance() {
        int distance = 0;
        int count = 1;
        int n = board.length;
    
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != count && board[i][j] != 0) {
                    distance++;
                }
                count++;
            }
        }
    
        return distance;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getSize() {
        return size;
    }

    // Setters
    public void setTotalCost(int val) {
        totalCost = val;
    }

	public void setActualCost(int val) {
		actualCost = val;
	}

	public void setHeuristicCost(int val) {
		heuristicCost = val;
	}

    public void setParent(PuzzleBoard parent) {
        this.parentBoard = parent;
    }

    // Utility Methods
    public boolean isGoalState() {

        int index = 1;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if(i == (size - 1) && j == (size - 1)) {
                    if(board[i][j] != 0) {
                        return false;
                    }
                }
                else if(board[i][j] != index) {
                    return false;
                }
                index++;
            }
        }
			
		return true;
    }

    public static String arrayToString(int[][] array) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : array) {
            for (int i : row) {
                sb.append(i).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private int[][] copyBoard(int[][] board) {
        int[][] copy = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }

    public void updateState() {
        String newState = arrayToString(board);
        state = newState;
    }

    public List<PuzzleBoard> expandNode() {

        List<PuzzleBoard> neighbours = new ArrayList<>();

        // Generate successors by swapping the blank tile with its neighboring tiles
        if(row > 0) {
            // Create new board with UP called
            int[][] copiedBoard = copyBoard(board);
            PuzzleBoard up = new PuzzleBoard(copiedBoard);
            up.moveUp();
            neighbours.add(up);
        }

        if(row < size - 1) {
            // Create new boarad with DOWN called
            int[][] copiedBoard = copyBoard(board);
            PuzzleBoard down = new PuzzleBoard(copiedBoard);
            down.moveDown();
            neighbours.add(down);
        }

        if(col > 0) {
            // Create new board with LEFT called
            int[][] copiedBoard = copyBoard(board);
            PuzzleBoard left = new PuzzleBoard(copiedBoard);
            left.moveLeft();
            neighbours.add(left);
        }

        if(col < size - 1) {
            // Create new board with RIGHT called
            int[][] copiedBoard = copyBoard(board);
            PuzzleBoard right = new PuzzleBoard(copiedBoard);
            right.moveRight();
            neighbours.add(right);
        }

        return neighbours;
    }

    public void displayBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Compare nodes based on their f-score (g-score + h-score) (used for priortiy queue)
    @Override
    public int compareTo(PuzzleBoard other) {
        int fScore = this.actualCost + this.heuristicCost;
        int otherFScore = other.getActualCost() + other.getHeuristicCost();
        return Integer.compare(fScore, otherFScore);
    }

}