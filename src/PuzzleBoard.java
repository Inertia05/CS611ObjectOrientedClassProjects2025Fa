import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages the state and logic for a sliding puzzle game board.
 * This class handles the initialization of the grid, shuffling the tiles,
 * processing player moves, and checking for the solved state.
 */
public class PuzzleBoard extends Board {
    private final int[][] grid;
    private int emptySpaceRowIndex;
    private int emptySpaceColumnIndex;
    private final Random random = new Random();

    public PuzzleBoard(int width, int height) {
        super(width, height);
        if (width < 2 || width > 10 || height < 2 || height > 10) {
            throw new IllegalArgumentException("Board dimensions must be between 2x2 and 10x10.");
        }
        this.grid = new int[height][width];
        initializeBoard();
        shuffleBoard();
    }

    /**
     * Sets up the board in its initial, solved state with tiles in ascending order
     * and the empty space (represented by 0) in the bottom-right corner.
     */
    private void initializeBoard() {
        int currentTileValue = 1;
        for (int rowIndex = 0; rowIndex < height; rowIndex++) {
            for (int columnIndex = 0; columnIndex < width; columnIndex++) {
                grid[rowIndex][columnIndex] = currentTileValue++;
            }
        }
        // Place the empty space at the end
        grid[height - 1][width - 1] = 0;
        this.emptySpaceRowIndex = height - 1;
        this.emptySpaceColumnIndex = width - 1;
    }

    /**
     * Randomizes the board by making a large number of valid moves.
     */
    private void shuffleBoard() {
        int shuffleMoves = width * height * 20;
        for (int i = 0; i < shuffleMoves; i++) {
            List<int[]> possibleMoves = getValidMoves();
            int[] selectedMove = possibleMoves.get(random.nextInt(possibleMoves.size()));
            performSlide(selectedMove[0], selectedMove[1]);
        }
    }

    /**
     * Calculates the possible moves based on the current position of the empty space.
     * @return A list of valid coordinates [row, column] that can be slid into the empty space.
     */
    private List<int[]> getValidMoves() {
        List<int[]> validMovesList = new ArrayList<>();
        if (emptySpaceRowIndex > 0) validMovesList.add(new int[]{emptySpaceRowIndex - 1, emptySpaceColumnIndex});
        if (emptySpaceRowIndex < height - 1) validMovesList.add(new int[]{emptySpaceRowIndex + 1, emptySpaceColumnIndex});
        if (emptySpaceColumnIndex > 0) validMovesList.add(new int[]{emptySpaceRowIndex, emptySpaceColumnIndex - 1});
        if (emptySpaceColumnIndex < width - 1) validMovesList.add(new int[]{emptySpaceRowIndex, emptySpaceColumnIndex + 1});
        return validMovesList;
    }

    /**
     * Attempts to slide a given tile into the empty space.
     * @param tileValue The number on the tile the user wants to move.
     * @return true if the move was valid and performed, false otherwise.
     */
    public boolean slideTile(int tileValue) {
        if (tileValue <= 0 || tileValue >= width * height) return false;

        int tileRowIndex = -1, tileColumnIndex = -1;
        // Find the location of the requested tile
        for (int rowIndex = 0; rowIndex < height; rowIndex++) {
            for (int columnIndex = 0; columnIndex < width; columnIndex++) {
                if (grid[rowIndex][columnIndex] == tileValue) {
                    tileRowIndex = rowIndex;
                    tileColumnIndex = columnIndex;
                    break;
                }
            }
        }

        if (tileRowIndex == -1) return false; // Tile not found

        // Check if the found tile is adjacent to the empty space
        boolean isAdjacent = Math.abs(tileRowIndex - emptySpaceRowIndex) + Math.abs(tileColumnIndex - emptySpaceColumnIndex) == 1;
        if (isAdjacent) {
            performSlide(tileRowIndex, tileColumnIndex);
            return true;
        }
        return false;
    }

    /**
     * Swaps the tile at the given coordinates with the empty space.
     * @param sourceRow The row of the tile to slide.
     * @param sourceColumn The column of the tile to slide.
     */
    private void performSlide(int sourceRow, int sourceColumn) {
        grid[emptySpaceRowIndex][emptySpaceColumnIndex] = grid[sourceRow][sourceColumn];
        grid[sourceRow][sourceColumn] = 0;
        emptySpaceRowIndex = sourceRow;
        emptySpaceColumnIndex = sourceColumn;
    }

    /**
     * Checks if the board is in the solved configuration.
     * @return true if all tiles are in ascending order, false otherwise.
     */
    public boolean isSolved() {
        int expectedTileValue = 1;
        for (int rowIndex = 0; rowIndex < height; rowIndex++) {
            for (int columnIndex = 0; columnIndex < width; columnIndex++) {
                // For the last cell, check if it's the empty space
                if (rowIndex == height - 1 && columnIndex == width - 1) {
                    return grid[rowIndex][columnIndex] == 0;
                }
                // For all other cells, check if they hold the correct number
                if (grid[rowIndex][columnIndex] != expectedTileValue++) {
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
        StringBuilder boardBuilder = new StringBuilder();
        StringBuilder borderBuilder = new StringBuilder("+");
        for (int i = 0; i < width; i++) {
            borderBuilder.append("--+");
        }
        borderBuilder.append("\n");
        String horizontalBorder = borderBuilder.toString();

        boardBuilder.append(horizontalBorder);
        for (int rowIndex = 0; rowIndex < height; rowIndex++) {
            boardBuilder.append("|");
            for (int columnIndex = 0; columnIndex < width; columnIndex++) {
                int tileValue = grid[rowIndex][columnIndex];
                if (tileValue == 0) {
                    boardBuilder.append("  |");
                } else {
                    boardBuilder.append(String.format("%2d|", tileValue));
                }
            }
            boardBuilder.append("\n");
            boardBuilder.append(horizontalBorder);
        }
        return boardBuilder.toString();
    }
}