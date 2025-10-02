import java.util.Scanner; // Source: google
public class MainMenu {
    public static void main(String[] args) {
        System.out.println("Welcome to Board Games!");
        Scanner scanner = new Scanner(System.in); 
        
        Game newGame = null;
        while (true) {
            int choice = showMenu(scanner); // if this returns, user chose to start a new game
            if (choice == 1) {
                newGame = new PuzzleGame();
            } else if (choice == 2) {
                newGame = new DotAndBoxGame();
            }
            String info = newGame.getGameInfo(scanner);
            newGame.initializeBoard();
            newGame.runGame(scanner);//game loop running in this method
            // Game have ended here
        }
    }

    private static int showMenu(Scanner scanner) {
        System.out.println("Menu:");
        System.out.println("1. Start sliding puzzle game");
        System.out.println("2. Start dot and box game");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        if (choice == 3) {
            System.out.println("Goodbye!");
            System.exit(0);
        }
        if (choice != 1 && choice != 2) {
            throw new IllegalArgumentException("Invalid menu option: " + choice);
        }
        return choice;
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
