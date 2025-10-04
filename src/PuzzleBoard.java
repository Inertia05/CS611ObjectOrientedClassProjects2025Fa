import java.util.ArrayList;
import java.util.List;

public class DotsAndBoxesBoard extends Board {

    private final List<Piece> lines;      // All horizontal + vertical lines
    private final Player[][] boxOwners;   // Tracks who owns each box

    private final Player p1;
    private final Player p2;
    private int player1Score;
    private int player2Score;

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

        initPieces();
    }

    // Initialize all pieces (horizontal + vertical)
    private void initPieces() {
        // Horizontal lines
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width - 1; c++) {
                lines.add(new Piece(r, c, 'H'));
            }
        }
        // Vertical lines
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
        if (piece == null) throw new IllegalArgumentException("Line is out of bounds.");
        if (piece.isClaimed()) throw new IllegalArgumentException("Line is already taken.");
        piece.claim(currentPlayer);
        return checkForCompletedBoxes(currentPlayer);
    }

    private int checkForCompletedBoxes(Player currentPlayer) {
        int boxesCompleted = 0;
        for (int r = 0; r < height - 1; r++) {
            for (int c = 0; c < width - 1; c++) {
                if (boxOwners[r][c] == null &&
                        isClaimed(r, c, 'H') &&        // Top
                        isClaimed(r + 1, c, 'H') &&    // Bottom
                        isClaimed(r, c, 'V') &&        // Left
                        isClaimed(r, c + 1, 'V')) {    // Right

                    boxOwners[r][c] = currentPlayer;
                    if (currentPlayer.equals(p1)) player1Score++;
                    else player2Score++;
                    boxesCompleted++;
                }
            }
        }
        return boxesCompleted;
    }

    @Override
    public boolean isGameOver() {
        for (Piece piece : lines) {
            if (!piece.isClaimed()) return false;
        }
        return true;
    }

    @Override
    public String getBoardAsString() {
        StringBuilder sb = new StringBuilder();

        for (int r = 0; r < height; r++) {
            // Draw dots + horizontal lines
            for (int c = 0; c < width; c++) {
                sb.append("●");
                if (c < width - 1) {
                    sb.append(isClaimed(r, c, 'H') ? "---" : "   ");
                }
            }
            sb.append("\n");

            // Draw vertical lines + box owners
            if (r < height - 1) {
                for (int c = 0; c < width; c++) {
                    sb.append(isClaimed(r, c, 'V') ? "| " : "  ");
                    if (c < width - 1) {
                        Player owner = boxOwners[r][c];
                        if (owner != null) {
                            sb.append(owner.getName().substring(0, 1).toUpperCase()).append("  ");
                        } else {
                            sb.append("   ");
                        }
                    }
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    // --- Helper methods ---
    private Piece findPiece(int r, int c, char direction) {
        for (Piece piece : lines) {
            if (piece.getRow() == r && piece.getCol() == c && piece.getDirection() == direction) {
                return piece;
            }
        }
        return null;
    }

    private boolean isClaimed(int r, int c, char direction) {
        Piece piece = findPiece(r, c, direction);
        return piece != null && piece.isClaimed();
    }
}
