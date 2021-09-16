package dungeonrun;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * @TODO: bryt ut lite kod från konstruktorn i en metod
 */
public class Map {

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

    private static final int MAX_NR_MONSTERS_PER_ROOM = 3;
    private static final int MAX_NR_TREASURES_PER_ROOM = 1;

    private static Stack<Monster> monsters = new Stack<>();
    private static Stack<Treasure> treasures = new Stack<>();

    private static SecureRandom random = new SecureRandom();

    private static Scanner input = new Scanner(System.in);

    private int addedMonsters = 0;
    private int addedTreasures = 0;

    private static int monstersToBeFound;
    private static int treasuresToBeFound;

    private int choice;
    private int doneRooms;

    private static final Scanner scanner = new Scanner(System.in);

    private Room[][] map;

    private String[][] visualMap;

    private String visitedRoom = ANSI_YELLOW + "[★]" + ANSI_RESET;
    private String unvisitedRoom = "[✩]";
    private String playerPos = ANSI_GREEN + "[✰]" + ANSI_RESET;
    private String monstersLeftInRoom = ANSI_RED + "[✩]" + ANSI_RESET;

    private int x;
    private int previousX;
    private int y;
    private int previousY;

    public Map() {

        System.out.println("\tWhich castle do you want to enter?\n\t[1] - Highlord Umber (16 rooms)\n\t[2] - Nobleman Braxxus (25 rooms)\n\t[3] - Great Emperor Verrine (64 rooms)");
        System.out.println(ANSI_GREEN + "\tMake a choice: " + ANSI_RESET);

        choice = UserInput.getIntFromUser();

        switch (choice) {
            case 1:
                map = new Room[4][4];
                visualMap = new String[4][4];
                printStorySmallCastle();
                break;
            case 2:
                map = new Room[5][5];
                visualMap = new String[5][5];
                printStoryMediumCastle();
                break;
            case 3:
                map = new Room[8][8];
                visualMap = new String[8][8];
                printStoryBigCastle();
                break;
        }
    }

    public void createRooms() {

        for (Room[] rooms : map) {
            for (int j = 0; j < rooms.length; j++) {
                rooms[j] = new Room();
            }
        }

        // Nr of each monsters separately in the map:
        int nrOfRooms = map.length * map.length;

        int nrOfOrcs = (int) (nrOfRooms * Orc.getUSUALITY() * MAX_NR_MONSTERS_PER_ROOM + 0.5);
        int nrOfSkeletons = (int) (nrOfRooms * Skeleton.getUSUALITY() * MAX_NR_MONSTERS_PER_ROOM + 0.5);
        int nrOfSpiders = (int) (nrOfRooms * GiantSpider.getUSUALITY() * MAX_NR_MONSTERS_PER_ROOM + 0.5);
        int nrOfTrolls = (int) (nrOfRooms * Troll.getUSUALITY() * MAX_NR_MONSTERS_PER_ROOM + 0.5);

        int nrOfCoins = (int) (nrOfRooms * Coin.getUsuality() * MAX_NR_TREASURES_PER_ROOM + 0.5);
        int nrOfBagWithCoins = (int) (nrOfRooms * BagWithCoins.getUsuality() * MAX_NR_TREASURES_PER_ROOM + 0.5);
        int nrOfGoldJewel = (int) (nrOfRooms * GoldJewel.getUsuality() * MAX_NR_TREASURES_PER_ROOM + 0.5);
        int nrOfGemstone = (int) (nrOfRooms * Gemstone.getUsuality() * MAX_NR_TREASURES_PER_ROOM + 0.5);
        int nrOfSmallChest = (int) (nrOfRooms * SmallChest.getUsuality() * MAX_NR_TREASURES_PER_ROOM + 0.5);

        // Nr of all monsters in the map:
        monstersToBeFound = nrOfSpiders + nrOfOrcs + nrOfSkeletons + nrOfTrolls;

        // Nr of all treasures in the map:
        treasuresToBeFound = nrOfCoins + nrOfBagWithCoins + nrOfGoldJewel + nrOfGemstone + nrOfSmallChest;

        // Add monsters to stack
        for (int i = 0; i < nrOfOrcs; i++) {
            monsters.add(new Orc());
        }

        for (int i = 0; i < nrOfSkeletons; i++) {
            monsters.add(new Skeleton());
        }

        for (int i = 0; i < nrOfSpiders; i++) {
            monsters.add(new GiantSpider());
        }

        for (int i = 0; i < nrOfTrolls; i++) {
            monsters.add(new Troll());
        }

        // Add treasures to stack
        for (int i = 0; i < nrOfCoins; i++) {
            treasures.add(new Coin());
        }

        for (int i = 0; i < nrOfBagWithCoins; i++) {
            treasures.add(new BagWithCoins());
        }

        for (int i = 0; i < nrOfGoldJewel; i++) {
            treasures.add(new GoldJewel());
        }

        for (int i = 0; i < nrOfGemstone; i++) {
            treasures.add(new Gemstone());
        }

        for (int i = 0; i < nrOfSmallChest; i++) {
            treasures.add(new SmallChest());
        }

        shuffleMonsters();
        shuffleTreasures();

        /*Populate monsters in rooms */
        int nrMonstersGenerated;

        for (Room[] rooms : map) {
            for (Room room : rooms) {

                nrMonstersGenerated = random.nextInt(4);

                addedMonsters += nrMonstersGenerated;

                if (addedMonsters > monstersToBeFound) {
                    addedMonsters -= nrMonstersGenerated;
                    nrMonstersGenerated = monstersToBeFound - addedMonsters;
                    room.populateMonsterCages(nrMonstersGenerated);
                    addedMonsters += nrMonstersGenerated;
                    break;
                } else if (addedMonsters == monstersToBeFound) {
                    room.populateMonsterCages(nrMonstersGenerated);
                    break;
                } else if (monsters.isEmpty()) {
                    break;
                } else {
                    room.populateMonsterCages(nrMonstersGenerated);
                }
            }
        }

        int nrOfRows = map.length;
        if (addedMonsters < monstersToBeFound) {
            nrMonstersGenerated = monstersToBeFound - addedMonsters;
            int counter = nrMonstersGenerated;
            for (int i = 0; i < counter; i++) {
                int rowIndex = random.nextInt(nrOfRows);
                int columnIndex = random.nextInt(nrOfRows);
                if (map[rowIndex][columnIndex].monsterCages.size() != MAX_NR_MONSTERS_PER_ROOM) {
                    map[rowIndex][columnIndex].populateMonsterCages(1);
                    addedMonsters += 1;
                } else {
                    ++counter;
                }
            }
        }

        /* Add tresures to rooms in map */
        int nrTreasuresGenerated;

        for (Room[] rooms : map) {
            for (Room room : rooms) {

                nrTreasuresGenerated = random.nextInt(2);

                addedTreasures += nrTreasuresGenerated;

                if (addedTreasures > treasuresToBeFound) {
                    addedTreasures -= nrTreasuresGenerated;
                    nrTreasuresGenerated = treasuresToBeFound - addedTreasures;
                    room.populateTresure(nrTreasuresGenerated);
                    addedTreasures += nrTreasuresGenerated;
                    break;
                } else if (addedTreasures == treasuresToBeFound) {
                    room.populateTresure(nrTreasuresGenerated);
                    break;
                } else if (treasures.isEmpty()) {
                    break;
                } else {
                    room.populateTresure(nrTreasuresGenerated);
                }
            }
        }

        if (addedTreasures < treasuresToBeFound) {
            nrTreasuresGenerated = treasuresToBeFound - addedTreasures;
            int counter = nrTreasuresGenerated;
            for (int i = 0; i < counter; i++) {
                int rowIndex = random.nextInt(nrOfRows);
                int columnIndex = random.nextInt(nrOfRows);
                if (map[rowIndex][columnIndex].treasures.size() != MAX_NR_TREASURES_PER_ROOM) {
                    map[rowIndex][columnIndex].populateTresure(1);
                    addedTreasures += 1;
                } else {
                    ++counter;
                }
            }
        }
    }

    public void chooseStartPos() {

        System.out.println(ANSI_YELLOW + "Map instructions: " + ANSI_RESET);
        System.out.println("Your position = " + ANSI_GREEN + "[✰]" + ANSI_RESET);
        System.out.println("Unvisited = [✩]");
        System.out.println("Done = " + ANSI_YELLOW + "[★]" + ANSI_RESET);
        System.out.println("Monsters left = " + ANSI_RED + "[✩]\n" + ANSI_RESET);

        for (String[] vmap : visualMap) {
            for (int j = 0; j < vmap.length; j++) {
                vmap[j] = unvisitedRoom;
            }
        }

        visualMap[0][0] = ANSI_YELLOW + "[  1   ]" + ANSI_RESET;
        visualMap[0][visualMap.length - 1] = ANSI_YELLOW + "[   2  ]" + ANSI_RESET;
        visualMap[visualMap.length - 1][0] = ANSI_YELLOW + "[  3   ]" + ANSI_RESET;
        visualMap[visualMap.length - 1][visualMap.length - 1] = ANSI_YELLOW + "[   4  ]" + ANSI_RESET;

        printMap();

        for (String[] vmap : visualMap) {
            for (int j = 0; j < vmap.length; j++) {
                vmap[j] = unvisitedRoom;
            }
        }

        System.out.println(ANSI_GREEN + "The castle has 4 entrances, choose one entrance!" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "[1], [2], [3], [4]" + ANSI_RESET);

        int choice = UserInput.getIntFromUser();

        switch (choice) {
            case 1:
                y = 0;
                x = 0;
                map[0][visualMap.length - 1].hasExit();
                map[visualMap.length - 1][0].hasExit();
                map[visualMap.length - 1][visualMap.length - 1].hasExit();
                break;
            case 2:
                y = 0;
                x = visualMap.length - 1;
                map[0][0].hasExit();
                map[visualMap.length - 1][0].hasExit();
                map[visualMap.length - 1][visualMap.length - 1].hasExit();
                break;
            case 3:
                y = visualMap.length - 1;
                x = 0;
                map[0][0].hasExit();
                map[0][visualMap.length - 1].hasExit();
                map[visualMap.length - 1][visualMap.length - 1].hasExit();
                break;
            case 4:
                y = visualMap.length - 1;
                x = visualMap.length - 1;
                map[0][0].hasExit();
                map[0][visualMap.length - 1].hasExit();
                map[visualMap.length - 1][0].hasExit();
                break;
        }

        visualMap[y][x] = playerPos;

        System.out.println("You are now going to enter a room from the entrance you choose!");
        System.out.println("<PRESS ENTER IF YOU ARE READY>");
        input.nextLine();

//        int outCome = map[y][x].visitRoom();
//
//        if (outCome == 0) {
//            doneRooms++;
//        }
    }

    public void move() {

        boolean tryInput = true;

        System.out.println("Choose direction");

        System.out.println("[1] ⇨\t[2] ⇦\t[3] ⇧\t[4] ⇩ ");

        do {
            int choice = UserInput.getIntFromUser();

            visualMap[y][x] = visitedRoom;
            previousY = y;
            previousX = x;

            switch (choice) {
                case 1:
                    if (x == visualMap.length - 1) {
                        System.out.println(ANSI_RED + "Not an option" + ANSI_RESET);
                        break;
                    } else {
                        x++;
                        tryInput = false;
                    }
                    break;
                case 2:
                    if (x == 0) {
                        System.out.println(ANSI_RED + "Not an option" + ANSI_RESET);
                        break;
                    } else {
                        x--;
                        tryInput = false;
                    }
                    break;
                case 3:
                    if (y == 0) {
                        System.out.println(ANSI_RED + "Not an option" + ANSI_RESET);
                        break;
                    } else {
                        y--;
                        tryInput = false;
                    }
                    break;
                case 4:
                    if (y == visualMap.length - 1) {
                        System.out.println(ANSI_RED + "Not an option" + ANSI_RESET);
                        break;
                    } else {
                        y++;
                        tryInput = false;
                    }
                    break;
            }
        } while (tryInput);

        visualMap[y][x] = playerPos;

    }

    public int goInRoom() {

        int exit = 0;

        int outCome = map[y][x].visitRoom();  // Call on method in room klass
        
        switch (outCome) {
            case 0: // When you are done in a room
                doneRooms++;
                break;
            case 1: // If you escape from a room
                visualMap[y][x] = monstersLeftInRoom;

                if (previousX == x && previousY == y) {
                    System.out.println("You have run from the room with monsters, now comes the next room.");
                    System.out.println(ANSI_GREEN + "<PRESS ENTER TO CONTINUE>" + ANSI_RESET);
                    input.nextLine();
                    if (x == 0) {
                        x++;
                    } else {
                        x--;
                    }

                    visualMap[y][x] = playerPos;

                } else {
                    visualMap[previousY][previousX] = playerPos;
                    x = previousX;
                    y = previousY;
                }

                //map[y][x].visitRoom();  // PROBLEM
                
                exit = 2;
                
                break;
            case 2: // If you die in room or exit
                exit = 1;
                break;
            case 3:
                break;
        }

        if (doneRooms == map.length * map.length) { // When you are done with all rooms (should be tracked on some other way)
            System.out.println(ANSI_YELLOW + "CONGRATS!!!" + ANSI_RESET);
            System.out.println("You have survived throughout the castle. Good work!");
            System.out.println("Summary: ");
            System.out.println("Collected treasures: " + DungeonRun.getPlayerCharacter().getTreasuresFoundDuringGame());
            System.out.println("Collected treasures value: " + DungeonRun.getPlayerCharacter().getPointsDuringGame());
            DungeonRun.getPlayerCharacter().collectPoints();
            DungeonRun.getPlayerCharacter().collectTreasuresFound();
            System.out.println(ANSI_GREEN + "<PRESS ENTER TO CONTINUE>" + ANSI_RESET);
            input.nextLine();
            exit = 1;
        }

        return exit;
    }

    public void printMap() {

        for (String[] row : visualMap) {
            for (String c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    /**
     * Metod that shuffles all monsters in the stack "monsters" Each monster in
     * the stack "monsters" is flipped through in the for- loop and switches
     * place with a random monster in the stack.
     */
    public static void shuffleMonsters() {

        Monster temp;

        for (int first = 0; first < monsters.size(); first++) {

            int second = random.nextInt(monsters.size());

            temp = monsters.get(first);
            monsters.set(first, monsters.get(second));
            monsters.set(second, temp);
        }
    }

    /**
     * Metod that shuffles all treasures in the stack "treasure" Each treasure
     * in the stack "treasure" is flipped through in the for- loop and switches
     * place with a random treasure in the stack.
     */
    public static void shuffleTreasures() {

        Treasure temp; // temp obj for treasure

        for (int first = 0; first < treasures.size(); first++) {

            int second = random.nextInt(treasures.size());

            temp = treasures.get(first);
            treasures.set(first, treasures.get(second));
            treasures.set(second, temp);
        }
    }

    public static Stack<Monster> getMonsters() {
        return monsters;
    }

    public static Stack<Treasure> getTreasures() {
        return treasures;
    }

    public static void printStorySmallCastle() {
        // Story small castle: 
        System.out.println(" __| |________________________________________________________________________________________| |__");
        System.out.println("(__   ________________________________________________________________________________________   __)");
        System.out.println("   | |                                                                                        | |");
        System.out.println("   | | Within the walls surrounding the Great City of Oribos, lies a mansion. In this mansion,| |");
        System.out.println("   | | self-appointed Highlord Umber lives a lavish life - ruling all of the city with powers | |");
        System.out.println("   | | beyond the realm of Light. Highlord Umber and his monsters awaits those brave enough   | |");
        System.out.println("   | | to oppose him in the mansion. Should our Heroes choose to challenge the monters, it is | |");
        System.out.println("   | | with the fate of all citizens in Oribos resting a heavy burden on their shoulders.     | |");
        System.out.println(" __| |________________________________________________________________________________________| |__");
        System.out.println("(__   ________________________________________________________________________________________   __)");
        System.out.println("   | |                                                                                        | |");
        System.out.println(ANSI_GREEN + "<PRESS ENTER TO CONTINUE>" + ANSI_RESET);
        input.nextLine();
    }

    public static void printStoryMediumCastle() {
        // Story medium castle:
        System.out.println(" __| |________________________________________________________________________________________| |__");
        System.out.println("(__   ________________________________________________________________________________________   __)");
        System.out.println("   | |                                                                                        | |");
        System.out.println("   | | Nobleman Braxxus rules a smaller Kingdom, the Kingdom of Nespiria, driving it to ruin  | |");
        System.out.println("   | | with the help of Monsters, and driving it to poverty with insane taxes. On the stone   | |");
        System.out.println("   | | walls och all around the rooms, hangs tapestries detailing all his wicked and sick     | |");
        System.out.println("   | | deeds like a morbid collection of trophies. It is in this rooms our Heroes can defeat  | |");
        System.out.println("   | | the Evils that has taken control of Nespiria.                                          | |");
        System.out.println(" __| |________________________________________________________________________________________| |__");
        System.out.println("(__   ________________________________________________________________________________________   __)");
        System.out.println("   | |                                                                                        | |");
        System.out.println(ANSI_GREEN + "<PRESS ENTER TO CONTINUE>" + ANSI_RESET);
        input.nextLine();
    }

    public static void printStoryBigCastle() {
        // Story big castle:
        System.out.println(" __| |________________________________________________________________________________________| |__");
        System.out.println("(__   ________________________________________________________________________________________   __)");
        System.out.println("   | |                                                                                        | |");
        System.out.println("   | | Within the biggest castle, in the largest kingdom of Darkness, the Great Emperor lives.| |");
        System.out.println("   | | It is from here that Emperor Verrine rules, using his Tyrannical Advisor Ornias -      | |");
        System.out.println("   | | a demonic shapeshifter - to keep all his subjects under control. Should the Heroes     | |");
        System.out.println("   | | choose to enter the rooms, Emperor Verrines monsters will wait for them, large and     | |");
        System.out.println("   | | embellished with dangerus weapon. In the rooms, our Heroes must face                   | |");
        System.out.println("   | | the monsters of emperor to fight for the survival of all Humanity.                     | |");
        System.out.println(" __| |________________________________________________________________________________________| |__");
        System.out.println("(__   ________________________________________________________________________________________   __)");
        System.out.println("   | |                                                                                        | |");
        System.out.println(ANSI_GREEN + "<PRESS ENTER TO CONTINUE>" + ANSI_RESET);
        input.nextLine();
    }

    public static void punchSound() {
        PlaySound pS = new PlaySound("punch.wav");
        Thread punchSound = new Thread(pS, "T1");
        punchSound.start();
    }

    public static void treasureSound() {
        PlaySound treasureSound = new PlaySound("treasure.wav");
        Thread pickupTreasure = new Thread(treasureSound, "T2");
        pickupTreasure.start();
    }

}
