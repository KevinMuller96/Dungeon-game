package dungeonrun;

public class Orc extends Monster {

    private static final String TYPE = "Orc";
    private static final double USUALITY = 0.1;
    private static final int INITIATIVE = 6;
    private static final int ATTACK = 4;
    private static final int AGILITY = 4;
    
    private int initiativeSum = 0;
    private int health = 3;

    @Override
    public String getTYPE() {
        return TYPE;
    }

    public static double getUSUALITY() {
        return USUALITY;
    }

    @Override
    public int getINITIATIVE() {
        return INITIATIVE;
    }

    @Override
    public int getATTACK() {
        return ATTACK;
    }

    @Override
    public int getAGILITY() {
        return AGILITY;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void getHitFromHero() {
        health -= 1;
    }

    @Override
    public void monsterIntroduction() {
        System.out.println("I'm an Orc");
    }

    @Override
    public int getInitiativeSum() {
        return initiativeSum;
    }

    @Override
    public void setInitiativeSum(int initiativeSum) {
        this.initiativeSum = initiativeSum;
    }

    @Override
    public String toString() {
        return "\n*******************************"
                + "\n* MONSTER: "
                + "\n* Type: " + "\t" + TYPE
                + "\n* HP: " + "\t\t" + health
                + "\n* Attack Power: " + ATTACK
                + "\n* Agility: " + "\t" + AGILITY
                + "\n* Initiative: " + "\t" + INITIATIVE
                + "\n* Start sum: \t " + initiativeSum
                + "\n*******************************\n";
    }
}
