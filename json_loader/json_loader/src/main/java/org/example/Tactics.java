package org.example;
import java.util.List;

public class Tactics {
    private int formation;
    private List<PlayerPosition> lineup;  // Assuming you have a PlayerPosition class that includes player details in context of a formation

    public int getFormation() {
        return formation;
    }

    public void setFormation(int formation) {
        this.formation = formation;
    }

    public List<PlayerPosition> getLineup() {
        return lineup;
    }

    public void setLineup(List<PlayerPosition> lineup) {
        this.lineup = lineup;
    }

    // Inner class to handle player positions within a lineup
    public static class PlayerPosition {
        private Player player;  // Assuming a Player class is defined elsewhere
        private Position position;  // Assuming a Position class is defined elsewhere
        private int jerseyNumber;

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        public Position getPosition() {
            return position;
        }

        public void setPosition(Position position) {
            this.position = position;
        }

        public int getJerseyNumber() {
            return jerseyNumber;
        }

        public void setJerseyNumber(int jerseyNumber) {
            this.jerseyNumber = jerseyNumber;
        }
    }
}
