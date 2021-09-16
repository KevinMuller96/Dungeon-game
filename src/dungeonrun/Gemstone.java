package dungeonrun;

public class Gemstone extends Treasure {

    private static String TYPE = "Gemstone";
    private static int VALUE = 14;
    private static double USUALITY = 0.1;

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
