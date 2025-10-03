import java.util.Scanner;

/**
 * This class contains the main logic for running a game of Dots and Boxes.
 */
public class DotsAndBoxesGame extends Game {

    private Board.DotsAndBoxesBoard board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    public DotsAndBoxesGame() {
    }

    @Override
    protected String getGameInfo(Scanner scanner) {
        System.out.println("\n--- Setting up Dots and Boxes ---");
        System.out.print("Enter name for Player 1: ");
        // scanner.nextLine(); // This might not be needed depending on the MainMenu's scanner state.
        this.player1 = new Player(scanner.nextLine());

        System.out.print("Enter name for Player 2: ");
        this.player2 = new Player(scanner.nextLine());

        return "Players created. Board is ready to be initialized.";
    }

    @Override
    protected void initializeBoard() {
        // We'll use a default size of 3x3 dots (for a 2x2 box game)
        int boardSize = 3;
        this.board = new Board.DotsAndBoxesBoard(boardSize, player1, player2);
        this.currentPlayer = player1; // Player 1 always starts
        System.out.println("A " + (boardSize - 1) + "x" + (boardSize - 1) + " board has been created. Let's play!");
    }

    @Override
    protected void runGame(Scanner scanner) {
        // The main game loop
        while (!isGameOver()) {
            printBoard();
            System.out.println("\nIt's " + currentPlayer.getName() + "'s turn.");
            System.out.print("Enter move (e.g., '0 0 H' for the top-left horizontal line, or 'quit'): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("quit")) {
                quitToMainMenu();
                return; // Exit the game loop
            }

            try {
                String[] parts = input.split(" ");
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Input must have 3 parts: row col direction.");
                }
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                char direction = parts[2].toUpperCase().charAt(0);

                if (direction != 'H' && direction != 'V') {
                    throw new IllegalArgumentException("Direction must be 'H' or 'V'.");
                }

                int boxesCompleted = board.drawLine(row, col, direction, currentPlayer);

                if (boxesCompleted == 0) {
                    switchPlayer();
                } else {
                    System.out.println("You completed " + boxesCompleted + " box(es)! You get to go again. ⭐");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! " + e.getMessage() + " Please use the format 'row col direction'.");
            }
        }

        // --- Game Over ---
        System.out.println("\n--- Game Over! ---");
        printBoard();

        // Announce the winner
        int score1 = board.getScore(player1);
        int score2 = board.getScore(player2);

        System.out.println("\n--- Final Score ---");
        System.out.println(player1.getName() + ": " + score1);
        System.out.println(player2.getName() + ": " + score2);

        if (score1 > score2) {
            System.out.println("\nCongratulations " + player1.getName() + ", you win! 🎉");
        } else if (score2 > score1) {
            System.out.println("\nCongratulations " + player2.getName() + ", you win! 🎉");
        } else {
            System.out.println("\nIt's a tie! 🤝");
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
    protected void quitToMainMenu() {
        System.out.println("\nReturning to the main menu...");
    }

    private void switchPlayer() {
        if (currentPlayer.equals(player1)) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }
}