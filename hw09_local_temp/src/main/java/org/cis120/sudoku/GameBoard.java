package org.cis120.sudoku;

/*
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class instantiates a TicTacToe object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Sudoku sdku; // model for the game
    private JOptionPane frame;
    private JLabel display; // current status text
    private JLabel isWin = new JLabel();
    private LinkedList<Cell[][]> boardStates = new LinkedList<>();
    private LinkedList<Cell> cellMoves;
    private int level;

    // Game constants
    public static final int BOARD_WIDTH = 630;
    public static final int BOARD_HEIGHT = 630;


    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit, int level) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new GridLayout(9, 9));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        setSize(BOARD_WIDTH,BOARD_HEIGHT);
        this.level = level;
        this.cellMoves = new LinkedList<>();
        this.sdku = new Sudoku(this.level); // initializes model for the game
        isWin.setText("Game in progress");
        display = statusInit; // initializes the status JLabel
        NumberListener listener = new NumberListener();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                add(sdku.getCells()[i][j]);
                if (sdku.getCells()[i][j].getStatus() == CellState.GUESS) {
                    sdku.getCells()[i][j].addActionListener(listener);
                }
            }
        }

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();

                // updates the model given the coordinates of the mouseclick
//                sdku.playTurn(p.x / 100, p.y / 100);

                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void restart() {
        sdku.restart();
        isWin.setText("Game in progress");
        display.setText("Mistakes: " + sdku.mistakes + " | " + isWin.getText());
        cellMoves.clear();
        updateStatus();

        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void newGame() {
        removeAll();
        String[] levels = {"Easy", "Medium", "Hard"};
        int chosenLevel = JOptionPane.showOptionDialog(frame, "Choose a level", "Level",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, levels, levels[0]);
        this.sdku = new Sudoku(chosenLevel);
        NumberListener listener = new NumberListener();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                add(sdku.getCells()[i][j]);
                if (sdku.getCells()[i][j].getStatus() == CellState.GUESS) {
                    sdku.getCells()[i][j].addActionListener(listener);
                }
                sdku.getCells()[i][j].cellDisplay();
            }
        }
        revalidate();

        repaint();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        display.setText("Mistakes: " + sdku.mistakes + " | " + isWin.getText());
    }

    public void undo() {
        try {
            cellMoves.getLast().setStatus(CellState.GUESS);
            cellMoves.getLast().cellDisplay();
            cellMoves.removeLast();
        } catch (NoSuchElementException e) {
            JOptionPane.showMessageDialog(frame, "Nothing to undo");
        }

        repaint();
    }

    /**
     * Draws the game board.
     * 
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

//         Draws board grid
        for (int i = 0; i <= 9; i++) {
            g.drawLine(BOARD_WIDTH * i / 9, 0,
                    BOARD_WIDTH * i / 9, BOARD_HEIGHT);
            if (i % 3 == 0) {
                g.drawLine(BOARD_WIDTH * i / 9 + 1, 0,
                        BOARD_WIDTH * i / 9 + 1, BOARD_HEIGHT);
                g.drawLine(BOARD_WIDTH * i / 9 - 1, 0,
                        BOARD_WIDTH * i / 9 - 1, BOARD_HEIGHT);
            }
        }

        for (int i = 0; i <= 9; i++) {
            g.drawLine(0, BOARD_HEIGHT * i / 9,
                    BOARD_WIDTH, BOARD_HEIGHT * i / 9);
            if (i % 3 == 0) {
                g.drawLine(0, BOARD_HEIGHT * i / 9 + 1,
                        BOARD_WIDTH, BOARD_HEIGHT * i / 9 + 1);
                g.drawLine(0, BOARD_HEIGHT * i / 9 - 1,
                        BOARD_WIDTH, BOARD_HEIGHT * i / 9 - 1);
            }
        }


    }

    public GameState getGameState() {
        return sdku.checkStatus();
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }

    private class NumberListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Cell cell = (Cell) ae.getSource();
            try {

                if (cell.getText().equals("")) {
                    cell.setStatus(CellState.GUESS);
                }
                int numberIn = Integer.parseInt(cell.getText());

                if (numberIn < 1 || numberIn > 9) {
                    cell.setStatus(CellState.GUESS);
                } else if (numberIn == cell.getCorrectNumber()) {
                    // If the number is right, change the status
                    cell.setStatus(CellState.GUESS_RIGHT);
                } else {
                    cell.setStatus(CellState.GUESS_WRONG);
                }
                cellMoves.add(cell);
                cell.cellDisplay();

                // Keep track of the # of mistakes
                sdku.checkMistakes();

                if (sdku.checkStatus() == GameState.WON) {
                    isWin.setText("You win!");
                    JOptionPane.showMessageDialog(frame, "You win!");
                } else if (sdku.checkStatus() == GameState.LOST) {
                    isWin.setText("You lost :(");
                    cellMoves.clear();
                    JOptionPane.showMessageDialog(frame, "You lost :(");
                } else if (sdku.checkStatus() == GameState.IN_PROGRESS) {
                    isWin.setText("Game in progress");
                }
                updateStatus();
                boardStates.add(sdku.getCells());

            } catch (NumberFormatException e) {
                cell.setText("");
                cell.setStatus(CellState.GUESS);
                cell.cellDisplay();
            }

        }

        public Collection<Cell[][]> getBoardStates() {
            return boardStates;
        }
    }
}
