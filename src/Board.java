import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the abstract concept of a game board, serving as a blueprint
 * for specific game board implementations like Dots and Boxes or a sliding puzzle.
 */
public abstract class Board {
    /**
     * The width of the game board (number of columns).
     */
    protected final int width;

    /**
     * The height of the game board (number of rows).
     */
    protected final int height;

    /**
     * Constructor for a board with given dimensions.
     * @param width The width of the board.
     * @param height The height of the board.
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public abstract String getBoardAsString();

    public abstract boolean isGameOver();
}