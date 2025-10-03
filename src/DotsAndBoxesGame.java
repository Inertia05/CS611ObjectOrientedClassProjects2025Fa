import java.util.Scanner;

public class DotsAndBoxesGame extends Game {

    private Board.DotsAndBoxesBoard board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    // CHANGED: Added fields for width and height
    private int gridWidth;
    private int gridHeight;

    public DotsAndBoxesGame() {
    }

    @Override
    protected String getGameInfo(Scanner scanner) {
        System.out.println("\n--- Setting up Dots and Boxes ---");
        System.out.print("Enter name for Player 1: ");
        this.player1 = new Player(scanner.nextLine());

        System.out.print("Enter name for Player 2: ");
        this.player2 = new Player(scanner.nextLine());

        // CHANGED: Get grid dimensions from the user
        System.out.println("Enter the dimensions of the dot grid.");
        this.gridWidth = promptForDimension(scanner, "width");
        this.gridHeight = promptForDimension(scanner, "height");

        return "Players and board dimensions are set.";
    }

    // ADDED: A helper method for getting dimensions
    private int promptForDimension(Scanner scanner, String dimensionName) {
        while (true) {
            System.out.print("Enter grid " + dimensionName + " (between 2 and 5): ");
            try {
                int dim = Integer.parseInt(scanner.nextLine());
                if (dim >= 2 && dim <= 5) {
                    return dim;
                } else {
                    System.out.println("Invalid " + dimensionName + ". Please enter a number between 2 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    @Override
    protected void initializeBoard() {
        // CHANGED: Pass width and height to the board constructor
        this.board = new Board.DotsAndBoxesBoard(this.gridWidth, this.gridHeight, player1, player2);
        this.currentPlayer = player1;
        // CHANGED: Updated confirmation message
        System.out.println("A " + (gridWidth - 1) + "x" + (gridHeight - 1) + " box grid has been created. Let's play!");
    }

    // The rest of the DotsAndBoxesGame class remains the same...
    @Override
    protected void runGame(Scanner scanner) {
        while (!isGameOver()) {
            printBoard();
            System.out.println("\nIt's " + currentPlayer.getName() + "'s turn.");
            System.out.print("Enter move (e.g., '0 0 H' or 'quit'): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("quit")) {
                quitToMainMenu();
                return;
            }

            try {
                String[] parts = input.split(" ");
                if (parts.length != 3) throw new IllegalArgumentException("Input must have 3 parts.");
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                char direction = parts[2].toUpperCase().charAt(0);
                if (direction != 'H' && direction != 'V') throw new IllegalArgumentException("Direction must be 'H' or 'V'.");

                int boxesCompleted = board.drawLine(row, col, direction, currentPlayer);
                if (boxesCompleted == 0) {
                    switchPlayer();
                } else {
                    System.out.println("You completed " + boxesCompleted + " box(es)! Go again. ⭐");
                }
            } catch (Exception e) {
                System.out.println("Invalid move! " + e.getMessage() + " Please try again.");
            }
        }

        System.out.println("\n--- Game Over! ---");
        printBoard();
        int score1 = board.getScore(player1);
        int score2 = board.getScore(player2);
        System.out.println("\n--- Final Score ---");
        System.out.println(player1.getName() + ": " + score1);
        System.out.println(player2.getName() + ": " + score2);
        if (score1 > score2) System.out.println("\nCongratulations " + player1.getName() + ", you win! 🎉");
        else if (score2 > score1) System.out.println("\nCongratulations " + player2.getName() + ", you win! 🎉");
        else System.out.println("\nIt's a tie! 🤝");
    }

    @Override
    protected boolean isGameOver() { return board.isGameOver(); }
    @Override
    protected void printBoard() { System.out.println(board.getBoardAsString()); }
    @Override
    protected void quitToMainMenu() { System.out.println("\nReturning to the main menu..."); }
    private void switchPlayer() {
        currentPlayer = (currentPlayer.equals(player1)) ? player2 : player1;
    }
}