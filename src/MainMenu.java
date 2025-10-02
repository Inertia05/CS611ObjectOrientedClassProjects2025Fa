import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        System.out.println("Welcome to Board Games!");
        Scanner scanner = new Scanner(System.in); 
        print("Enter player name: ");
        String name = scanner.nextLine();
        print("Hello, " + name + "!");
        Player player = new Player(name);
        Game newGame = null;
        while (true) {
            showMenu(scanner, name); // if this returns, user chose to start a new game
            String info = newGame.getGameInfo(scanner);
            newGame.initializeBoard();
            newGame.runGame(scanner);//game loop running in this method
            // Game have ended here
        }
    }
    private static void print(String msg) {
        System.out.println(msg);
    }

    private static void showMenu(Scanner scanner, String name) {
        System.out.println("Menu:");
        System.out.println("1. Start sliding puzzle game");
        System.out.println("2. Start dot and box game");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        if (choice == 3) {
            System.out.println("Goodbye, " + name + "!");
            System.exit(0);
        }
        if (choice != 1 && choice != 2) {
            throw new IllegalArgumentException("Invalid menu option: " + choice);
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
