package dungeonrun;

public class Wizard extends PlayerCharacter {

    private static final String TYPE = "Wizard";
    private static final int INITIATIVE = 6;
    private static final int STARTHEALTH = 4;
    private static final int STARTATTACK = 9;
    private static final int STARTAGILITY = 5;

    private int ATTACK = 9;
    private int AGILITY = 5;
    private int initiativeSum = 0;
    private int health = 4;



    public Wizard(String name) {
        super(name);
    }

    @Override
    public String getTYPE() {
        return TYPE;
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
        System.out.println("   | | The Sorcerer from the Time of Owls weilds magic. Magic that is used in battle against  | |");
        System.out.println("   | | the Dark World. The Sorcerer uses their knowlegde to fight with Light which blinds     | |");
        System.out.println("   | | these Creatures. While the Sorcerer may not be strong or fast, they use this Magic of  | |");
        System.out.println("   | | Light - lest they risk their life.                                                     | |");
        System.out.println("   | |                                                                                        | |");
        System.out.println(" __| |________________________________________________________________________________________| |__");
        System.out.println("(__   ________________________________________________________________________________________   __)");
        System.out.println("   | |                                                                                        | |");

    }

    @Override
    public String toString() {
        return " (∩｀-´)⊃━☆ﾟ.*･｡ﾟ"
                + "\n╔═══*.·:·.☽✧ ✦ ✧☾.·:·.*═══╗"
                + " \n║Name: " + getName()
                + " \n║Character type: " + TYPE
                + " \n║HP: " + health
                + " \n║Attack power: " + ATTACK
                + " \n║Agility: " + AGILITY
                + " \n║Initiative: " + INITIATIVE
                + " \n║Total points: " + getPoints()
                + " \n╚═══*.·:·.☽✧ ✦ ✧☾.·:·.*═══╝";
    }
}
