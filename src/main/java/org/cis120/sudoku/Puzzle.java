package org.cis120.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Puzzle {

    // Instance variables
    private int[][] solution;
    private final boolean[][] isPrefilled;

    // Constructor
    public Puzzle(int level) {
        super();
        generate();
        finishPuzzle();
        this.isPrefilled = generatePrefilled(level);
    }

    // Check if puzzle satisfies invariants
    public boolean checkValidity() {
        // Numbers must be between 0 and 9
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.solution[i][j] < 1 || this.solution[i][j] > 9) {
                    return false;
                }
            }
        }

        // Each 3x3 box should have no duplicates
        ArrayList<Integer> boxList = new ArrayList<>();
        ArrayList<Integer> intsInOrder = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            intsInOrder.add(i);
        }

        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                if (!validBox(this.solution, i, j)) {
                    return false;
                }
            }
        }

        // Each row should have no duplicates
        ArrayList<Integer> rowList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                rowList.add(this.solution[i][j]);
            }
            Collections.sort(rowList);
            if (!rowList.equals(intsInOrder)) {
                return false;
            }
            rowList.clear();
        }

        // Each column should have no duplicates
        ArrayList<Integer> colList = new ArrayList<>();
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                colList.add(this.solution[i][j]);
            }
            Collections.sort(colList);
            if (!colList.equals(intsInOrder)) {
                return false;
            }
            colList.clear();
        }
        return true;
    }

    // Create new puzzle
    public void generate() {

        this.solution = new int[9][9];
        // Initialize a 9x9 grid
        int[][] puzzle = new int[9][9];

        // Populate the diagonal boxes since we don't need to check
        // for row and column existence for those yet
        populateFirstBoxes(puzzle, 0, 0);
        populateFirstBoxes(puzzle, 3, 3);
        populateFirstBoxes(puzzle, 6, 6);

        this.solution = puzzle;
    }

    // Populate 3x3 box with random integers 1-9
    public void populateFirstBoxes(int[][] box, int rowStart, int colStart) {
        // Shuffled List
        ArrayList<Integer> numbers = randomize();

        int count = 0;
        for (int i = rowStart; i < rowStart + 3; i++) {
            for (int j = colStart; j < colStart + 3; j++) {
                box[i][j] = numbers.get(count);
                count++;
            }
        }
    }

    public boolean usedInRow(int[][] array, int number, int row) {
        for (int i = 0; i < 9; i++) {
            if (array[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    public boolean usedInColumn(int[][] array, int number, int col) {
        for (int i = 0; i < 9; i++) {
            if (array[i][col] == number) {
                return true;
            }
        }
        return false;
    }

    public boolean usedInBox(int[][] array, int number, int row, int col) {
        int rowLowerBound;
        int colLowerBound;
        if (row < 3) {
            rowLowerBound = 0;
        } else if (row < 6) {
            rowLowerBound = 3;
        } else {
            rowLowerBound = 6;
        }
        if (col < 3) {
            colLowerBound = 0;
        } else if (col < 6) {
            colLowerBound = 3;
        } else {
            colLowerBound = 6;
        }
        for (int rowIndex = rowLowerBound; rowIndex < rowLowerBound + 3; rowIndex++) {
            for (int colIndex = colLowerBound; colIndex < colLowerBound + 3; colIndex++) {
                if (array[rowIndex][colIndex] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Integer> randomize() {
        // List of integers 1-9
        ArrayList<Integer> intsInOrder = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            intsInOrder.add(i);
        }
        // List to shuffle
        ArrayList<Integer> numbers = new ArrayList<>(intsInOrder);
        // Shuffle the list
        Collections.shuffle(numbers);
        return numbers;
    }


    public boolean finishPuzzle() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9 ; j++) {
                if (this.solution[i][j] == 0) {
                    for (int k = 1; k <= 9; k++) {
                        if (!(usedInColumn(this.solution, k, j))
                                && !(usedInRow(this.solution, k, i))
                                && !(usedInBox(this.solution, k, i, j))) {
                            this.solution[i][j] = k;
                            if (finishPuzzle()) {
                                return true;
                            } else {
                                this.solution[i][j] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    // Get puzzle array
    public int[][] getNumbers() {
        // Copy for encapsulation
//        int[][] solutionCopy = new int[9][9];
//        for (int i = 0; i < 9; i++) {
//            System.arraycopy(this.solution[i], 0,
//                    solutionCopy[i], 0, 9);
//        }
//        return solutionCopy;
        return this.solution;
    }

    public boolean[][] generatePrefilled(int level) {
        boolean[][] prefill = new boolean[9][9];
        int randomNumber;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Random random = new Random();
                randomNumber = random.nextInt(100);
                if (level == 0) {
                    prefill[i][j] = randomNumber < 65;
                } else if (level == 1) {
                    prefill[i][j] = randomNumber < 50;
                } else {
                    prefill[i][j] = randomNumber < 35;
                }

            }
        }
        return prefill;
    }

    public boolean[][] getIsPrefilled() {
        return isPrefilled;
    }

    public boolean validBox(int[][] array, int rowStart, int colStart) {
        ArrayList<Integer> intsInOrder = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            intsInOrder.add(i);
        }
        ArrayList<Integer> box = new ArrayList<>();
        for (int i = rowStart; i < rowStart + 3; i++) {
            for (int j = colStart; j < colStart + 3; j++) {
                box.add(array[i][j]);
            }
        }
        Collections.sort(box);
        return (box.equals(intsInOrder));
    }

}
