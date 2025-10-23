import java.util.HashMap;
import java.util.Map;

/**
 * Tracks win/loss statistics for all players across different games.
 * This class maintains records of how many games each player has won and lost by game type.
 */
public class GameStats {
    private final Map<String, PlayerRecord> playerRecords;
    
    public GameStats() {
        this.playerRecords = new HashMap<>();
    }
    
    /**
     * Records a win for the specified player in a specific game.
     * @param playerName The name of the player who won
     * @param gameType The type of game (e.g., "Puzzle", "Dots and Boxes", "Quoridor")
     */
    public void recordWin(String playerName, String gameType) {
        PlayerRecord record = playerRecords.computeIfAbsent(playerName, k -> new PlayerRecord());
        record.incrementWins(gameType);
    }
    
    /**
     * Records a loss for the specified player in a specific game.
     * @param playerName The name of the player who lost
     * @param gameType The type of game (e.g., "Puzzle", "Dots and Boxes", "Quoridor")
     */
    public void recordLoss(String playerName, String gameType) {
        PlayerRecord record = playerRecords.computeIfAbsent(playerName, k -> new PlayerRecord());
        record.incrementLosses(gameType);
    }
    
    /**
     * Records a game quit (treated as a loss) for the specified player.
     * @param playerName The name of the player who quit
     * @param gameType The type of game
     */
    public void recordQuit(String playerName, String gameType) {
        recordLoss(playerName, gameType);
    }
    
    /**
     * Displays all player statistics by game type.
     */
    public void displayStats() {
        System.out.println("\n=== GAME STATISTICS ===");
        if (playerRecords.isEmpty()) {
            System.out.println("No games played yet.");
            return;
        }
        
        for (Map.Entry<String, PlayerRecord> entry : playerRecords.entrySet()) {
            String playerName = entry.getKey();
            PlayerRecord record = entry.getValue();
            
            System.out.println("\n" + playerName + "'s Statistics:");
            System.out.println("----------------------------------------");
            System.out.printf("%-20s %5s %7s %7s\n", "Game", "Wins", "Losses", "Total");
            System.out.println("----------------------------------------");
            
            int totalWins = 0;
            int totalLosses = 0;
            
            for (Map.Entry<String, GameRecord> gameEntry : record.getGameRecords().entrySet()) {
                String gameType = gameEntry.getKey();
                GameRecord gameRecord = gameEntry.getValue();
                int gameTotal = gameRecord.getWins() + gameRecord.getLosses();
                
                System.out.printf("%-20s %5d %7d %7d\n", 
                    gameType, gameRecord.getWins(), gameRecord.getLosses(), gameTotal);
                
                totalWins += gameRecord.getWins();
                totalLosses += gameRecord.getLosses();
            }
            
            System.out.println("----------------------------------------");
            System.out.printf("%-20s %5d %7d %7d\n", "TOTAL", totalWins, totalLosses, totalWins + totalLosses);
        }
        System.out.println("\n========================================");
    }
    
    /**
     * Gets the win count for a specific player and game type.
     * @param playerName The name of the player
     * @param gameType The type of game
     * @return The number of wins, or 0 if player hasn't played this game
     */
    public int getWins(String playerName, String gameType) {
        PlayerRecord record = playerRecords.get(playerName);
        return record != null ? record.getWins(gameType) : 0;
    }
    
    /**
     * Gets the loss count for a specific player and game type.
     * @param playerName The name of the player
     * @param gameType The type of game
     * @return The number of losses, or 0 if player hasn't played this game
     */
    public int getLosses(String playerName, String gameType) {
        PlayerRecord record = playerRecords.get(playerName);
        return record != null ? record.getLosses(gameType) : 0;
    }
    
    /**
     * Inner class to track individual player statistics across all games.
     */
    private static class PlayerRecord {
        private final Map<String, GameRecord> gameRecords;
        
        public PlayerRecord() {
            this.gameRecords = new HashMap<>();
        }
        
        public void incrementWins(String gameType) {
            GameRecord record = gameRecords.computeIfAbsent(gameType, k -> new GameRecord());
            record.incrementWins();
        }
        
        public void incrementLosses(String gameType) {
            GameRecord record = gameRecords.computeIfAbsent(gameType, k -> new GameRecord());
            record.incrementLosses();
        }
        
        public int getWins(String gameType) {
            GameRecord record = gameRecords.get(gameType);
            return record != null ? record.getWins() : 0;
        }
        
        public int getLosses(String gameType) {
            GameRecord record = gameRecords.get(gameType);
            return record != null ? record.getLosses() : 0;
        }
        
        public Map<String, GameRecord> getGameRecords() {
            return gameRecords;
        }
    }
    
    /**
     * Inner class to track statistics for a specific game type.
     */
    private static class GameRecord {
        private int wins;
        private int losses;
        
        public GameRecord() {
            this.wins = 0;
            this.losses = 0;
        }
        
        public void incrementWins() {
            wins++;
        }
        
        public void incrementLosses() {
            losses++;
        }
        
        public int getWins() {
            return wins;
        }
        
        public int getLosses() {
            return losses;
        }
    }
}