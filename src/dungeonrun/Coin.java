
package dungeonrun;

public class Coin extends Treasure{

    private static String TYPE = "Coin";
    private static int VALUE = 2;
    private static double USUALITY = 0.4;

    @Override
    public int getPoints() {
        return VALUE;
    }

    public static double getUsuality() {
        return USUALITY;
    }
    
    @Override
    public String toString() {
        return "Type: " + TYPE + " Value: " + VALUE;
    }
}
