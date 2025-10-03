import java.util.Scanner;

/**
 * The main class that runs the sliding puzzle game.
 * It extends the abstract Game class to fit into the main menu system.
 */
public class PuzzleGame extends Game {

    private Board.PuzzleBoard board;
    private Player player;
    private int boardSize;

    // Default constructor
    public PuzzleGame() {
    }

    // In PuzzleGame.java
 
    @Override
    protected String getGameInfo(Scanner scanner) {
        System.out.println("\n--- Setting up Sliding Puzzle ---");
        System.out.print("Enter your name: ");

        // The extra scanner.nextLine() was here. It has been removed.
        this.player = new Player(scanner.nextLine());

        // Get the desired board size from the user
        while (true) {
            System.out.print("Enter puzzle size (between 2 and 5): ");
            try {
                int size = Integer.parseInt(scanner.nextLine());
                if (size >= 2 && size <= 5) {
                    this.boardSize = size;
                    break;
                } else {
                    System.out.println("Invalid size. Please enter a number between 2 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return "Player and board size are set.";
    }

    @Override
    protected void initializeBoard() {
        this.board = new Board.PuzzleBoard(this.boardSize);
        System.out.println("Okay " + player.getName() + ", here’s your puzzle:");
    }

    @Override
    protected void runGame(Scanner scanner) {
        while (!isGameOver()) {
            printBoard();
            System.out.print(player.getName() + ", which tile do you want to slide? (or type 'quit'): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("quit")) {
                quitToMainMenu();
                return; // Exit the game loop to return to the menu
            }

            try {
                int tile = Integer.parseInt(input);
                if (!board.slideTile(tile)) {
                    System.out.println("Invalid move! That tile is not adjacent to the empty space.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        // Game has ended (puzzle is solved)
        printBoard(); // Show the final solved board
        System.out.println("Congratulations, " + player.getName() + "! You solved the puzzle!");
    }

    @Override
    protected boolean isGameOver() {
        return board.isSolved();
    }

    @Override
    protected void printBoard() {
        System.out.println(board.getBoardAsString());
    }

    @Override
    protected void quitToMainMenu() {
        System.out.println("Returning to the main menu...");
    }
}