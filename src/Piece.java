public class Piece {
    private final int row;
    private final int col;
    private final char direction; // 'H' for horizontal, 'V' for vertical
    private Player owner;

    public Piece(int row, int col, char direction) {
        this.row = row;
        this.col = col;
        this.direction = direction;
        this.owner = null;
    }

    public boolean isClaimed() {
        return owner != null;
    }

    public boolean claim(Player player) {
        if (!isClaimed()) {
            this.owner = player;
            return true;
        }
        return false;
    }

    public Player getOwner() { return owner; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public char getDirection() { return direction; }
}
