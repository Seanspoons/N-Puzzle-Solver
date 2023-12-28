package fifteenpuzzle;

import java.io.*;
import java.util.*;

/**
 * Uses the final solution board and goes up the parent chain, checks what move was made and writes it to file
 * 
 * @author Sean Wotherspoon & Nolan Whittaker
 */

public class PuzzleBoardWriter {

	public PuzzleBoardWriter(PuzzleBoard finalBoard, String outputName) {

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
			List<String> col = new ArrayList<String>();
			PuzzleBoard temp = finalBoard;
			// Find the root to the node.
			while (temp.getParent() != null) {
				char move = ' ';
				int tempVal = 0;

				// Check what move was made
				int[][] board1 = temp.getBoard();
				int[][] board2 = temp.getParent().getBoard();

				for (int i = 0; i < finalBoard.getBoard().length; i++) {
					for (int j = 0; j < finalBoard.getBoard().length; j++) {
						if (board1[i][j] != board2[i][j] && board1[i][j] != 0) {

							tempVal = board1[i][j];

							// Check down
							if (i != finalBoard.getBoard().length - 1 && board1[i][j] == board2[i + 1][j]) {
								move = 'U';
							}

							if (i != 0 && board1[i][j] == board2[i - 1][j]) {
								move = 'D';
							}

							if (j != finalBoard.getBoard().length - 1 && board1[i][j] == board2[i][j + 1]) {
								move = 'L';
							}

							if (j != 0 && board1[i][j] == board2[i][j - 1]) {
								move = 'R';
							}

							String s = new StringBuilder().append(tempVal).append(' ').append(move).toString();
							col.add(s);
						}
					}
				}

				temp = temp.getParent();
			}
			Collections.reverse(col);
			//System.out.println("Depth is : " + col.size());
			for (int i = 0; i < col.size(); i++) {
				writer.write(col.get(i));
				writer.newLine();
			}
			System.out.println();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
