import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the board for the sliding puzzle game.
 * This version uses a grid of Tile objects instead of primitive integers.
 */
public class PuzzleBoard extends Board {
    // CHANGED: The grid now holds Tile objects.
    private final Tile[][] grid;
    private int emptyRow, emptyCol;
    private final Random random = new Random();

    public PuzzleBoard(int width, int height) {
        super(width, height);
        if (width < 2 || width > 10 || height < 2 || height > 10) {
            throw new IllegalArgumentException("Board dimensions must be between 2 and 10.");
        }

        // CHANGED: The grid is initialized to hold Tile objects.
        this.grid = new Tile[height][width];
        initBoard();
        shuffleBoard();
    }

    @Override
    public String getBoardAsString() {
        StringBuilder sb = new StringBuilder();
        String line = "+--".repeat(width) + "+\n";
        sb.append(line);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // CHANGED: Appends the string representation from the Tile object itself.
                sb.append("|").append(grid[i][j].toString());
            }
            sb.append("|\n");
            sb.append(line);
        }
        return sb.toString();
    }

    @Override
    public boolean isGameOver() {
        return isSolved();
    }

    public boolean isSolved() {
        int expectedValue = 1;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // CHANGED: Gets the value from the Tile object.
                int currentValue = grid[i][j].getValue();

                if (i == height - 1 && j == width - 1) {
                    if (!grid[i][j].isEmpty()) return false;
                } else if (currentValue != expectedValue++) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean slideTile(int tileValue) {
        if (tileValue <= 0 || tileValue >= width * height) return false;
        int tileRow = -1, tileCol = -1;

        // Find the row and column of the tile to slide
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // CHANGED: Gets value from the Tile object for comparison.
                if (grid[i][j].getValue() == tileValue) {
                    tileRow = i;
                    tileCol = j;
                    break;
                }
            }
            if (tileRow != -1) break;
        }

        // Check if the found tile is adjacent to the empty space
        if (tileRow == -1 || !isAdjacent(tileRow, tileCol)) return false;

        // Slide the tile
        slide(tileRow, tileCol);
        return true;
    }

    private void initBoard() {
        int value = 1;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // CHANGED: Creates a new Tile object for each position.
                grid[i][j] = new Tile(value++);
            }
        }
        // Place the empty tile (value 0)
        grid[height - 1][width - 1] = new Tile(0);
        emptyRow = height - 1;
        emptyCol = width - 1;
    }

    private void shuffleBoard() {
        for (int i = 0; i < width * height * 20; i++) {
            List<int[]> moves = getValidMoves();
            int[] move = moves.get(random.nextInt(moves.size()));
            slide(move[0], move[1]);
        }
    }

    private void slide(int row, int col) {
        // CHANGED: The logic now swaps Tile objects.
        grid[emptyRow][emptyCol] = grid[row][col];
        grid[row][col] = new Tile(0); // The slid-from position is now empty.
        emptyRow = row;
        emptyCol = col;
    }

    private boolean isAdjacent(int row, int col) {
        return (Math.abs(row - emptyRow) == 1 && col == emptyCol) ||
                (Math.abs(col - emptyCol) == 1 && row == emptyRow);
    }

    private List<int[]> getValidMoves() {
        List<int[]> moves = new ArrayList<>();
        if (emptyRow > 0) moves.add(new int[]{emptyRow - 1, emptyCol});
        if (emptyRow < height - 1) moves.add(new int[]{emptyRow + 1, emptyCol});
        if (emptyCol > 0) moves.add(new int[]{emptyRow, emptyCol - 1});
        if (emptyCol < width - 1) moves.add(new int[]{emptyRow, emptyCol + 1});
        return moves;
    }
}
