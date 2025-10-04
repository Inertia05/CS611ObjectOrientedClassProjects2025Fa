import java.util.ArrayList;
import java.util.List;
import java.util.Random;

    // == NESTED PUZZLE BOARD CLASS ==
public static class PuzzleBoard extends Board {
    // CHANGED: Replaced size with width and height
    private final int width;
    private final int height;
    private final int[][] grid;
    private int emptyRow, emptyCol;
    private final Random random = new Random();

    // CHANGED: Constructor now accepts width and height
    public PuzzleBoard(int width, int height) {
        if (width < 1 || width > 10 || height < 1 || height > 10) {
            throw new IllegalArgumentException("Board dimensions must be between 10 and 10.");
        }
        this.width = width;
        this.height = height;
        // CHANGED: Grid is initialized with height (rows) and width (columns)
        this.grid = new int[height][width];
        initBoard();
        shuffleBoard();
    }

    @Override
    public String getBoardAsString() {
        StringBuilder sb = new StringBuilder();
        // CHANGED: Line repeat is based on width
        StringBuilder lineBuilder = new StringBuilder();
        for (int i = 0; i < width; i++) {
            lineBuilder.append("+--");
        }
        lineBuilder.append("+\n");
        String line = lineBuilder.toString();
        sb.append(line);
        // CHANGED: Loops use height for rows and width for columns
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == 0) {
                    sb.append("|  ");
                } else {
                    sb.append(String.format("|%2d", grid[i][j]));
                }
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
        int value = 1;
        // CHANGED: Loops use height and width
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // Check for the last cell
                if (i == height - 1 && j == width - 1) {
                    if (grid[i][j] != 0) return false;
                } else if (grid[i][j] != value++) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean slideTile(int tile) {
        // CHANGED: Tile number validation uses width * height
        if (tile <= 0 || tile > width * height - 1) return false;
        int tileRow = -1, tileCol = -1;
        // CHANGED: Loops use height and width
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == tile) {
                    tileRow = i;
                    tileCol = j;
                }
            }
        }
        if (tileRow == -1) return false;
        if (!((Math.abs(tileRow - emptyRow) == 1 && tileCol == emptyCol) ||
                (Math.abs(tileCol - emptyCol) == 1 && tileRow == emptyRow))) {
            return false;
        }
        slide(tileRow, tileCol);
        return true;
    }

    private void initBoard() {
        int value = 1;
        // CHANGED: Loops use height and width
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = value++;
            }
        }
        // CHANGED: Empty space is at the bottom-right of the rectangular grid
        grid[height - 1][width - 1] = 0;
        emptyRow = height - 1;
        emptyCol = width - 1;
    }

    private void shuffleBoard() {
        // CHANGED: Number of shuffles is based on area
        for (int i = 0; i < width * height * 20; i++) {
            List<int[]> moves = validMoves();
            int[] move = moves.get(random.nextInt(moves.size()));
            slide(move[0], move[1]);
        }
    }

    private void slide(int row, int col) {
        grid[emptyRow][emptyCol] = grid[row][col];
        grid[row][col] = 0;
        emptyRow = row;
        emptyCol = col;
    }

    private List<int[]> validMoves() {
        List<int[]> moves = new ArrayList<>();
        // CHANGED: Boundary checks use height and width
        if (emptyRow > 0) moves.add(new int[]{emptyRow - 1, emptyCol});
        if (emptyRow < height - 1) moves.add(new int[]{emptyRow + 1, emptyCol});
        if (emptyCol > 0) moves.add(new int[]{emptyRow, emptyCol - 1});
        if (emptyCol < width - 1) moves.add(new int[]{emptyRow, emptyCol + 1});
        return moves;
    }
}

