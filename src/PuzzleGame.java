import java.util.Scanner;

/**
 * The main class that runs the sliding puzzle game.
 */
public class PuzzleGame {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Welcome to the Sliding Puzzle Game! ===");
        String playerName = promptForName();
        Player player = new Player(playerName);

        boolean playAgain;
        do {
            int size = promptBoardSize();
            Board board = new Board(size);

            System.out.println("Okay " + player.getName() + ", here’s your puzzle:");
            System.out.print(board.getBoardAsString());

            while (!board.isSolved()) {
                String input = prompt(player.getName() + ", which tile do you want to slide? ");
                int tile = parseInt(input, -1);

                if (!board.slideTile(tile)) {
                    System.out.println("Invalid move! That tile cannot be moved.");
                }
                System.out.print(board.getBoardAsString());
            }

            System.out.println("Congratulations, " + player.getName() + "! You solved the puzzle!");
            playAgain = promptYesNo("Do you want to play again? (y/n): ");
        } while (playAgain);

        System.out.println("Thanks for playing! Goodbye!");
        SCANNER.close();
    }

    private static String prompt(String message) {
        System.out.print(message);
        return SCANNER.nextLine().trim();
    }

    private static String promptForName() {
        while (true) {
            String name = prompt("Enter your name: ");
            if (!name.trim().isEmpty() && name.matches("[a-zA-Z ]+")) {
                return name;
            }
            System.out.println("Invalid name! Please use letters and spaces only.");
        }
    }

    private static int promptBoardSize() {
        while (true) {
            String input = prompt("Enter puzzle size (between 2 and 5): ");
            int size = parseInt(input, -1);
            if (size >= 2 && size <= 5) return size;
            System.out.println("Invalid input. Please enter a number between 2 and 5.");
        }
    }

    private static int parseInt(String input, int defaultValue) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private static boolean promptYesNo(String message) {
        while (true) {
            String input = prompt(message).toLowerCase();
            if (input.equals("y")) return true;
            if (input.equals("n")) return false;
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
        }
    }
}
