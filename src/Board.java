import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An abstract class representing a generic game board.
 * It contains nested classes for specific board game implementations.
 */
public abstract class Board {
    public abstract String getBoardAsString();

    public abstract boolean isGameOver();

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


    // ==              NESTED DOTS AND BOXES BOARD CLASS              ==
    public static class DotsAndBoxesBoard extends Board {
        // CHANGED: Replaced gridSize with width and height
        private final int gridWidth;
        private final int gridHeight;
        private final boolean[][] horizontalLines;
        private final boolean[][] verticalLines;
        private final Player[][] boxOwners;

        private final Player p1;
        private final Player p2;
        private int player1Score;
        private int player2Score;

        // CHANGED: Constructor now accepts width and height
        public DotsAndBoxesBoard(int width, int height, Player player1, Player player2) {
            if (width < 2 || height < 2) {
                throw new IllegalArgumentException("Grid dimensions must be at least 2.");
            }
            this.gridWidth = width;
            this.gridHeight = height;

            // CHANGED: Arrays are initialized with rectangular dimensions
            this.horizontalLines = new boolean[height][width - 1];
            this.verticalLines = new boolean[height - 1][width];
            this.boxOwners = new Player[height - 1][width - 1];

            this.p1 = player1;
            this.p2 = player2;
            this.player1Score = 0;
            this.player2Score = 0;
        }

        public int getScore(Player player) {
            return player.equals(p1) ? player1Score : player2Score;
        }

        public int drawLine(int r, int c, char direction, Player currentPlayer) {
            // CHANGED: Boundary checks use width and height
            if (direction == 'H') {
                if (r < 0 || r >= gridHeight || c < 0 || c >= gridWidth - 1)
                    throw new IllegalArgumentException("Line is out of bounds.");
                if (horizontalLines[r][c]) throw new IllegalArgumentException("Line is already taken.");
                horizontalLines[r][c] = true;
            } else { // 'V'
                if (r < 0 || r >= gridHeight - 1 || c < 0 || c >= gridWidth)
                    throw new IllegalArgumentException("Line is out of bounds.");
                if (verticalLines[r][c]) throw new IllegalArgumentException("Line is already taken.");
                verticalLines[r][c] = true;
            }

            return checkForCompletedBoxes(currentPlayer);
        }

        private int checkForCompletedBoxes(Player currentPlayer) {
            int boxesCompleted = 0;
            // CHANGED: Loops use height-1 and width-1 for box grid dimensions
            for (int r = 0; r < gridHeight - 1; r++) {
                for (int c = 0; c < gridWidth - 1; c++) {
                    if (boxOwners[r][c] == null &&
                            horizontalLines[r][c] &&      // Top
                            horizontalLines[r + 1][c] &&  // Bottom
                            verticalLines[r][c] &&        // Left
                            verticalLines[r][c + 1]) {    // Right

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
            // CHANGED: Total lines calculation for a rectangular grid
            int totalLines = gridHeight * (gridWidth - 1) + gridWidth * (gridHeight - 1);
            int drawnLines = 0;
            for (boolean[] row : horizontalLines) for (boolean line : row) if (line) drawnLines++;
            for (boolean[] row : verticalLines) for (boolean line : row) if (line) drawnLines++;
            return drawnLines == totalLines;
        }

        @Override
        public String getBoardAsString() {
            StringBuilder sb = new StringBuilder();
            // CHANGED: Loops use height and width
            for (int r = 0; r < gridHeight; r++) {
                for (int c = 0; c < gridWidth; c++) {
                    sb.append("●");
                    if (c < gridWidth - 1) {
                        sb.append(horizontalLines[r][c] ? "---" : "   ");
                    }
                }
                sb.append("\n");

                if (r < gridHeight - 1) {
                    for (int c = 0; c < gridWidth; c++) {
                        sb.append(verticalLines[r][c] ? "| " : "  ");
                        if (c < gridWidth - 1) {
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