/**
 * Models a player in one of the board games.
 * This class stores the player's name and ensures that upon creation the name is valid.
 * A valid name cannot be empty, must contain only letters, numbers, and spaces,
 * and has its whitespace normalized (e.g., multiple spaces are reduced to one).
 */
public class Player {
    private final String name;

    public Player(String inputName) {
        if (inputName == null || inputName.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty.");
        }

        String trimmedName = inputName.trim();

        // The regex ensures the name contains only allowed characters.
        if (!trimmedName.matches("[a-zA-Z0-9 ]+")) {
            throw new IllegalArgumentException("Player name must contain only letters, numbers, and spaces.");
        }

        // Replaces multiple whitespace characters with a single space for consistency.
        String normalizedName = trimmedName.replaceAll("\\s+", " ");
        this.name = normalizedName;
    }

    public String getName() {
        return name;
    }
}