import java.util.Scanner;

/**
 * Orchestrates the gameplay for the Dots and Boxes game.
 * This class handles player setup, board initialization, the main game loop,
 * processing user input, and determining the winner.
 */
public class DotsAndBoxesGame extends Game {

    private DotsAndBoxesBoard board;
    private Player playerOne;
    private Player playerTwo;
    private Player currentPlayer;
    private int gridWidth;
    private int gridHeight;

    public DotsAndBoxesGame() {
    }

    @Override
    protected String getGameInfo(Scanner scanner) {
        System.out.println("\n--- Setting up Dots and Boxes ---");

        System.out.print("Enter name for Player 1: ");
        this.playerOne = new Player(scanner.nextLine(), 1); // Team 1

        System.out.print("Enter name for Player 2: ");
        this.playerTwo = new Player(scanner.nextLine(), 2); // Team 2

        System.out.println("Enter the dimensions of the dot grid.");
        while (true) {
            System.out.print("Enter grid width and height (e.g., '4 3'): ");
            try {
                String[] dimensionParts = scanner.nextLine().split(" ");
                if (dimensionParts.length != 2) {
                    System.out.println("Invalid format. Please enter two numbers separated by a space.");
                    continue;
                }
                int desiredWidth = Integer.parseInt(dimensionParts[0]);
                int desiredHeight = Integer.parseInt(dimensionParts[1]);
                if (desiredWidth >= 1 && desiredWidth <= 10 && desiredHeight >= 1 && desiredHeight <= 10) {
                    this.gridWidth = desiredWidth;
                    this.gridHeight = desiredHeight;
                    break;
                } else {
                    System.out.println("Invalid dimensions. Both width and height must be between 1 and 10.");
                }
            } catch (NumberFormatException exception) {
                System.out.println("Invalid input. Please enter numbers only.");
            }
        }
        return "Players and board dimensions are set.";
    }

    @Override
    protected void initializeBoard() {
        this.board = new DotsAndBoxesBoard(this.gridWidth, this.gridHeight, playerOne, playerTwo);
        this.currentPlayer = playerOne;
        System.out.println("A " + (gridWidth) + "x" + (gridHeight) + " dot grid has been created. Let's play!");
    }

    @Override
    protected void runGame(Scanner scanner, GameStats gameStats) {
        while (!isGameOver()) {
            printBoard();
            System.out.println("\nIt's team " + currentPlayer.getTeamId() +
                    " (" + currentPlayer.getName() + ")'s turn.");
            System.out.print("Enter move (e.g., '0 0 H' or 'quit'): ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("quit")) {
                quitToMainMenu(gameStats);
                return;
            }

            try {
                String[] moveParts = userInput.split(" ");
                if (moveParts.length != 3) throw new IllegalArgumentException("Input must have 3 parts.");
                int rowIndex = Integer.parseInt(moveParts[0]);
                int columnIndex = Integer.parseInt(moveParts[1]);
                char direction = moveParts[2].toUpperCase().charAt(0);
                if (direction != 'H' && direction != 'V') {
                    throw new IllegalArgumentException("Direction must be 'H' or 'V'.");
                }
                int boxesCompleted = board.drawLine(rowIndex, columnIndex, direction, currentPlayer);
                if (boxesCompleted == 0) {
                    switchPlayer();
                } else {
                    System.out.println("You completed " + boxesCompleted + " box(es)! Go again.");
                }
            } catch (Exception exception) {
                System.out.println("Invalid move! " + exception.getMessage() + " Please try again.");
            }
        }

        System.out.println("\n--- Game Over! ---");
        printBoard();
        int playerOneScore = board.getScore(playerOne);
        int playerTwoScore = board.getScore(playerTwo);

        System.out.println("\n--- Final Score ---");
        System.out.println(playerOne.getName() + ": " + playerOneScore);
        System.out.println(playerTwo.getName() + ": " + playerTwoScore);

        if (playerOneScore > playerTwoScore) {
            System.out.println("\nCongratulations " + playerOne.getName() + ", you win!");
            gameStats.recordWin(playerOne.getName(), "Dots and Boxes");
            gameStats.recordLoss(playerTwo.getName(), "Dots and Boxes");
        } else if (playerTwoScore > playerOneScore) {
            System.out.println("\nCongratulations " + playerTwo.getName() + ", you win!");
            gameStats.recordWin(playerTwo.getName(), "Dots and Boxes");
            gameStats.recordLoss(playerOne.getName(), "Dots and Boxes");
        } else {
            System.out.println("\nIt's a tie!");
            // In case of a tie, we don't record wins or losses
        }
    }

    @Override
    protected boolean isGameOver() {
        return board.isGameOver();
    }

    @Override
    protected void printBoard() {
        System.out.println(board.getBoardAsString());
    }

    @Override
    protected void quitToMainMenu(GameStats gameStats) {
        System.out.println("\nReturning to the main menu...");
        // When a player quits, both players get a quit (loss) record
        gameStats.recordQuit(playerOne.getName(), "Dots and Boxes");
        gameStats.recordQuit(playerTwo.getName(), "Dots and Boxes");
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer.equals(playerOne)) ? playerTwo : playerOne;
    }
}