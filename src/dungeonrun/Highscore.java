package dungeonrun;

import java.util.ArrayList;
import java.util.Scanner;

public class Highscore {

    // <editor-fold defaultstate="collapsed" desc=" Colorcodes ">
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    // </editor-fold>

    private static ArrayList<PlayerCharacter> highscore = new ArrayList<>();
    private static final Scanner INPUT = new Scanner(System.in);

    public static void monstersDeafeated(ArrayList<PlayerCharacter> players) {

        for (PlayerCharacter k : players) {
            highscore.add(k);
        }

        for (int k = highscore.size() - 2; k >= 0; k--) {

            int highestPos = k;

            for (int i = highscore.size() - 1; i >= 0; i--) {
                if (highscore.get(i).getTotalMonsterDefeated() > highscore.get(highestPos).getTotalMonsterDefeated()) {
                    highestPos = i;
                    PlayerCharacter temp = highscore.get(k);
                    highscore.set(k, highscore.get(highestPos));
                    highscore.set(highestPos, temp);
                }
            }
        }

        System.out.println("Most defeated monsters: ");
        int counter = 1;
        for (int j = 0; j <= highscore.size() - 1; j++) {
            System.out.println(ANSI_YELLOW + "[" + counter + "]" + ANSI_RESET);
            System.out.println("Name: " + highscore.get(j).getName());
            System.out.println("Charactertype: " + highscore.get(j).getTYPE());
            System.out.println("Monster defeated: " + highscore.get(j).getTotalMonsterDefeated());
            counter++;
        }
        System.out.println(ANSI_GREEN + "<PRESS ENTER TO GO BACK TO MENU>" + ANSI_RESET);
        INPUT.nextLine();
        highscore.clear();
    }

    public static void mostTreasureFound(ArrayList<PlayerCharacter> players) {

        for (PlayerCharacter k : players) {
            highscore.add(k);
        }

        for (int k = highscore.size() - 2; k >= 0; k--) {

            int highestPos = k;

            for (int i = highscore.size() - 1; i >= 0; i--) {
                if (highscore.get(i).getTotalTreasureFound() > highscore.get(highestPos).getTotalTreasureFound()) {
                    highestPos = i;
                    PlayerCharacter temp = highscore.get(k);
                    highscore.set(k, highscore.get(highestPos));
                    highscore.set(highestPos, temp);
                }
            }
        }

        System.out.println("Most treasure found: ");
        int counter = 1;
        for (int j = 0; j <= highscore.size() - 1; j++) {
            System.out.println(ANSI_YELLOW + "[" + counter + "]" + ANSI_RESET);
            System.out.println("Name: " + highscore.get(j).getName());
            System.out.println("Charactertype: " + highscore.get(j).getTYPE());
            System.out.println("Treasure found: " + highscore.get(j).getTotalTreasureFound());
            counter++;
        }
        System.out.println(ANSI_GREEN + "<PRESS ENTER TO GO BACK TO MENU>" + ANSI_RESET);
        INPUT.nextLine();
        highscore.clear();
    }

    public static void mostVisitedRooms(ArrayList<PlayerCharacter> players) {

        for (PlayerCharacter k : players) {
            highscore.add(k);
        }

        for (int k = highscore.size() - 2; k >= 0; k--) {

            int highestPos = k;

            for (int i = highscore.size() - 1; i >= 0; i--) {
                if (highscore.get(i).getTotalVisitedRooms() > highscore.get(highestPos).getTotalVisitedRooms()) {
                    highestPos = i;
                    PlayerCharacter temp = highscore.get(k);
                    highscore.set(k, highscore.get(highestPos));
                    highscore.set(highestPos, temp);
                }
            }
        }

        System.out.println("Most visited rooms: ");
        int counter = 1;
        for (int j = 0; j <= highscore.size() - 1; j++) {
            System.out.println(ANSI_YELLOW + "[" + counter + "]" + ANSI_RESET);
            System.out.println("Name: " + highscore.get(j).getName());
            System.out.println("Charactertype: " + highscore.get(j).getTYPE());
            System.out.println("Visited rooms: " + highscore.get(j).getTotalVisitedRooms());
            counter++;
        }
        System.out.println(ANSI_GREEN + "<PRESS ENTER TO GO BACK TO MENU>" + ANSI_RESET);
        INPUT.nextLine();
        highscore.clear();
    }

    public static void mostPoints(ArrayList<PlayerCharacter> players) {

        for (PlayerCharacter k : players) {
            highscore.add(k);
        }

        for (int k = highscore.size() - 2; k >= 0; k--) {

            int highestPos = k;

            for (int i = highscore.size() - 1; i >= 0; i--) {
                if (highscore.get(i).getPoints() > highscore.get(highestPos).getPoints()) {
                    highestPos = i;
                    PlayerCharacter temp = highscore.get(k);
                    highscore.set(k, highscore.get(highestPos));
                    highscore.set(highestPos, temp);
                }
            }
        }

        System.out.println("Most visited rooms: ");
        int counter = 1;
        for (int j = 0; j <= highscore.size() - 1; j++) {
            System.out.println(ANSI_YELLOW + "[" + counter + "]" + ANSI_RESET);
            System.out.println("Name: " + highscore.get(j).getName());
            System.out.println("Charactertype: " + highscore.get(j).getTYPE());
            System.out.println("Points: " + highscore.get(j).getPoints());
            counter++;
        }
        System.out.println(ANSI_GREEN + "<PRESS ENTER TO GO BACK TO MENU>" + ANSI_RESET);
        INPUT.nextLine();
        highscore.clear();

    }
}
