import java.util.Scanner;

/**
 * The main class that runs the sliding puzzle game.
 * It extends the abstract Game class to fit into the main menu system.
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
        this.player = new Player(scanner.nextLine());

        while (true) {
            System.out.print("Enter puzzle width and height (e.g., '4 3'): ");
            try {
                String[] parts = scanner.nextLine().split(" ");
                if (parts.length != 2) {
                    System.out.println("Invalid format. Please enter two numbers separated by a space.");
                    continue;
                }

                int width = Integer.parseInt(parts[0]);
                int height = Integer.parseInt(parts[1]);

                if (width >= 1 && width <= 10 && height >= 1 && height <= 10) {
                    this.boardWidth = width;
                    this.boardHeight = height;
                    break; // Exit the loop if input is valid
                } else {
                    System.out.println("Invalid dimensions. Both width and height must be between 5 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numbers only.");
            }
        }
        return "Player and board dimensions are set.";
    }

    @Override
    protected void initializeBoard() {
        // CHANGED: Pass both width and height to the constructor
        this.board = new PuzzleBoard(this.boardWidth, this.boardHeight);
        System.out.println("Okay " + player.getName() + ", here’s your " + boardWidth + "x" + boardHeight + " puzzle:");
    }

    @Override
    protected void runGame(Scanner scanner) {
        while (!isGameOver()) {
            printBoard();
            System.out.print(player.getName() + ", which tile do you want to slide? (or type 'quit'): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("quit")) {
                quitToMainMenu();
                return;
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

        printBoard();
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