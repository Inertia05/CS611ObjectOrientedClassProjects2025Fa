import java.util.*;

/**
 * Manages the state and logic of the Dots and Boxes game board.
 * This class tracks all drawn lines, box ownership, and player scores,
 * and provides the functionality to play the game and render the board.
 */
public class DotsAndBoxesBoard extends Board {

    private final List<Piece> lines;
    private final Player[][] boxOwners;
    private final Player playerOne;
    private final Player playerTwo;
    private int playerOneScore;
    private int playerTwoScore;

    public DotsAndBoxesBoard(int width, int height, Player player1, Player player2) {
        super(width, height);
        if (width < 2 || height < 2 || width > 10 || height > 10) {
            throw new IllegalArgumentException("Grid dimensions must be between 2 and 10.");
        }

        this.lines = new ArrayList<>();
        this.boxOwners = new Player[height - 1][width - 1];
        this.playerOne = player1;
        this.playerTwo = player2;
        this.playerOneScore = 0;
        this.playerTwoScore = 0;

        // Create all horizontal pieces
        for (int rowIndex = 0; rowIndex < height; rowIndex++) {
            for (int columnIndex = 0; columnIndex < width - 1; columnIndex++) {
                lines.add(new Piece(rowIndex, columnIndex, 'H'));
            }
        }

        // Create all vertical pieces
        for (int rowIndex = 0; rowIndex < height - 1; rowIndex++) {
            for (int columnIndex = 0; columnIndex < width; columnIndex++) {
                lines.add(new Piece(rowIndex, columnIndex, 'V'));
            }
        }
    }

    public int getScore(Player player) {
        return player.equals(playerOne) ? playerOneScore : playerTwoScore;
    }

    public int drawLine(int rowIndex, int columnIndex, char direction, Player currentPlayer) {
        Piece piece = findPiece(rowIndex, columnIndex, direction);
        if (piece == null) throw new IllegalArgumentException("Line out of bounds.");
        if (!piece.claim(currentPlayer)) throw new IllegalArgumentException("Line already taken.");
        return checkForCompletedBoxes(currentPlayer);
    }

    private Piece findPiece(int rowIndex, int columnIndex, char direction) {
        for (Piece piece : lines) {
            if (piece.getRow() == rowIndex && piece.getCol() == columnIndex && piece.getDirection() == direction) {
                return piece;
            }
        }
        return null;
    }

    private int checkForCompletedBoxes(Player currentPlayer) {
        int boxesCompleted = 0;
        // Here, we iterate over the grid of potential boxes
        for (int boxRowIndex = 0; boxRowIndex < height - 1; boxRowIndex++) {
            for (int boxColumnIndex = 0; boxColumnIndex < width - 1; boxColumnIndex++) {
                if (boxOwners[boxRowIndex][boxColumnIndex] == null &&
                        isClaimed(boxRowIndex, boxColumnIndex, 'H') &&        // Top line
                        isClaimed(boxRowIndex + 1, boxColumnIndex, 'H') &&  // Bottom line
                        isClaimed(boxRowIndex, boxColumnIndex, 'V') &&        // Left line
                        isClaimed(boxRowIndex, boxColumnIndex + 1, 'V')) {  // Right line
                    boxOwners[boxRowIndex][boxColumnIndex] = currentPlayer;
                    if (currentPlayer.equals(playerOne)) playerOneScore++;
                    else playerTwoScore++;
                    boxesCompleted++;
                }
            }
        }
        return boxesCompleted;
    }

    private boolean isClaimed(int rowIndex, int columnIndex, char direction) {
        Piece piece = findPiece(rowIndex, columnIndex, direction);
        return piece != null && piece.isClaimed();
    }

    @Override
    public boolean isGameOver() {
        return lines.stream().allMatch(Piece::isClaimed);
    }

    @Override
    public String getBoardAsString() {
        StringBuilder boardString = new StringBuilder();
        for (int rowIndex = 0; rowIndex < height; rowIndex++) {
            for (int columnIndex = 0; columnIndex < width; columnIndex++) {
                boardString.append(".");
                if (columnIndex < width - 1) {
                    boardString.append(isClaimed(rowIndex, columnIndex, 'H') ? "---" : "   ");
                }
            }
            boardString.append("\n");

            if (rowIndex < height - 1) {
                for (int columnIndex = 0; columnIndex < width; columnIndex++) {
                    boardString.append(isClaimed(rowIndex, columnIndex, 'V') ? "| " : "  ");

                    if (columnIndex < width - 1) {
                        Player owner = boxOwners[rowIndex][columnIndex];
                        if (owner != null) {
                            boardString.append(owner.getName().substring(0, 1).toUpperCase()).append(" ");
                        } else {
                            boardString.append("  ");
                        }
                    }
                }
                boardString.append("\n");
            }
        }
        return boardString.toString();
    }
}