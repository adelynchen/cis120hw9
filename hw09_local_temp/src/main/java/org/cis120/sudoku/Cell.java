package org.cis120.sudoku;

import java.awt.*;
import javax.swing.*;

public class Cell extends JFormattedTextField {

    // Private instance variables
    private final int row;
    private final int col;
    private int correctNumber;
    private CellState status;

    // Public constants
    public static final Color PREFILLEDCOLOR = Color.LIGHT_GRAY;
    public static final Font FONT_NUMBERS =
            new Font("SansSerif", Font.BOLD, 25);

    // Constructor
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        setHorizontalAlignment(CENTER);
        setFont(FONT_NUMBERS);
        setBackground(Color.WHITE);
    }

    // Initialize cell
    public void init(int correctNumber, CellState status) {
        this.correctNumber = correctNumber;
        this.status = status;
        cellDisplay();
    }

    // Get number
    public int getCorrectNumber() {
        return correctNumber;
    }

    // Get status
    public CellState getStatus() {
        return status;
    }

    // Set status
    public void setStatus(CellState status) {
        this.status = status;
    }

    // Get row
    public int getRow() {
        return this.row;
    }

    // Get column
    public int getCol() {
        return this.col;
    }

    // Display the cell
    public void cellDisplay() {
        // If the cell can't be changed, display the #
        // and do not allow edits
        if (this.status == CellState.PREFILLED) {
            setText(String.valueOf(getCorrectNumber()));
            setEditable(false);
            setBackground(PREFILLEDCOLOR);
        }
        // If guessed incorrectly, make red
        if (this.status == CellState.GUESS_WRONG) {
            setBackground(Color.RED);
            // If number is guessed correctly, make cell green
        } else if (this.status == CellState.GUESS_RIGHT) {
            setBackground(Color.GREEN);
            // If number is guessed incorrectly, make cell text red
            // If the cell needs to be changed, let it be edited
            // Background initialized to unclicked - no change
        } else if (this.status == CellState.GUESS) {
            setEditable(true);
            setText("");
            setBackground(Color.WHITE);
        }


    }


}
