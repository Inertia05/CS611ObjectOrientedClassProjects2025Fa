import java.util.Scanner;

/**
 * An abstract class that defines the structure for all board games.
 * It uses the Template Method design pattern with the final 'play' method.
 */
public abstract class Game {

    /**
     * The main template method that runs the entire game lifecycle.
     */
    public final void play(Scanner scanner) {
        getGameInfo(scanner);
        initializeBoard();
        runGame(scanner);
        System.out.println("\n------------------------------------\n");
    }

    // Abstract methods to be implemented by specific game classes
    protected abstract String getGameInfo(Scanner scanner);
    protected abstract void initializeBoard();
    protected abstract void runGame(Scanner scanner);
    protected abstract boolean isGameOver();
    protected abstract void printBoard();
    protected abstract void quitToMainMenu();
}

