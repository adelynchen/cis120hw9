package org.cis120.sudoku;

public enum CellState {
    PREFILLED, // Number is given
    GUESS, // Number needs to be guessed
    GUESS_RIGHT, // Number is guessed correctly
    GUESS_WRONG // Number is guessed incorrectly
}
