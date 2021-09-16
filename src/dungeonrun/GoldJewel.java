package dungeonrun;

public class GoldJewel extends Treasure {

    private static String TYPE = "GoldJewel";
    private static int VALUE = 10;
    private static double USUALITY = 0.15;

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
