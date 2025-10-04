import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An abstract class representing a generic game board.
 * It contains nested classes for specific board game implementations.
 */
public abstract class Board {
    public abstract String getBoardAsString();

    public abstract boolean isGameOver();


