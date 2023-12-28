package fifteenpuzzle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * Reads the N-Puzzle from .txt file and creates a 2D array with it's contents
 * 
 * @author Sean Wotherspoon & Nolan Whittaker
 */


public class PuzzleBoardReader {

    private int[][] gameBoard;

    // Read the .txt file and create a 2D array representation
	public PuzzleBoardReader(String fileName) throws FileNotFoundException, IOException {

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            int size = br.read();
            int temp =  br.read();
            int temp1;
            
            if(temp != '\n') {
                size = 10*(size-'0') + (temp - '0');
            } else {
                size = size - '0';
            }

            gameBoard = new int[size][size];

            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    temp = br.read();
                    temp1 = br.read();
                    br.read();

                    if(temp == ' ') {
                        temp = '0';
                    }
                    if(temp1 == ' ') {
                        temp1 = '0';
                    }

                    gameBoard[row][col] = 10*(temp - '0') + (temp1 - '0');

                }
            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public int[][] getGameBoard() {
		return gameBoard;
	}

}
