# CS611ObjectOrientedClassProjects2025Fa





# CS611-Assignment < 3 >
## < Quoridor Game >
---------------------------------------------------------------------------
- Name1: placeholder
- Email1: placeholder
- Student ID1: placeholder
- Name2: placeholder
- Email2: placeholder
- Student ID2: placeholder

## Files
---------------------------------------------------------------------------

This section should be all of the source code files that have a .java extension. You should also include a brief description of what the class does.

Board.java - This class represents the game board and contains methods to initialize the board, display it, and check for completed boxes.

DotsAndBoxesBoard.java - A subclass of Board that implements specific functionalities for the Dots and Boxes game.

PuzzleBoard.java - A subclass of Board that implements specific functionalities for the sliding puzzle game.

QuoridorBoard.java - A subclass of Board that implements specific functionalities for Quoridor.

Game.java - This class manages the overall game loop.

GameStats.java - This class tracks and displays game statistics for each player.

DotsAndBoxesGame.java - A subclass of Game that implements the game logic for Dots and Boxes.

PuzzleGame.java - A subclass of Game that implements the game logic for the sliding puzzle.

QuoridorGame.java - A subclass of Game that implements the game logic for Quoridor.

MainMenu.java - This class provides the main menu interface for selecting between different games.

Piece.java - This class represents a piece on the board, including its position and state.

Player.java - This class represents a player in the game, including their name.

Tile.java - This class represents a tile, we decided to not use it.


## Notes
---------------------------------------------------------------------------
Please explain the cool features of your program. Anything that you feel like you did a good job at or were creative about, explain it in bullets here. Additionally, any design decisions should be made here.

We have a main menu that allows users to select between two games. 

Our game also do not throw exceptions for invalid inputs, instead it prompts the user to enter a valid input again.

We have a quit button in both main menu and in the games.


## How to compile and run
---------------------------------------------------------------------------
Your directions on how to run the code. Make sure to be as thorough as possible!

Navigate to the directory "CS611ObjectOrientedClassProjects2025Fa" after unzipping the files
Use javac "src/*.java"  to compile all the .java files in the src folder.
Then use "java -cp src MainMenu" to run the program.

## Input/Output Example
---------------------------------------------------------------------------
Please give us a full execution of what we should see on the screen. Label each text with input and output. For example:

```
    Output:
        Welcome to Board Games!

        Menu:
        1. Start sliding puzzle game
        2. Start dot and box game
        3. Start Quoridor game
        4. Exit
        Choose an option: 

    Input:
        3

    Output:
        
        --- Setting up Quoridor ---
        Quoridor is a 2-player game on a 9x9 board.
        Player 1 starts at 'e1' (bottom) and wins by reaching row 9.
        Player 2 starts at 'e9' (top) and wins by reaching row 1.
        Each player has 10 walls to place.

        Movement: Use 'up', 'down', 'left', 'right' to move your pawn.
        Walls: Use 'wall e3 h' (horizontal) or 'wall e3 v' (vertical) to place walls.
        Enter name for Player 1:
    
    Input:
        P1

    Output:
        Enter name for Player 2:
    
    Input:
        P2

    Output:
The board is set. Let's play!
   a   b   c   d   e   f   g   h   i
  +---+---+---+---+---+---+---+---+---+
9 |   |   |   |   | 2 |   |   |   |   | 9
  +---+---+---+---+---+---+---+---+---+ 8
8 |   |   |   |   |   |   |   |   |   | 8
  +---+---+---+---+---+---+---+---+---+ 7
7 |   |   |   |   |   |   |   |   |   | 7
  +---+---+---+---+---+---+---+---+---+ 6
6 |   |   |   |   |   |   |   |   |   | 6
  +---+---+---+---+---+---+---+---+---+ 5
5 |   |   |   |   |   |   |   |   |   | 5
  +---+---+---+---+---+---+---+---+---+ 4
4 |   |   |   |   |   |   |   |   |   | 4
  +---+---+---+---+---+---+---+---+---+ 3
3 |   |   |   |   |   |   |   |   |   | 3
  +---+---+---+---+---+---+---+---+---+ 2
2 |   |   |   |   |   |   |   |   |   | 2
  +---+---+---+---+---+---+---+---+---+ 1
1 |   |   |   |   | 1 |   |   |   |   | 1
  +---+---+---+---+---+---+---+---+---+
   a   b   c   d   e   f   g   h   i


It's P1's turn (Team 1).
Walls remaining: 10
Enter command (up/down/left/right, 'wall e3 h', or 'quit'):
    
    Input:
        up

    Output:
   a   b   c   d   e   f   g   h   i
  +---+---+---+---+---+---+---+---+---+
9 |   |   |   |   | 2 |   |   |   |   | 9
  +---+---+---+---+---+---+---+---+---+ 8
8 |   |   |   |   |   |   |   |   |   | 8
  +---+---+---+---+---+---+---+---+---+ 7
7 |   |   |   |   |   |   |   |   |   | 7
  +---+---+---+---+---+---+---+---+---+ 6
6 |   |   |   |   |   |   |   |   |   | 6
  +---+---+---+---+---+---+---+---+---+ 5
5 |   |   |   |   |   |   |   |   |   | 5
  +---+---+---+---+---+---+---+---+---+ 4
4 |   |   |   |   |   |   |   |   |   | 4
  +---+---+---+---+---+---+---+---+---+ 3
3 |   |   |   |   |   |   |   |   |   | 3
  +---+---+---+---+---+---+---+---+---+ 2
2 |   |   |   |   | 1 |   |   |   |   | 2
  +---+---+---+---+---+---+---+---+---+ 1
1 |   |   |   |   |   |   |   |   |   | 1
  +---+---+---+---+---+---+---+---+---+
   a   b   c   d   e   f   g   h   i


It's P2's turn (Team 2).
Walls remaining: 10
Enter command (up/down/left/right, 'wall e3 h', or 'quit'):

    Input:
        down

    Output:
   a   b   c   d   e   f   g   h   i
  +---+---+---+---+---+---+---+---+---+
9 |   |   |   |   |   |   |   |   |   | 9
  +---+---+---+---+---+---+---+---+---+ 8
8 |   |   |   |   | 2 |   |   |   |   | 8
  +---+---+---+---+---+---+---+---+---+ 7
7 |   |   |   |   |   |   |   |   |   | 7
  +---+---+---+---+---+---+---+---+---+ 6
6 |   |   |   |   |   |   |   |   |   | 6
  +---+---+---+---+---+---+---+---+---+ 5
5 |   |   |   |   |   |   |   |   |   | 5
  +---+---+---+---+---+---+---+---+---+ 4
4 |   |   |   |   |   |   |   |   |   | 4
  +---+---+---+---+---+---+---+---+---+ 3
3 |   |   |   |   |   |   |   |   |   | 3
  +---+---+---+---+---+---+---+---+---+ 2
2 |   |   |   |   | 1 |   |   |   |   | 2
  +---+---+---+---+---+---+---+---+---+ 1
1 |   |   |   |   |   |   |   |   |   | 1
  +---+---+---+---+---+---+---+---+---+
   a   b   c   d   e   f   g   h   i


It's P1's turn (Team 1).
Walls remaining: 10
Enter command (up/down/left/right, 'wall e3 h', or 'quit'):

    Input:
        wall e3 h

    Output:
   a   b   c   d   e   f   g   h   i
  +---+---+---+---+---+---+---+---+---+
9 |   |   |   |   |   |   |   |   |   | 9
  +---+---+---+---+---+---+---+---+---+ 8
8 |   |   |   |   | 2 |   |   |   |   | 8
  +---+---+---+---+---+---+---+---+---+ 7
7 |   |   |   |   |   |   |   |   |   | 7
  +---+---+---+---+---+---+---+---+---+ 6
6 |   |   |   |   |   |   |   |   |   | 6
  +---+---+---+---+---+---+---+---+---+ 5
5 |   |   |   |   |   |   |   |   |   | 5
  +---+---+---+---+---+---+---+---+---+ 4
4 |   |   |   |   |   |   |   |   |   | 4
  +---+---+---+---+---+---+---+---+---+ 3
3 |   |   |   |   |   |   |   |   |   | 3
  +---+---+---+---+===+===+---+---+---+ 2
2 |   |   |   |   | 1 |   |   |   |   | 2
  +---+---+---+---+---+---+---+---+---+ 1
1 |   |   |   |   |   |   |   |   |   | 1
  +---+---+---+---+---+---+---+---+---+
   a   b   c   d   e   f   g   h   i


It's P2's turn (Team 2).
Walls remaining: 10
Enter command (up/down/left/right, 'wall e3 h', or 'quit'):

    Input:
        quit
    
    Output:
Returning to the main menu...

------------------------------------


Menu:
1. Start sliding puzzle game
2. Start dot and box game
3. Start Quoridor game
4. Exit
Choose an option:

    Input:
        4

    Output:
=== GAME STATISTICS ===

P1's Statistics:
----------------------------------------
Game                  Wins  Losses   Total
----------------------------------------
Quoridor                 0       1       1
----------------------------------------
TOTAL                    0       1       1

P2's Statistics:
----------------------------------------
Game                  Wins  Losses   Total
----------------------------------------
Quoridor                 0       1       1
----------------------------------------
TOTAL                    0       1       1

========================================
Goodbye!
   

```


