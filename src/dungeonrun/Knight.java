package dungeonrun;

public class Knight extends PlayerCharacter {

    private static final String TYPE = "Knight";
    private static final int INITIATIVE = 5;
    private static final int STARTHEALTH = 9;
    private static final int STARTATTACK = 6;
    private static final int STARTAGILITY = 4;

    private int ATTACK = 6;
    private int AGILITY = 4;
    private int initiativeSum = 0;
    private int health = 9;

    public Knight(String name) {
        super(name);
    }

    @Override
    public int getSTARTATTACK() {
        return STARTATTACK;
    }

    @Override
    public int getSTARTAGILITY() {
        return STARTAGILITY;
    }

    @Override
    public int getINITIATIVE() {
        return INITIATIVE;
    }

    @Override
    public String getTYPE() {
        return TYPE;
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
    public void getHitFromMonster() {
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
    public void restoreHP() {
        health = STARTHEALTH;
    }

    @Override
    public void buyAttack(int cost) {
        this.ATTACK++;
        decreaseCredits(cost);
    }

    @Override
    public void buyAgility(int cost) {
        this.AGILITY++;
        decreaseCredits(cost);
    }

    @Override
    public void printStory() {

        System.out.println(" __| |________________________________________________________________________________________| |__");
        System.out.println("(__   ________________________________________________________________________________________   __)");
        System.out.println("   | |                                                                                        | |");
        System.out.println("   | | The Knight holds scars on their face, a sign of many battles fought. These battles     | |");
        System.out.println("   | | have strenghtened their stamina and hardened their resolve to never be afraid of       | |");
        System.out.println("   | | meeting these Monsters in battle. The Shield is this noble Knight's best friend,       | |");
        System.out.println("   | | protecting them from the first attack. However, their armor hinders the Knight's       | |");
        System.out.println("   | | movements - lowering their agility.                                                    | |");
        System.out.println(" __| |________________________________________________________________________________________| |__");
        System.out.println("(__   ________________________________________________________________________________________   __)");
        System.out.println("   | |                                                                                        | |");

    }

    @Override
    public String toString() {
        return " cxxx{}::::::::::::::::::::::::::::>"
                + "\n╔═══━━━─── ♚ ───━━━═══╗"
                + "\n║Name: " + getName()
                + "\n║Character type: " + TYPE
                + "\n║HP: " + health
                + "\n║Attack power: " + ATTACK
                + "\n║Agility: " + AGILITY
                + "\n║Initiative: " + INITIATIVE
                + "\n║Total points: " + getPoints()
                + "\n╚═══━━━─── ♚ ───━━━═══╝";
    }

}
