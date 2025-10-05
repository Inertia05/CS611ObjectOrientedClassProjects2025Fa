/**
 * Models a single tile within the sliding puzzle game.
 * Each tile holds a numerical value, with a value of 0 representing the empty space.
 * This class is designed to be immutable.
 */
public class Tile {
    private final int value;

    /**
     * Constructs a Tile with a specific value.
     *
     * @param value The number on the tile.
     */
    public Tile(int value) {
        this.value = value;
    }

    /**
     * Gets the numerical value of the tile.
     * @return The tile's number.
     */
    public int getValue() {
        return value;
    }

    /**
     * Checks if this tile is the empty space.
     *
     * @return true if the tile's value is 0, false otherwise.
     */
    public boolean isEmpty() {
        return this.value == 0;
    }

    /**
     * Provides a formatted string representation for the tile.
     *
     * @return A two-character string for the tile's value, or spaces for an empty tile.
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "  ";
        }
        return String.format("%2d", this.value);
    }
}
