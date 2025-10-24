import java.util.Scanner;

/**
 * Orchestrates the gameplay for the sliding puzzle game.
 * This class handles the player and board setup, runs the main game loop,
 * processes user input for sliding tiles, and displays a message upon completion.
 */
public class PuzzleGame extends Game {

    private PuzzleBoard board;
    private Player player;
    private int boardWidth;
    private int boardHeight;

    public PuzzleGame() {
    }

    @Override
    protected String getGameInfo(Scanner scanner) {
        System.out.println("\n--- Setting up Sliding Puzzle ---");
        System.out.print("Enter your name: ");
        this.player = new Player(scanner.nextLine(), 1); // Default to Team 1

        while (true) {
            System.out.print("Enter puzzle width and height (e.g., '4 3'): ");
            try {
                String[] dimensionParts = scanner.nextLine().split(" ");
                if (dimensionParts.length != 2) {
                    System.out.println("Invalid format. Please enter two numbers separated by a space.");
                    continue;
                }

                int desiredWidth = Integer.parseInt(dimensionParts[0]);
                int desiredHeight = Integer.parseInt(dimensionParts[1]);

                if (desiredWidth >= 2 && desiredWidth <= 10 && desiredHeight >= 2 && desiredHeight <= 10) {
                    this.boardWidth = desiredWidth;
                    this.boardHeight = desiredHeight;
                    break;
                } else {
                    System.out.println("Invalid dimensions. Both width and height must be between 1 and 10.");
                }
            } catch (NumberFormatException exception) {
                System.out.println("Invalid input. Please enter numbers only.");
            }
        }
        return "Player and board dimensions are set.";
    }

    @Override
    protected void initializeBoard() {
        this.board = new PuzzleBoard(this.boardWidth, this.boardHeight);
        System.out.println("Okay " + player.getName() + ", here's your " + boardWidth + "x" + boardHeight + " puzzle:");
    }

    @Override
    protected void runGame(Scanner scanner, GameStats gameStats) {
        while (!isGameOver()) {
            printBoard();
            System.out.print(player.getName() + ", which tile do you want to slide? (or type 'quit'): ");
            String userInput = scanner.nextLine().trim();

            if (userInput.equalsIgnoreCase("quit")) {
                quitToMainMenu(gameStats);
                return;
            }

            try {
                int tileValueToSlide = Integer.parseInt(userInput);
                if (!board.slideTile(tileValueToSlide)) {
                    System.out.println("Invalid move! That tile is not adjacent to the empty space.");
                }
            } catch (NumberFormatException exception) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        printBoard();
        System.out.println("Congratulations, " + player.getName() + "! You solved the puzzle!");
        gameStats.recordWin(player.getName(), "Puzzle");
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
    protected void quitToMainMenu(GameStats gameStats) {
        System.out.println("Returning to the main menu...");
        gameStats.recordQuit(player.getName(), "Puzzle");
    }
}