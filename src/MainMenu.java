import java.util.Scanner;

/**
 * The main entry point for the application. It displays a menu
 * and allows the user to choose which game to play.
 */
public class MainMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Board Games!");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Start sliding puzzle game");
            System.out.println("2. Start dot and box game");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();
            Game game = null;

            switch (choice) {
                case "1":
                    game = new PuzzleGame();
                    break;
                case "2":
                    game = new DotsAndBoxesGame();
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    scanner.close();
                    return; // Exit the program
                default:
                    System.out.println("Invalid option. Please try again.");
                    continue; // Skip the rest of the loop and show the menu again
            }

            // The 'play' method now handles the entire game flow for the selected game.
            // The MainMenu doesn't need to know the individual steps.
            if (game != null) {
                game.play(scanner);
            }
        }
    }
}