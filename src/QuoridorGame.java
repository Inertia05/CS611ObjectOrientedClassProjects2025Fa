import java.util.Scanner;

/**
 * Orchestrates the gameplay for Quoridor.
 * This class handles player setup, board initialization, the main game loop,
 * processing user input, and determining the winner, reusing the Game template.
 */
public class QuoridorGame extends Game {

    private QuoridorBoard board;
    private Player playerOne;
    private Player playerTwo;
    private Player currentPlayer;

    public QuoridorGame() {
    }

    @Override
    protected String getGameInfo(Scanner scanner) {
        System.out.println("\n--- Setting up Quoridor ---");
        System.out.println("Quoridor is a 2-player game on a 9x9 board.");
        System.out.println("Player 1 starts at 'e1' (bottom) and wins by reaching row 9.");
        System.out.println("Player 2 starts at 'e9' (top) and wins by reaching row 1.");
        System.out.println("Each player has 10 walls to place.");

        System.out.print("Enter name for Player 1: ");
        this.playerOne = new Player(scanner.nextLine(), 1);

        System.out.print("Enter name for Player 2: ");
        this.playerTwo = new Player(scanner.nextLine(), 2);
        return "Players are set.";
    }

    @Override
    protected void initializeBoard() {
        this.board = new QuoridorBoard(playerOne, playerTwo);
        this.currentPlayer = playerOne;
        System.out.println("The board is set. Let's play!");
    }

    @Override
    protected void runGame(Scanner scanner) {
        while (!isGameOver()) {
            printBoard();
            System.out.println("\nIt's " + currentPlayer.getName() + "'s turn (Team " + currentPlayer.getTeamId() + ").");
            System.out.println("Walls remaining: " + board.getWallCount(currentPlayer));
            System.out.print("Enter move (e.g., 'move e2' or 'wall e3 h' or 'quit'): ");
            String userInput = scanner.nextLine().trim().toLowerCase();

            if (userInput.equalsIgnoreCase("quit")) {
                quitToMainMenu();
                return;
            }

            try {
                if (!board.performAction(userInput, currentPlayer)) {
                    // This method returns false if no player switch should happen (e.g., bad input)
                    // But we use exceptions for that, so this is just a placeholder.
                }
                // Check for a winner *after* the move
                if (isGameOver()) {
                    break;
                }
                switchPlayer();

            } catch (Exception exception) {
                System.out.println("Invalid move! " + exception.getMessage() + " Please try again.");
            }
        }

        System.out.println("\n--- Game Over! ---");
        printBoard();
        // The winner is the *current* player, since the game ends right after their move.
        System.out.println("Congratulations " + currentPlayer.getName() + ", you win!");
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
    protected void quitToMainMenu() {
        System.out.println("\nReturning to the main menu...");
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer.equals(playerOne)) ? playerTwo : playerOne;
    }
}