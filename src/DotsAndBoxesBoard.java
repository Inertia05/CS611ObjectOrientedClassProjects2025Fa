
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
            if (width < 1 || height < 1 || width > 10 || height > 10 ) {
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
