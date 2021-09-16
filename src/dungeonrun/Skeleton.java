package dungeonrun;

public class Skeleton extends Monster {

    private static final String TYPE = "Skeleton";
    private static final double USUALITY = 0.15;
    private static final int INITIATIVE = 4;
    private static final int ATTACK = 3;
    private static final int AGILITY = 3;
    
    private int initiativeSum = 0;
    private int health = 2;

    @Override
    public void monsterIntroduction() {
        System.out.println("I'm a Skeleton");
    }

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
