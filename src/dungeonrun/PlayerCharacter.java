package dungeonrun;

import java.io.Serializable;

public abstract class PlayerCharacter extends LivingCharacters implements Serializable {

    private String name;
    private int points;
    private int pointDuringGame;
    private int credits;

    private int totalTreasureFound;
    private int treasureFoundDuringGame;

    private int totalMonsterDefeated;
    private int totalVisitedRooms;

    public PlayerCharacter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }
    public void decreaseCredits(int cost) {
        credits -= cost;
    }
    public void collectPoints() {
        this.points += pointDuringGame;
        credits += pointDuringGame / 10;
        pointDuringGame = 0;
    }
    public int getCredits() {
        return credits;
    }
    public void collectTreasuresFound() {
        this.totalTreasureFound += treasureFoundDuringGame;
        treasureFoundDuringGame = 0;
    }
    public int getPointsDuringGame() {
        return pointDuringGame;
    }
    public void setPointsDuringGame(int points) {
        this.pointDuringGame += points;
    }
    public void resetPointsDuringGame() {
        pointDuringGame = 0;
    }
    public void increaseTreausureFound() {
        treasureFoundDuringGame++;
    }
    public int getTreasuresFoundDuringGame() {
        return treasureFoundDuringGame;
    }
    public void resetTreasuresFoundDuringGame() {
        treasureFoundDuringGame = 0;
    }
    public int getTotalTreasureFound() {
        return totalTreasureFound;
    }
    public void increaseTotalMonsterDefeated() {
        totalMonsterDefeated++;
    }
    public int getTotalMonsterDefeated() {
        return totalMonsterDefeated;
    }
    public void increaseTotalVisitedRooms() {
        totalVisitedRooms++;
    }
    
    public int getTotalVisitedRooms() {
        return totalVisitedRooms;
    }
    
    public abstract void getHitFromMonster();

    public abstract void restoreHP();

    public abstract void printStory();

    public abstract void buyAttack(int cost);

    public abstract void buyAgility(int cost);
    
    public abstract int getSTARTATTACK();
    
    public abstract int getSTARTAGILITY();

    @Override
    public abstract String toString();
}
