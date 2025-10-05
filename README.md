# CS611ObjectOrientedClassProjects2025Fa





# CS611-Assignment < 2 >
## < Dots and Boxes Game >
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

Game.java - This class manages the overall game loop.

DotsAndBoxesGame.java - A subclass of Game that implements the game logic for Dots and Boxes.

PuzzleGame.java - A subclass of Game that implements the game logic for the sliding puzzle.

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
Use javac src/*.java   to compile all the .java files in the src folder.
Then use java -cp src MainMenu to run the program.

## Input/Output Example
---------------------------------------------------------------------------
Please give us a full execution of what we should see on the screen. Label each text with input and output. For example:

```
    Output:
        Welcome to Board Games!

        Menu:
        1. Start sliding puzzle game
        2. Start dot and box game
        3. Exit
        Choose an option:

    Input:
        2

    Output:
        
        --- Setting up Dots and Boxes ---
        Enter name for Player 1:
    
    Input:
        P1

    Output:
        Enter name for Player 2:
    
    Input:
        P2

    Output:
        Enter the dimensions of the dot grid.
        Enter grid width and height (e.g., '4 3'): 
    
    Input:
        6 6

    Output:
        A 6x6 dot grid has been created. Let's play!
        .   .   .   .   .   .

        .   .   .   .   .   .

        .   .   .   .   .   .

        .   .   .   .   .   .

        .   .   .   .   .   .

        .   .   .   .   .   .


        It's P1's turn.
        Enter move (e.g., '0 0 H' or 'quit'):

    Input:
        3 3 V

    Output:
        .   .   .   .   .   .

        .   .   .   .   .   .

        .   .   .   .   .   .

        .   .   .   .   .   .
                    |
        .   .   .   .   .   .

        .   .   .   .   .   .


        It's P2's turn.
        Enter move (e.g., '0 0 H' or 'quit'):

    Input:
        3 4 V

    Output:
        .   .   .   .   .   .

        .   .   .   .   .   .

        .   .   .   .   .   .

        .   .   .   .   .   .
                    |   |
        .   .   .   .   .   .

        .   .   .   .   .   .


        It's P1's turn.
        Enter move (e.g., '0 0 H' or 'quit'):

    Input:
        3 3 H
    
    Output:
        .   .   .   .   .   .

        .   .   .   .   .   .

        .   .   .   .   .   .

        .   .   .   .---.   .
                    |   |
        .   .   .   .   .   .

        .   .   .   .   .   .


        It's P2's turn.
        Enter move (e.g., '0 0 H' or 'quit'):

    Input:
        You completed 1 box(es)! Go again.
        .   .   .   .   .   .

        .   .   .   .   .   .

        .   .   .   .   .   .

        .   .   .   .---.   .
                    | P |
        .   .   .   .---.   .

        .   .   .   .   .   .


        It's P2's turn.
        Enter move (e.g., '0 0 H' or 'quit'):

    Input:
        quit

    Output:
        Returning to the main menu...

        ------------------------------------


        Menu:
        1. Start sliding puzzle game
        2. Start dot and box game
        3. Exit
        Choose an option:

    Input:
        3

    Output:
        Goodbye!


```


