=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: adelync
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D arrays: I represented the Sudoku board and puzzle as 2D arrays, which
  helped with the display. A Sudoku board by nature is a 9x9 grid, which is best
  represented by a 2D array.

  2. Recursion: I didn't include this one originally in my project proposal
  because it didn't occur to me that I would have to use it, but ultimately
  in order to generate a valid Sudoku puzzle for which the invariants are
  satisfied, I needed to use recursion to fill in the boxes. The way my
  puzzle generation works is that I first randomly populate diagonal 3x3
  boxes within the 9x9 grid, and then recursively attempt to fill the rest
  depending on whether a number has already been used; if all numbers have
  been used in the row, column, or box, the puzzle has no solution, so the
  recursion allows the program to move backwards and undo some actions to
  try again. It's impossible for the program to know immediately what
  numbers can and cannot work without recursion because if it iterates
  through random numbers 1-9 it's possible that there is no solution.

  3. Collections: I originally wanted to use collections for user mistakes
  but received TA feedback that those could just be stored as an int; instead,
  I attempted to use collections to save player moves so that I could add an
  undo button. I couldn't quite figure out how to make this work, but I wanted
  to store the state of the board after each input to the JTextFields in the cells
  and return cells to previous states if the undo button was clicked. That didn't
  really match my implementation though, so I ended up just adding the cells to
  a LinkedList and only allowing the undo button to reset cells (as opposed to
  also being able to undo the erasure of content in a cell). I think this is the
  best concept for the undo button because there needs to be a data structure that
  keeps track of what the player does; otherwise, the move data will be lost.

  4. I didn't quite get to finishing it, but I wanted to use JUnit testing to
  check whether the game state was being correctly updated (as I received
  TA feedback on this). This would be the best concept for this because it
  would help demonstrate modularity and that the game mechanisms don't
  depend on GUI components.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  The Cell class represents each of the cells in the 9x9 Sudoku grid. This
  class initializes each of the boxes on the board and allows text input.
  Second, it helps make the distinction between pre-filled and guess-able
  cells. It also manages the way the background color looks, which helps prompt the
  user during the game (letting them know if their answer is right or wrong,
  etc).

  Along with the Cell class, there is an enumeration called CellState that I
  defined in order to make it easier to keep track of whether a cell is
  prefilled, needs user input, is guessed correctly, or guessed incorrectly.
  This was easier than using a lot of booleans.

  The Puzzle class is used to generate the puzzle that is then mapped to the
  board, i.e. the sequence of numbers that represents the solution. Without
  this class, there is no playable game.

  The GameState enumeration helps keep track of whether the game is in progress,
  or if it has been won or lost (i.e. if the user made too many mistakes).


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  Trying to figure out how to generate the puzzles was arguably the hardest
  part. I tried two different strategies that I ultimately had to combine to
  get to my final solution. At first, I started with the random generation for
  the diagonal boxes, but then I was attempting to randomly populate the rest
  of the boxes, which meant that if the program made a "mistake" and there was
  no solution, I would get an unsolvable puzzle with a bunch of 0s in the array.
  The second thing I tried was to use recursion on the entire 9x9 grid, which
  kept leading to Stack Overflow errors. Finally, I combined the diagonal box
  fill with the recursive solving.

  I also had a lot of trouble figuring out how to validate the text input for
  cells that need to be guessed.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

   I feel like I could have improved private state encapsulation. When I was
   first implementing everything I sometimes forgot that with private variables
   you just have to write a get method in order to access them, so I wasn't
   making everything private because I was trying to access them by name and
   not by a method. If I could, I would have made more variables private and
   better kept track of get and set methods. I also would have wanted the state
   of the game to be updated as a board, which would probably involve iteration
   over the cells a lot.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

  JOptionPane
    https://www.tutorialspoint.com/what-are-the-different-types-of-joptionpane-dialogs-in-java
  Random number generation https://www.geeksforgeeks.org/java-util-random-nextint-java/
  Easybrain Sudoku app (referenced for feature implementation ideas)
  Adding and removing components
    https://stackoverflow.com/questions/8608902/the-correct-way-to-swap-a-component-in-java
