import java.util.LinkedList;
import java.util.Queue;

/**
 * Manages the state and logic of the Quoridor game board.
 * Tracks pawn positions, wall placements, wall counts, and rules validation.
 */
public class QuoridorBoard extends Board {

    private final Player playerOne, playerTwo;
    private int p1Row, p1Col, p2Row, p2Col;
    private int p1WallCount, p2WallCount;

    // horizontalWalls[0] is between row 0 and 1
    // horizontalWalls[7] is between row 7 and 8
    private final boolean[][] horizontalWalls; // [8][9]

    // verticalWalls[0] is between col 0 and 1
    // verticalWalls[7] is between col 7 and 8
    private final boolean[][] verticalWalls;   // [9][8]

    public QuoridorBoard(Player player1, Player player2) {
        super(9, 9); // 9x9 grid
        this.playerOne = player1;
        this.playerTwo = player2;
        this.p1WallCount = 10;
        this.p2WallCount = 10;

        // P1 (Team 1) starts at 'e1' (row 0, col 4) and wins by reaching row 8
        this.p1Row = 0;
        this.p1Col = 4;

        // P2 (Team 2) starts at 'e9' (row 8, col 4) and wins by reaching row 0
        this.p2Row = 8;
        this.p2Col = 4;

        this.horizontalWalls = new boolean[8][9];
        this.verticalWalls = new boolean[9][8];
    }

    public int getWallCount(Player player) {
        return player.equals(playerOne) ? p1WallCount : p2WallCount;
    }

    @Override
    public boolean isGameOver() {
        return p1Row == 8 || p2Row == 0;
    }

    public boolean performAction(String userInput, Player player) {
        String[] parts = userInput.split(" ");
        if (parts.length == 0) throw new IllegalArgumentException("No command entered.");

        String command = parts[0];
        if (command.equals("move") && parts.length == 2) {
            return movePawn(parts[1], player);
        } else if (command.equals("wall") && parts.length == 3) {
            return placeWall(parts[1], parts[2].charAt(0), player);
        } else {
            throw new IllegalArgumentException("Invalid command format.");
        }
    }

    // --- Pawn Movement Logic ---

    private boolean movePawn(String coord, Player player) {
        int[] target = parseCoord(coord);
        int tRow = target[0];
        int tCol = target[1];

        int cRow, cCol, oRow, oCol;
        if (player.equals(playerOne)) {
            cRow = p1Row; cCol = p1Col; oRow = p2Row; oCol = p2Col;
        } else {
            cRow = p2Row; cCol = p2Col; oRow = p1Row; oCol = p1Col;
        }

        int dr = tRow - cRow;
        int dc = tCol - cCol;

        boolean isOneSpaceOrthogonal = (Math.abs(dr) == 1 && dc == 0) || (dr == 0 && Math.abs(dc) == 1);
        if (!isOneSpaceOrthogonal) {
            throw new IllegalArgumentException("Pawns can only move 1 space up, down, left, or right.");
        }

        if (tRow == oRow && tCol == oCol) {
            throw new IllegalArgumentException("Cannot move onto the other player's square.");
            // Note: This is where jump logic would go.
        }

        if (isWallBetween(cRow, cCol, tRow, tCol)) {
            throw new IllegalArgumentException("A wall is blocking that move.");
        }

        if (player.equals(playerOne)) {
            p1Row = tRow; p1Col = tCol;
        } else {
            p2Row = tRow; p2Col = tCol;
        }
        return true;
    }

    private boolean isWallBetween(int r1, int c1, int r2, int c2) {
        if (r2 == r1 + 1) return horizontalWalls[r1][c1]; // Moving down (e.g., r=0 to r=1), check h-wall[0]
        if (r2 == r1 - 1) return horizontalWalls[r1 - 1][c1]; // Moving up (e.g., r=1 to r=0), check h-wall[0]
        if (c2 == c1 + 1) return verticalWalls[r1][c1]; // Moving right (e.g., c=0 to c=1), check v-wall[0]
        if (c2 == c1 - 1) return verticalWalls[r1][c1 - 1]; // Moving left (e.g., c=1 to c=0), check v-wall[0]
        return false;
    }

    // --- Wall Placement Logic ---

    private boolean placeWall(String coord, char direction, Player player) {
        if (getWallCount(player) <= 0) {
            throw new IllegalArgumentException("You have no walls left.");
        }

        int[] c = parseCoord(coord);
        int row = c[0]; // This is the row of the pawn square
        int col = c[1]; // This is the col of the pawn square

        if (direction == 'h') {
            // 'e2 h' -> row=1, col=4. Places wall *above* 'e2'
            // This is horizontalWalls[0][4] and horizontalWalls[0][5]
            if (row == 0) throw new IllegalArgumentException("Cannot place horizontal wall at the bottom edge.");
            if (col > 7) throw new IllegalArgumentException("Cannot place horizontal wall at the right edge.");

            int wallRow = row - 1; // Wall is *above* the given row

            if (horizontalWalls[wallRow][col] || horizontalWalls[wallRow][col + 1]) {
                throw new IllegalArgumentException("A wall is already there.");
            }
            if (col > 0 && verticalWalls[row][col-1] && verticalWalls[row-1][col-1]) {
                throw new IllegalArgumentException("Cannot place a wall that crosses another.");
            }

            horizontalWalls[wallRow][col] = true;
            horizontalWalls[wallRow][col + 1] = true;
            if (!doesPlayerHavePath(playerOne, 8) || !doesPlayerHavePath(playerTwo, 0)) {
                horizontalWalls[wallRow][col] = false;
                horizontalWalls[wallRow][col + 1] = false;
                throw new IllegalArgumentException("Placing this wall would block all paths to a goal.");
            }

        } else if (direction == 'v') {
            // 'b1 v' -> row=0, col=1. Places wall *left* of 'b1'
            // This is verticalWalls[0][0] and verticalWalls[1][0]
            if (col == 0) throw new IllegalArgumentException("Cannot place vertical wall at the left edge.");
            if (row > 7) throw new IllegalArgumentException("Cannot place vertical wall at the top edge.");

            int wallCol = col - 1; // Wall is *left* of the given col

            if (verticalWalls[row][wallCol] || verticalWalls[row + 1][wallCol]) {
                throw new IllegalArgumentException("A wall is already there.");
            }
            if (row > 0 && horizontalWalls[row-1][col] && horizontalWalls[row-1][col-1]) {
                throw new IllegalArgumentException("Cannot place a wall that crosses another.");
            }

            verticalWalls[row][wallCol] = true;
            verticalWalls[row + 1][wallCol] = true;
            if (!doesPlayerHavePath(playerOne, 8) || !doesPlayerHavePath(playerTwo, 0)) {
                verticalWalls[row][wallCol] = false;
                verticalWalls[row + 1][wallCol] = false;
                throw new IllegalArgumentException("Placing this wall would block all paths to a goal.");
            }
        } else {
            throw new IllegalArgumentException("Direction must be 'h' or 'v'.");
        }

        if (player.equals(playerOne)) p1WallCount--;
        else p2WallCount--;

        return true;
    }

    private boolean doesPlayerHavePath(Player player, int goalRow) {
        int startRow = (player.equals(playerOne)) ? p1Row : p2Row;
        int startCol = (player.equals(playerOne)) ? p1Col : p2Col;

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[9][9];
        queue.add(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        int[] dRows = {-1, 1, 0, 0}; // up, down
        int[] dCols = {0, 0, -1, 1}; // left, right

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];

            if (r == goalRow) return true;

            for (int i = 0; i < 4; i++) {
                int nr = r + dRows[i];
                int nc = c + dCols[i];
                if (nr < 0 || nr > 8 || nc < 0 || nc > 8) continue;
                if (visited[nr][nc]) continue;
                if (!isWallBetween(r, c, nr, nc)) {
                    visited[nr][nc] = true;
                    queue.add(new int[]{nr, nc});
                }
            }
        }
        return false;
    }


    // --- Coordinate and Rendering Logic ---

    private int[] parseCoord(String coord) {
        if (coord.length() != 2) throw new IllegalArgumentException("Coordinate must be 2 characters (e.g., 'e1').");
        char c = coord.charAt(0);
        char r = coord.charAt(1);
        if (c < 'a' || c > 'i' || r < '1' || r > '9') {
            throw new IllegalArgumentException("Invalid coordinate. Must be 'a'-'i' and '1'-'9'.");
        }
        return new int[]{r - '1', c - 'a'}; // [row, col]
    }

    /**
     * This is the new, corrected rendering method.
     */
    @Override
    public String getBoardAsString() {
        StringBuilder sb = new StringBuilder();
        String hWall = "===";
        String hGroove = "---";
        String vWall = "‖";
        String vGroove = "|";
        String p1 = " 1 ";
        String p2 = " 2 ";
        String empty = "   ";

        // Top labels
        sb.append("   a   b   c   d   e   f   g   h   i\n");
        // Top static border
        sb.append("  +---+---+---+---+---+---+---+---+---+\n");

        // Loop from the top row (r=8) down to the bottom (r=0)
        for (int r = 8; r >= 0; r--) {

            // --- Draw Pawn Row ---
            sb.append(r + 1).append(" "); // Row label (e.g., "9 ")
            for (int c = 0; c < 9; c++) {
                // Check for vertical wall to the LEFT
                boolean wallLeft = (c > 0) && verticalWalls[r][c - 1];
                sb.append(wallLeft ? vWall : vGroove);

                // Draw pawn or empty space
                if (p1Row == r && p1Col == c) sb.append(p1);
                else if (p2Row == r && p2Col == c) sb.append(p2);
                else sb.append(empty);
            }
            sb.append(vGroove).append(" ").append(r + 1).append("\n"); // Rightmost border and label

            // --- Draw Horizontal Wall Row ---
            if (r > 0) {
                sb.append("  +"); // Leftmost corner
                for (int c = 0; c < 9; c++) {
                    // Check for horizontal wall BELOW this row (i.e., above row r-1)
                    boolean wallBelow = horizontalWalls[r - 1][c];
                    sb.append(wallBelow ? hWall : hGroove).append("+");
                }
                sb.append(" ").append(r).append("\n"); // Label for row below
            }
        }

        // Bottom static border
        sb.append("  +---+---+---+---+---+---+---+---+---+\n");
        // Bottom labels
        sb.append("   a   b   c   d   e   f   g   h   i\n");

        return sb.toString();
    }
}
