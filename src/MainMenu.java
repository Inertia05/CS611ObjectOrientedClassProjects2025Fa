import java.util.Scanner;

/**
 * The main entry point for the board game application.
 * This class displays a menu to the user, allowing them to select a game
 * to play or to exit the application. It manages the main program loop.
 */
public class MainMenu {

    public static void main(String[] args) {
        new MainMenu().run();
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Board Games!");

        while (true) {
            showMenu();
            Game selectedGame = selectGame(scanner);
            if (selectedGame == null) {
                System.out.println("Goodbye!");
                scanner.close();
                break;
            }
            selectedGame.play(scanner);
        }
    }

    private void showMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Start sliding puzzle game");
        System.out.println("2. Start dot and box game");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    private Game selectGame(Scanner scanner) {
        String userChoice = scanner.nextLine();
        switch (userChoice) {
            case "1":
                return new PuzzleGame();
            case "2":
                return new DotsAndBoxesGame();
            case "3":
                return null;
            default:
                System.out.println("Invalid option. Please try again.");
                return selectGame(scanner);
        }
    }
}