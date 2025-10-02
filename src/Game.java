package CS611ObjectOrientedClassProjects2025Fa;
public class Game {
    import java.util.Scanner; // Source: google
    public static void main(String[] args) {
        System.out.println("Welcome to Sliding Puzzle");
        Scanner scanner = new Scanner(System.in); 
        print("Enter player name: ");
        String name = scanner.nextLine();
        print("Hello, " + name + "!");
        SlidingGame newGame = null;
        while (true) {
            showMenu(scanner, name); // if this returns, user chose to start a new game
            newGame = getGameInfo(scanner);
            newGame.initializeBoard();
            newGame.runGame(scanner);
        }
    }
    private static void print(String msg) {
        System.out.println(msg);
    }

    private static SlidingGame getGameInfo(Scanner scanner) {
        print("Enter puzzle height: ");
        int height = scanner.nextInt();
        print("Enter puzzle width: ");
        int width = scanner.nextInt();

        print("Puzzle size set to " + height + "x" + width);
        return new SlidingGame(height, width);
    }

    private static void showMenu(Scanner scanner, String name) {
        System.out.println("Menu:");
        System.out.println("1. Start new game");
        System.out.println("2. Exit");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        if (choice == 2) {
            System.out.println("Goodbye, " + name + "!");
            System.exit(0);
        }
        if (choice != 1) {
            throw new IllegalArgumentException("Invalid menu option: " + choice);
        }
    }
    
}

class SlidingGame {
    private void initializeBoard() {
        // Placeholder for board initialization logic
    }
    
}
