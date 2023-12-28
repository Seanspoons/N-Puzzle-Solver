package fifteenpuzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class Solver {
    public static void main(String[] args) {
//        System.out.println("number of arguments: " + args.length);
//        for (int i = 0; i < args.length; i++) {
//            System.out.println(args[i]);
//        }

        if (args.length < 2) {
            System.out.println("File names are not specified");
            System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
            return;
        }


        // TODO

        // Input file
        String input = args[0];
        String output = args[1];

        try {

            PuzzleBoardReader puzzleReader1 = new PuzzleBoardReader(input);
            int[][] gameBoard1 = puzzleReader1.getGameBoard();
            PuzzleBoard puzzleBoard1 = new PuzzleBoard(gameBoard1);
            PuzzleSolver solver = new PuzzleSolver(puzzleBoard1, puzzleBoard1.getSize());
            File newFile = new File(output);
            newFile.createNewFile();
            PuzzleBoardWriter solutionWriter = new PuzzleBoardWriter(solver.getSolutionBoard(), newFile.getName());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
