import java.util.*;

public class DotsAndBoxesBoard extends Board {

    private final List<Piece> lines;   // all horizontal + vertical pieces
    private final Player[][] boxOwners;
    private final Player p1, p2;
    private int player1Score, player2Score;

    public DotsAndBoxesBoard(int width, int height, Player player1, Player player2) {
        super(width, height);
        if (width < 2 || height < 2 || width > 10 || height > 10) {
            throw new IllegalArgumentException("Grid dimensions must be between 2 and 10.");
        }

        this.lines = new ArrayList<>();
        this.boxOwners = new Player[height - 1][width - 1];
        this.p1 = player1;
        this.p2 = player2;
        this.player1Score = 0;
        this.player2Score = 0;

        // Create all horizontal pieces
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width - 1; c++) {
                lines.add(new Piece(r, c, 'H'));
            }
        }

        // Create all vertical pieces
        for (int r = 0; r < height - 1; r++) {
            for (int c = 0; c < width; c++) {
                lines.add(new Piece(r, c, 'V'));
            }
        }
    }

    public int getScore(Player player) {
        return player.equals(p1) ? player1Score : player2Score;
    }

    public int drawLine(int r, int c, char direction, Player currentPlayer) {
        Piece piece = findPiece(r, c, direction);
        if (piece == null) throw new IllegalArgumentException("Line out of bounds.");
        if (!piece.claim(currentPlayer)) throw new IllegalArgumentException("Line already taken.");
        return checkForCompletedBoxes(currentPlayer);
    }

    private Piece findPiece(int r, int c, char direction) {
        for (Piece p : lines) {
            if (p.getRow() == r && p.getCol() == c && p.getDirection() == direction) {
                return p;
            }
        }
        return null;
    }

    private int checkForCompletedBoxes(Player currentPlayer) {
        int boxesCompleted = 0;
        for (int r = 0; r < height - 1; r++) {
            for (int c = 0; c < width - 1; c++) {
                if (boxOwners[r][c] == null &&
                        isClaimed(r, c, 'H') &&
                        isClaimed(r + 1, c, 'H') &&
                        isClaimed(r, c, 'V') &&
                        isClaimed(r, c + 1, 'V')) {
                    boxOwners[r][c] = currentPlayer;
                    if (currentPlayer.equals(p1)) player1Score++;
                    else player2Score++;
                    boxesCompleted++;
                }
            }
        }
        return boxesCompleted;
    }

    private boolean isClaimed(int r, int c, char dir) {
        Piece p = findPiece(r, c, dir);
        return p != null && p.isClaimed();
    }

    @Override
    public boolean isGameOver() {
        return lines.stream().allMatch(Piece::isClaimed);
    }

    @Override
    public String getBoardAsString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                sb.append("●");
                if (c < width - 1) {
                    sb.append(isClaimed(r, c, 'H') ? "---" : "   ");
                }
            }
            sb.append("\n");

            if (r < height - 1) {
                for (int c = 0; c < width; c++) {
                    sb.append(isClaimed(r, c, 'V') ? "| " : "  ");

                    if (c < width - 1) {
                        Player owner = boxOwners[r][c];
                        if (owner != null) {
                            sb.append(owner.getName().substring(0, 1).toUpperCase()).append(" ");
                        } else {
                            sb.append("  ");
                        }
                    }
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
