package dungeonrun;

public class SmallChest extends Treasure {

    private static String TYPE = "SmallChest";
    private static int VALUE = 20;
    private static double USUALITY = 0.05;

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
