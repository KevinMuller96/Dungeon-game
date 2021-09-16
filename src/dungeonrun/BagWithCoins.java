package dungeonrun;

public class BagWithCoins extends Treasure {

    private static String TYPE = "BagsWithCoins";
    private static int VALUE = 6;
    private static double USUALITY = 0.2;

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
