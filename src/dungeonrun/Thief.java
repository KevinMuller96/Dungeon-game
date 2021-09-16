package dungeonrun;

public class Thief extends PlayerCharacter {

    private static final String TYPE = "Thief";
    private static final int INITIATIVE = 7;
    private static final int STARTHEALTH = 5;
    private static final int STARTATTACK = 5;
    private static final int STARTAGILITY = 7;

    private int ATTACK = 5;
    private int AGILITY = 7;
    private int initiativeSum = 0;
    private int health = 5;

    public Thief(String name) {
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
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int endurance) {
        this.health = endurance;
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
    public String getTYPE() {
        return TYPE;
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
        System.out.println("   | | The Orphan has grown up mastering the arts of Theivery, though they use their          | |");
        System.out.println("   | | abilities for good as they protects other orphans from Monsters. The Orphan is agile,  | |");
        System.out.println("   | | able to sneak close to their enemy, which gives them a higher chance of a successful   | |");
        System.out.println("   | | attack - though not always. But at times, the Orphan might win in battle against the   | |");
        System.out.println("   | | monsters with just a single blow.                                                      | |");
        System.out.println(" __| |________________________________________________________________________________________| |__");
        System.out.println("(__   ________________________________________________________________________________________   __)");
        System.out.println("   | |                                                                                        | |");

    }

    @Override
    public String toString() {
        return "✦•.•:•.•:•☾․° ☣ °․☽•:•.•:•.•"
                + "\n✦◢✥◣◢✥◣◢✥◣◢✥◣◢✥◣◢✥◣"
                + "\n✦Name: " + getName()
                + "\n✦Character type: " + TYPE
                + "\n✦HP: " + health
                + "\n✦Attack power: " + ATTACK
                + "\n✦Agility: " + AGILITY
                + "\n✦Initiative: " + INITIATIVE
                + "\n✦Total points: " + getPoints()
                + "\n✦◥✥◤◥✥◤◥✥◤◥✥◤◥✥◤◥✥◤";
    }
}
