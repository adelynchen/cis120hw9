package org.cis120.sudoku;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SudokuTest {
    private Sudoku sudoku;

    public SudokuTest() {
    }

    @BeforeEach
    public void setUp() {
        this.sudoku = new Sudoku(0);
    }

    @Test
    public void testValidityOfPuzzle() {
        assertTrue(sudoku.getPuzzle().checkValidity());
    }




}
