package fifteenpuzzle;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Contains A* algorithm N-Puzzle Solver and greedy A* algorithm N-Puzzle Solver
 * 
 * @author Sean Wotherspoon & Nolan Whittaker
 */

public class PuzzleSolver {

    private PuzzleBoard finishedBoard;

    // Initalize lists
    private PriorityQueue<PuzzleBoard> openList;
    private HashMap<String, PuzzleBoard> closedList;

    // Constructor
    public PuzzleSolver(PuzzleBoard board, int size) {

        if(size <= 3) { // Run A* algorithm on 3x3 and 4x4 boards
            finishedBoard = aStarSolver(board);
        } else { // Run greedy A* algorithm on 5x5 boards and up
            finishedBoard = aStarSolverNoDepth(board);
        }
    }

    // Standard A* Solver
    public PuzzleBoard aStarSolver(PuzzleBoard board) {
        
        // Initalize open list with first node
        openList = new PriorityQueue<>();
        board.setActualCost(0);
        board.setHeuristicCost(board.getLinearConflict());
        board.setTotalCost(board.getActualCost() + board.getHeuristicCost());
        openList.add(board);

        // Initalize closed list
        closedList = new HashMap<>();

        //int iteration = 1;

        // Loop through
        while(!openList.isEmpty()) {

            PuzzleBoard currentBoard = openList.poll();

            // If the current node is the goal node, return the path
            if (currentBoard.isGoalState()) {
                //System.out.println("Goal state has been reached and took: " + iteration + " iterations!");
                return currentBoard;
            }

            // Add current node to closed list to indicate it has been expanded
            closedList.put(currentBoard.getState(), currentBoard);

            // Expand current node to generate a list of neighbours
            List<PuzzleBoard> neighbours = currentBoard.expandNode();

            // Check neighbours against closed list and add them to open list if they are not present
            for(PuzzleBoard neighbour : neighbours) {

                if (closedList.containsKey(neighbour.getState())) {
                    continue; // skip this iteration and continue to next one
                }

                // Check if the neighbor is already in the open list
                if (openList.contains(neighbour)) {

                    // If it is in list then calculate new actual cost for the neighbor
                    int newActualCost = currentBoard.getActualCost() + 1;
                    if (newActualCost < neighbour.getActualCost()) {
                        // If the new cost is lower then update the neighbor's actual cost and parent
                        neighbour.setActualCost(newActualCost);
                        neighbour.setParent(currentBoard);
                    }

                } else {

                    // Set the neighbor's actual cost, heuristic cost, and parent
                    int newActualCost = currentBoard.getActualCost() + 1;
                    int newHeuristicCost = neighbour.getLinearConflict();
                    neighbour.setActualCost(newActualCost);
                    neighbour.setHeuristicCost(newHeuristicCost);
                    neighbour.setTotalCost(newActualCost + newHeuristicCost);
                    neighbour.setParent(currentBoard);

                    // Add neighbour to the priority queue to be explored
                    openList.add(neighbour);
                }
            }
            //iteration++;
        }
        return null;
    }

    // Greedy A* Solver
    public PuzzleBoard aStarSolverNoDepth(PuzzleBoard board) {

        // Initalize open list with first node
        openList = new PriorityQueue<>();
        board.setActualCost(0);
        board.setHeuristicCost(board.getCombinationHeuristic());
        board.setTotalCost(board.getHeuristicCost());
        openList.add(board);

        // Initalize closed list
        closedList = new HashMap<>();

        //int iteration = 1;

        // Loop through
        while(!openList.isEmpty()) {

            PuzzleBoard currentBoard = openList.poll();

            // If the current node is the goal node, return the path
            if (currentBoard.isGoalState()) {
                //System.out.println("Goal state has been reached and took: " + iteration + " iterations!");
                return currentBoard;
            }

            // Add current node to closed list to indicate it has been expanded
            closedList.put(currentBoard.getState(), currentBoard);

            // Expand current node to generate a list of neighbours
            List<PuzzleBoard> neighbours = currentBoard.expandNode();

            // Check neighbours against closed list and add them to open list if they are not present
            for(PuzzleBoard neighbour : neighbours) {

                if (closedList.containsKey(neighbour.getState())) {
                    continue; // skip this iteration and continue to next one
                }

                // Check if the neighbor is already in the open list
                if (openList.contains(neighbour)) {



                } else {

                    // Set the neighbor's actual cost, heuristic cost, and parent
                    int newHeuristicCost = neighbour.getCombinationHeuristic();
                    neighbour.setHeuristicCost(newHeuristicCost);
                    neighbour.setTotalCost(newHeuristicCost);
                    neighbour.setParent(currentBoard);

                    // Add neighbour to the priority queue to be explored
                    openList.add(neighbour);
                }
            }
            //iteration++;
        }
        return null;
    }

    public PuzzleBoard getSolutionBoard() {
        return finishedBoard;
    }
}