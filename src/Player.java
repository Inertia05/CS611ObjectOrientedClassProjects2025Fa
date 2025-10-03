/**
 * Models a player in the puzzle game.
 */
public class Player {
    private final String name;

    public Player(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty.");
        }

        String trimmed = name.trim();

        // CHANGED: Updated the regex to include numbers (0-9) and allow multiple characters (+)
        if (!trimmed.matches("[a-zA-Z0-9 ]+")) {
            throw new IllegalArgumentException("Player name must contain only letters, numbers, and spaces.");
        }

        String normalized = trimmed.replaceAll("\\s+", " ");
        this.name = normalized;
    }

    public String getName() {
        return name;
    }
}