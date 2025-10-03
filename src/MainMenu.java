import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        System.out.println("Welcome to Board Games!");
        Scanner scanner = new Scanner(System.in);

        Game newGame = null;
        while (true) {
            int choice = showMenu(scanner);

            // If the choice was invalid, show the menu again
            if (choice == -1) {
                System.out.println(); // Add a space for readability
                continue;
            }

            if (choice == 1) {
                newGame = new PuzzleGame();
            } else if (choice == 2) {
                newGame = new DotsAndBoxesGame();
            }

            String info = newGame.getGameInfo(scanner);
            newGame.initializeBoard();
            newGame.runGame(scanner); // Game loop runs here
            System.out.println("\n------------------------------------\n");
        }
    }

    private static int showMenu(Scanner scanner) {
        System.out.println("Menu:");
        System.out.println("1. Start sliding puzzle game");
        System.out.println("2. Start dot and box game");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");

        try {
            // Read the entire line of input as a string
            String input = scanner.nextLine();
            // Convert the string to an integer
            int choice = Integer.parseInt(input);

            if (choice == 3) {
                System.out.println("Goodbye!");
                System.exit(0);
            }

            if (choice == 1 || choice == 2) {
                return choice;
            } else {
                System.out.println("Invalid option. Please choose 1, 2, or 3.");
                return -1; // Return an invalid choice
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1; // Return an invalid choice
        }
    }
}

abstract class Game {
    protected abstract void initializeBoard();
    protected abstract void runGame(Scanner scanner);
    protected abstract String getGameInfo(Scanner scanner);
    protected abstract boolean isGameOver();
    protected abstract void printBoard();
    protected abstract void quitToMainMenu();

}