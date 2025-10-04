import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PuzzleBoard extends Board {
    private final int[][] grid;
    private int emptyRow, emptyCol;
    private final Random random = new Random();

    public PuzzleBoard(int width, int height) {
        super(width, height);
        if (width < 2 || width > 10 || height < 2 || height > 10) {
            throw new IllegalArgumentException("Board dimensions must be between 2x2 and 10x10.");
        }
        this.grid = new int[height][width];
        initBoard();
        shuffleBoard();
    }

    private void initBoard() {
        int value = 1;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = value++;
            }
        }
        grid[height - 1][width - 1] = 0;
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

    private List<int[]> getValidMoves() {
        List<int[]> moves = new ArrayList<>();
        if (emptyRow > 0) moves.add(new int[]{emptyRow - 1, emptyCol});
        if (emptyRow < height - 1) moves.add(new int[]{emptyRow + 1, emptyCol});
        if (emptyCol > 0) moves.add(new int[]{emptyRow, emptyCol - 1});
        if (emptyCol < width - 1) moves.add(new int[]{emptyRow, emptyCol + 1});
        return moves;
    }

    public boolean slideTile(int tile) {
        if (tile <= 0 || tile >= width * height) return false;

        int tileRow = -1, tileCol = -1;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == tile) {
                    tileRow = i;
                    tileCol = j;
                    break;
                }
            }
        }

        if (tileRow == -1) return false;

        // Check if adjacent to empty space
        if (Math.abs(tileRow - emptyRow) + Math.abs(tileCol - emptyCol) == 1) {
            slide(tileRow, tileCol);
            return true;
        }
        return false;
    }

    private void slide(int row, int col) {
        grid[emptyRow][emptyCol] = grid[row][col];
        grid[row][col] = 0;
        emptyRow = row;
        emptyCol = col;
    }

    public boolean isSolved() {
        int value = 1;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == height - 1 && j == width - 1) {
                    return grid[i][j] == 0;
                }
                if (grid[i][j] != value++) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isGameOver() {
        return isSolved();
    }

    @Override
    public String getBoardAsString() {
        StringBuilder sb = new StringBuilder();
        String line = "+" + "--+".repeat(width) + "\n";
        sb.append(line);
        for (int i = 0; i < height; i++) {
            sb.append("|");
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == 0) {
                    sb.append("  |");
                } else {
                    sb.append(String.format("%2d|", grid[i][j]));
                }
            }
            sb.append("\n");
            sb.append(line);
        }
        return sb.toString();
    }
}