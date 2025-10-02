import java.util.*;

/**
 * Manages the state, logic, and rules for an N x N sliding puzzle board.
 */
public class Board {
    private final int size;
    private final int[][] grid;
    private int emptyRow, emptyCol;
    private final Random random = new Random();

    public Board(int size) {
        if (size < 2 || size > 5) {
            throw new IllegalArgumentException("Board size must be between 2 and 5.");
        }
        this.size = size;
        this.grid = new int[size][size];
        initBoard();
        shuffleBoard();
    }

    private void initBoard() {
        int value = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = value++;
            }
        }
        grid[size - 1][size - 1] = 0; // empty space
        emptyRow = size - 1;
        emptyCol = size - 1;
    }

    private void shuffleBoard() {
        for (int i = 0; i < size * size * 20; i++) {
            List<int[]> moves = validMoves();
            int[] move = moves.get(random.nextInt(moves.size()));
            slide(move[0], move[1]);
        }
    }

    public String getBoardAsString() {
        StringBuilder sb = new StringBuilder();
        // fixing: Replaced String.repeat() with a Java 8 compatible loop.
        StringBuilder lineBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            lineBuilder.append("+--");
        }
        lineBuilder.append("+\n");
        String line = lineBuilder.toString();

        sb.append(line);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
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

    public boolean isSolved() {
        int value = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size - 1 && j == size - 1) {
                    if (grid[i][j] != 0) return false;
                } else if (grid[i][j] != value++) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean slideTile(int tile) {
        if (tile <= 0 || tile > size * size - 1) {
            return false;
        }

        int tileRow = -1, tileCol = -1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == tile) {
                    tileRow = i;
                    tileCol = j;
                }
            }
        }

        if (tileRow == -1) {
            // This case should ideally not be reachable if the tile number is valid
            return false;
        }

        if (!((Math.abs(tileRow - emptyRow) == 1 && tileCol == emptyCol) ||
                (Math.abs(tileCol - emptyCol) == 1 && tileRow == emptyRow))) {
            return false;
        }

        slide(tileRow, tileCol);
        return true;
    }

    private void slide(int row, int col) {
        grid[emptyRow][emptyCol] = grid[row][col];
        grid[row][col] = 0;
        emptyRow = row;
        emptyCol = col;
    }

    private List<int[]> validMoves() {
        List<int[]> moves = new ArrayList<>();
        if (emptyRow > 0) moves.add(new int[]{emptyRow - 1, emptyCol});
        if (emptyRow < size - 1) moves.add(new int[]{emptyRow + 1, emptyCol});
        if (emptyCol > 0) moves.add(new int[]{emptyRow, emptyCol - 1});
        if (emptyCol < size - 1) moves.add(new int[]{emptyRow, emptyCol + 1});
        return moves;
    }
}