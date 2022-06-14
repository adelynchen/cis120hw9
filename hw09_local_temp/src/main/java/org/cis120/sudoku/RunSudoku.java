package org.cis120.sudoku;

/*
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard. The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a TicTacToe object to serve as the game's model.
 */
public class RunSudoku implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Sudoku");
        frame.setLocation(0, 0);
        frame.setResizable(false);

        String[] levels = {"Easy", "Medium", "Hard"};
        int chosenLevel = JOptionPane.showOptionDialog(frame, "Choose a level", "Level",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, levels, levels[0]);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);


        // Game board
        GameBoard board = new GameBoard(status, chosenLevel);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton restart = new JButton("Restart");
        restart.addActionListener(e -> board.restart());
        control_panel.add(restart);

        // Instructions
        String instructionBody =
                "In Sudoku, you are given a 9x9 grid. Each number 1-9 must appear once and only\n" +
                 "once in any row, column, or inner 3x3 box that it is in (defined by the thick\n" +
                 "black borders). Solve the puzzle with less than 5 mistakes to win! \n" +
                "Click on a cell, use your keyboard keys to enter a number, and press Enter to \n" +
                        "see if you've gotten the right answer.";

        // Instructions button
        final JButton displayInstructions = new JButton("Instructions");
        displayInstructions.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, instructionBody));
        control_panel.add(displayInstructions);

        // New game
        final JButton newGame = new JButton("New Game");
        newGame.addActionListener(e -> board.newGame());
        control_panel.add(newGame);

        // Undo
        final JButton undo = new JButton("Undo");
        undo.addActionListener(e -> board.undo());
        control_panel.add(undo);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.restart();
    }
}