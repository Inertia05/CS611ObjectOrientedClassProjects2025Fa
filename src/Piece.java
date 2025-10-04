public class Piece {
    private final String type;
    private final Player owner;
    private Tile position;
    public Piece(String type, Player owner, Tile position) {
        this.type = type;
        this.owner = owner;
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public Player getOwner() {
        return owner;
    }

    public Tile getPosition() {
        return position;
    }

    public void setPosition(Tile position) {
        this.position = position;
    }
}