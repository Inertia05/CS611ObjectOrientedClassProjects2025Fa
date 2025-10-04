/**
 * Represents a single line segment on the Dots and Boxes board.
 * Each piece has a location (row and column index), a direction
 * (horizontal or vertical), and can be claimed by a player.
 */
public class Piece {
    private final int rowIndex;
    private final int columnIndex;
    private final char direction; // 'H' for horizontal, 'V' for vertical
    private Player owner;

    public Piece(int rowIndex, int columnIndex, char direction) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.direction = direction;
        this.owner = null;
    }

    /**
     * Checks if this piece has been claimed by a player.
     * @return true if the piece has an owner, false otherwise.
     */
    public boolean isClaimed() {
        return owner != null;
    }

    /**
     * Assigns a player as the owner of this piece if it is not already claimed.
     * @param claimingPlayer The player who is claiming this piece.
     * @return true if the claim was successful, false if the piece was already owned.
     */
    public boolean claim(Player claimingPlayer) {
        if (!isClaimed()) {
            this.owner = claimingPlayer;
            return true;
        }
        return false;
    }

    public Player getOwner() {
        return owner;
    }

    public int getRow() {
        return rowIndex;
    }

    public int getCol() {
        return columnIndex;
    }

    public char getDirection() {
        return direction;
    }
}