package dungeonrun;

import java.util.Random;

// Superklass för alla karaktärer i spelet
public abstract class LivingCharacters {
   
    public LivingCharacters() {}

    public static int roll(int times) {

        // Create a Random class object.
        Random dieRoll = new Random();

        int dieSum = 0;

        for (int time = 1; time <= times; time++) {
            // Get a random integer value for the dice between 1 and 6.
            int dieValue = dieRoll.nextInt(6) + 1;
            dieSum = dieSum + dieValue;
        }
        return dieSum;
    }

    public abstract String getTYPE();
    
    public abstract int getINITIATIVE();

    public abstract int getATTACK();

    public abstract int getAGILITY();
    
    public abstract int getHealth();
    
    public abstract void setHealth(int health);
    
    public abstract int getInitiativeSum();
    
    public abstract void setInitiativeSum(int initiativeSum);
    
    @Override
    public abstract String toString();
    
}
