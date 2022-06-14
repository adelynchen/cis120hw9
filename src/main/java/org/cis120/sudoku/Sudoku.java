package org.cis120.sudoku;

/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

/**
 * This class is a model for TicTacToe.
 * 
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games. We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec36.pdf
 * 
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 * 
 * Run this file to see the main method play a game of TicTacToe,
 * visualized with Strings printed to the console.
 */
public class Sudoku {

    private Cell[][] board = new Cell[9][9];
    int mistakes;
    private GameState gameStatus;
    private Puzzle puzzle;
    private int level = 0;

    /**
     * Constructor sets up game state.
     */
    public Sudoku(int level) {
        newGame(level);
    }

    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play in a location that is
     * taken or after the game has ended. If the turn is successful and the game
     * has not ended, the player is changed. If the turn is unsuccessful or the
     * game has ended, the player is not changed.
     *
     * @param c column to play in
     * @param r row to play in
     * @return whether the turn was successful
     */
//    public boolean playTurn(int r, int c) {
//        return board[r][c].getCorrectNumber() == 0 && (gameStatus == GameStatus.IN_PROGRESS);
//    }


    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                System.out.print(this.board[i][j]);
                if (j < 2) {
                    System.out.print(" | ");
                }
            }
            if (i < 2) {
                System.out.println("\n---------");
            }
        }
    }

    /**
     * restart allows user to restart the level
     */
    public void restart() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!(this.puzzle.getIsPrefilled()[i][j])) {
                    this.board[i][j].init(this.puzzle.getNumbers()[i][j], CellState.GUESS);
                    this.board[i][j].setText("");
                }
            }
        }
        mistakes = 0;
        this.gameStatus = GameState.IN_PROGRESS;
    }

    public void newGame(int level) {
        this.level = level;
        this.puzzle = new Puzzle(level);
        this.board = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.board[i][j] = new Cell(i, j);
                CellState cellState;
                if (this.puzzle.getIsPrefilled()[i][j]) {
                    cellState = CellState.PREFILLED;
                } else {
                    cellState = CellState.GUESS;
                }
                this.board[i][j].init(this.puzzle.getNumbers()[i][j], cellState);
                System.out.println(this.board[i][j].getText() + " | " +
                        this.board[i][j].getCorrectNumber());
//                this.board[i][j].cellDisplay();
            }
        }
        restart();
        mistakes = 0;
        this.gameStatus = GameState.IN_PROGRESS;
    }


    public Cell[][] getCells() {
        return this.board;
    }

    public Puzzle getPuzzle() {
        return this.puzzle;
    }

    /**
     * checkWinner checks whether the game has reached a win condition.
     * checkWinner only looks for horizontal wins.
     *
     * @return 0 if nobody has won yet, 1 if player 1 has won, and 2 if player 2
     *         has won, 3 if the game hits stalemate
     */
    public GameState checkStatus() {
        if (mistakes >= 5) {
            this.gameStatus = GameState.LOST;
            for (Cell[] cells : this.board) {
                for (int j = 0; j < this.board.length; j++) {
                    cells[j].setEditable(false);
                }
            }
            return this.gameStatus;
        } else {
            for (Cell[] cells : this.board) {
                for (int j = 0; j < this.board.length; j++) {
                    if (cells[j].getStatus() == CellState.GUESS
                            || cells[j].getStatus() == CellState.GUESS_WRONG) {
                        this.gameStatus = GameState.IN_PROGRESS;
                        return this.gameStatus;
                    }
                }
            }
        }
        this.gameStatus = GameState.WON;
        return this.gameStatus;
    }

    public void checkMistakes() {
        for (Cell[] cells : this.board) {
            for (int j = 0; j < this.board.length; j++) {
                if (cells[j].getStatus() == CellState.GUESS_WRONG) {
                    mistakes ++;
                }
            }
        }
    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        Sudoku s = new Sudoku(0);
    }
}
