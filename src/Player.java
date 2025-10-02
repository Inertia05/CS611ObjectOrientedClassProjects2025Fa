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

        if (!trimmed.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Player name must contain only letters and spaces.");
        }

        String normalized = trimmed.replaceAll("\\s+", " ");
        this.name = normalized;
    }

    public String getName() {
        return name;
    }
}
