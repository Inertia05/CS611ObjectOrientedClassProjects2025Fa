import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An abstract class representing a generic game board.
 * It contains nested classes for specific board game implementations.
 */
public abstract class Board {
    // ... (This part is the same)
    public abstract String getBoardAsString();
    public abstract boolean isGameOver();

    // ==                  NESTED PUZZLE BOARD CLASS                  ==
    public static class PuzzleBoard extends Board {
        // ... (Your PuzzleBoard code remains here, unchanged)
        private final int size;
        private final int[][] grid;
        private int emptyRow, emptyCol;
        private final Random random = new Random();

        public PuzzleBoard(int size) {
            if (size < 2 || size > 5) {
                throw new IllegalArgumentException("Board size must be between 2 and 5.");
            }
            this.size = size;
            this.grid = new int[size][size];
            initBoard();
            shuffleBoard();
        }

        @Override
        public String getBoardAsString() {
            StringBuilder sb = new StringBuilder();
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

        @Override
        public boolean isGameOver() {
            return isSolved();
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
            if (tile <= 0 || tile > size * size - 1) return false;
            int tileRow = -1, tileCol = -1;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
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
            grid[emptyRow][emptyCol] = grid[tileRow][tileCol];
            grid[tileRow][tileCol] = 0;
            emptyRow = tileRow;
            emptyCol = tileCol;
            return true;
        }

        private void initBoard() {
            int value = 1;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    grid[i][j] = value++;
                }
            }
            grid[size - 1][size - 1] = 0;
            emptyRow = size - 1;
            emptyCol = size - 1;
        }

        private void shuffleBoard() {
            for (int i = 0; i < size * size * 20; i++) {
                List<int[]> moves = new ArrayList<>();
                if (emptyRow > 0) moves.add(new int[]{emptyRow - 1, emptyCol});
                if (emptyRow < size - 1) moves.add(new int[]{emptyRow + 1, emptyCol});
                if (emptyCol > 0) moves.add(new int[]{emptyRow, emptyCol - 1});
                if (emptyCol < size - 1) moves.add(new int[]{emptyRow, emptyCol + 1});
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
    }


    // ==              NESTED DOTS AND BOXES BOARD CLASS              ==
    public static class DotsAndBoxesBoard extends Board {
        private final int gridSize;
        private final boolean[][] horizontalLines;
        private final boolean[][] verticalLines;
        private final Player[][] boxOwners;

        private final Player p1;
        private final Player p2;
        private int player1Score;
        private int player2Score;

        // FIXED: Updated constructor to accept players
        public DotsAndBoxesBoard(int size, Player player1, Player player2) {
            if (size < 2) {
                throw new IllegalArgumentException("Grid size must be at least 2.");
            }
            this.gridSize = size;
            int boxGridSize = size - 1;

            this.horizontalLines = new boolean[gridSize][boxGridSize];
            this.verticalLines = new boolean[boxGridSize][gridSize];
            this.boxOwners = new Player[boxGridSize][boxGridSize];

            this.p1 = player1;
            this.p2 = player2;
            this.player1Score = 0;
            this.player2Score = 0;
        }

        // FIXED: Added getScore method
        public int getScore(Player player) {
            if (player.equals(p1)) {
                return player1Score;
            } else {
                return player2Score;
            }
        }

        // FIXED: Added drawLine method
        public int drawLine(int r, int c, char direction, Player currentPlayer) {
            if (direction == 'H') {
                if (r < 0 || r >= gridSize || c < 0 || c >= gridSize - 1) throw new IllegalArgumentException("Line is out of bounds.");
                if (horizontalLines[r][c]) throw new IllegalArgumentException("Line is already taken.");
                horizontalLines[r][c] = true;
            } else { // 'V'
                if (r < 0 || r >= gridSize - 1 || c < 0 || c >= gridSize) throw new IllegalArgumentException("Line is out of bounds.");
                if (verticalLines[r][c]) throw new IllegalArgumentException("Line is already taken.");
                verticalLines[r][c] = true;
            }

            return checkForCompletedBoxes(currentPlayer);
        }

        private int checkForCompletedBoxes(Player currentPlayer) {
            int boxesCompleted = 0;
            for (int r = 0; r < gridSize - 1; r++) {
                for (int c = 0; c < gridSize - 1; c++) {
                    // Check if the box at (r,c) is newly completed
                    if (boxOwners[r][c] == null &&
                            horizontalLines[r][c] &&      // Top line
                            horizontalLines[r + 1][c] &&  // Bottom line
                            verticalLines[r][c] &&        // Left line
                            verticalLines[r][c + 1]) {    // Right line

                        boxOwners[r][c] = currentPlayer;
                        if (currentPlayer.equals(p1)) {
                            player1Score++;
                        } else {
                            player2Score++;
                        }
                        boxesCompleted++;
                    }
                }
            }
            return boxesCompleted;
        }

        @Override
        public boolean isGameOver() {
            int totalLines = (gridSize * (gridSize - 1)) * 2;
            int drawnLines = 0;
            for (boolean[] row : horizontalLines) for (boolean line : row) if (line) drawnLines++;
            for (boolean[] row : verticalLines) for (boolean line : row) if (line) drawnLines++;
            return drawnLines == totalLines;
        }

        @Override
        public String getBoardAsString() {
            StringBuilder sb = new StringBuilder();
            for (int r = 0; r < gridSize; r++) {
                // Draw dots and horizontal lines
                for (int c = 0; c < gridSize; c++) {
                    sb.append("●");
                    if (c < gridSize - 1) {
                        sb.append(horizontalLines[r][c] ? "---" : "   ");
                    }
                }
                sb.append("\n");

                // Draw vertical lines and box owners
                if (r < gridSize - 1) {
                    for (int c = 0; c < gridSize; c++) {
                        sb.append(verticalLines[r][c] ? "| " : "  ");
                        if (c < gridSize - 1) {
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
    }
}