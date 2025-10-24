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
        System.out.println("\nMovement: Use 'up', 'down', 'left', 'right' to move your pawn.");
        System.out.println("Walls: Use 'wall e3 h' (horizontal) or 'wall e3 v' (vertical) to place walls.");

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
    protected void runGame(Scanner scanner, GameStats gameStats) {
        while (!isGameOver()) {
            printBoard();
            System.out.println("\nIt's " + currentPlayer.getName() + "'s turn (Team " + currentPlayer.getTeamId() + ").");
            System.out.println("Walls remaining: " + board.getWallCount(currentPlayer));
            System.out.print("Enter command (up/down/left/right, 'wall e3 h', or 'quit'): ");
            String userInput = scanner.nextLine().trim().toLowerCase();

            if (userInput.equalsIgnoreCase("quit")) {
                quitToMainMenu(gameStats);
                return;
            }

            try {
                if (!board.performAction(userInput, currentPlayer)) {
                    // This method returns false if no player switch should happen (e.g., bad input)
                    // But we use exceptions for that, so this is just a placeholder.
                }
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
        
        // Record win/loss
        gameStats.recordWin(currentPlayer.getName(), "Quoridor");
        Player loser = (currentPlayer.equals(playerOne)) ? playerTwo : playerOne;
        gameStats.recordLoss(loser.getName(), "Quoridor");
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
        gameStats.recordQuit(playerOne.getName(), "Quoridor");
        gameStats.recordQuit(playerTwo.getName(), "Quoridor");
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer.equals(playerOne)) ? playerTwo : playerOne;
    }
}