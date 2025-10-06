/**
 * Models a player in one of the board games.
 * This class stores the player's name and ensures that upon creation the name is valid.
 * This class stores the player's name and an assigned team ID.
 * A valid name cannot be empty, must contain only letters, numbers, and spaces,
 * and has its whitespace normalized (e.g., multiple spaces are reduced to one).
 */
public class Player {
    private final String name;
    private final int teamId; // Field for the team ID

    public Player(String inputName, int teamId) { // Updated constructor
        if (inputName == null || inputName.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty.");
        }
        String trimmedName = inputName.trim();
        if (!trimmedName.matches("[a-zA-Z0-9 ]+")) {
            throw new IllegalArgumentException("Player name must contain only letters, numbers, and spaces.");
        }
        String normalizedName = trimmedName.replaceAll("\\s+", " ");

        this.name = normalizedName;
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public int getTeamId() {
        return teamId;
    }
}