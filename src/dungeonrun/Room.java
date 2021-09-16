package dungeonrun;

import java.util.*;

public class Room {

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

    private static int roomCounter = 1;
    private int roomID = 0;
    private boolean visited = false;
    private boolean done = false;
    private boolean block = false;
    boolean escapeSuccess = false;
    private boolean hasExit = false;
    private boolean heroAlive = true;
    private LivingCharacters fightNow;

    ArrayList<Monster> monsterCages = new ArrayList<>();
    ArrayList<Treasure> treasures = new ArrayList<>();
    ArrayList<LivingCharacters> battleCage = new ArrayList<>();

    Scanner input = new Scanner(System.in);

    public Room() {
        roomID = roomCounter;
        roomCounter++;
    }

    public void populateMonsterCages(int nrMonstersGenerated) {
        for (int i = 0; i < nrMonstersGenerated; i++) {
            monsterCages.add(Map.getMonsters().pop());
        }
    }

    public void populateTresure(int nrTreasuresGenerated) {
        for (int i = 0; i < nrTreasuresGenerated; i++) {
            treasures.add(Map.getTreasures().pop());
        }
    }

    public boolean fightMonster() {

        boolean monstersLeft = true;
        boolean tryEscape = false;
        escapeSuccess = false;

        if (battleCage.size() != 1) {
            System.out.println("*********************************************************************");
            System.out.println(ANSI_PURPLE + "YOUR TURN!!!" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "Do you wanna try to escape!? (Y/N)" + ANSI_RESET);
            tryEscape = UserInput.getYOrNFromUser();
        }

        if (tryEscape) {
            escapeSuccess = tryEscape();
            if (escapeSuccess) {
                monstersLeft = false;
            }
        } else if (!escapeSuccess) {

            for (LivingCharacters activeCharacter : battleCage) {

                if (activeCharacter instanceof Monster) {
                    System.out.println("You now fight against " + activeCharacter.getTYPE() + "! (Health: " + activeCharacter.getHealth() + " Agility: " + activeCharacter.getAGILITY() + ")");
                    System.out.println(ANSI_GREEN + "\n<PRESS ENTER TO HIT " + activeCharacter.getTYPE() + ">" + ANSI_RESET);
                    input.nextLine();
                    Map.punchSound();

                    int attack = LivingCharacters.roll(DungeonRun.getPlayerCharacter().getATTACK());
                    int agility = LivingCharacters.roll(activeCharacter.getAGILITY());

                    if (attack >= agility) {
                        int doubleBlow = 1;

                        if (DungeonRun.getPlayerCharacter().getTYPE().equalsIgnoreCase("thief")) {
                            int criticalHit = 25;
                            int randomInt = (int) (101 * Math.random());

                            System.out.println("As the thief you are you managed to sneek up on the monster and give it a double blow! ");

                            if (criticalHit >= randomInt) {
                                ((Monster) activeCharacter).getHitFromHero();
                                System.out.println(ANSI_GREEN + "Double blow succed!" + ANSI_RESET);
                                doubleBlow = 2;
                            } else {
                                System.out.println(ANSI_RED + "Double blow fail :(" + ANSI_RESET);
                            }
                        }

                        ((Monster) activeCharacter).getHitFromHero();
                        System.out.println("Your attack power: " + ANSI_GREEN + attack + ANSI_RESET + " VS " + activeCharacter.getTYPE() + " defense power: " + ANSI_RED + agility + ANSI_RESET);
                        System.out.println(ANSI_GREEN + "You damaged the " + activeCharacter.getTYPE() + "!" + ANSI_RESET);
                        System.out.println("-" + doubleBlow + " HP on " + activeCharacter.getTYPE());

                        if (activeCharacter.getHealth() <= 0) {
                            System.out.println(ANSI_GREEN + activeCharacter.getTYPE() + " is defeated!" + ANSI_RESET);
                            DungeonRun.getPlayerCharacter().increaseTotalMonsterDefeated();
                            System.out.println(ANSI_GREEN + "<PRESS ENTER TO CONTINUE>" + ANSI_RESET);
                            input.nextLine();
                        } else {
                            System.out.println(activeCharacter.getTYPE() + " now has " + activeCharacter.getHealth() + " HP left");
                            System.out.println(ANSI_GREEN + "<PRESS ENTER TO CONTINUE>" + ANSI_RESET);
                            input.nextLine();
                        }

                    } else if (attack < agility) {
                        System.out.println(activeCharacter.getTYPE() + " defense power: " + ANSI_RED + agility + ANSI_RESET + " VS your attack power: " + ANSI_GREEN + attack + ANSI_RESET);
                        System.out.println(ANSI_RED + "Attack missed!" + ANSI_RESET);
                        System.out.println(ANSI_GREEN + "<PRESS ENTER TO CONTINUE>" + ANSI_RESET);
                        input.nextLine();
                    }
                }
            }
        }

        if (battleCage.size() == 1) {
            monstersLeft = false;
        }

        return monstersLeft;
    }

    public boolean fightAgainstHero() {

        for (LivingCharacters activeCharacter : battleCage) {

            if (fightNow.getHealth() <= 0) {
                break;
            }

            if (activeCharacter instanceof PlayerCharacter) {
                System.out.println("*********************************************************************");
                System.out.println(fightNow.getTYPE() + " now fight against you! (Attack: " + fightNow.getATTACK() + ")");
                System.out.println(ANSI_GREEN + "\n<PRESS ENTER TO TAKE HIT FROM " + fightNow.getTYPE() + ">" + ANSI_RESET);
                input.nextLine();
                Map.punchSound();

                if (!block) {
                    int attack = LivingCharacters.roll(fightNow.getATTACK());
                    int agility = LivingCharacters.roll(DungeonRun.getPlayerCharacter().getAGILITY());

                    if (attack >= agility) {
                        DungeonRun.getPlayerCharacter().getHitFromMonster();
                        System.out.println(fightNow.getTYPE() + " attack value: " + ANSI_RED + attack + ANSI_RESET + " VS your agility value: " + ANSI_GREEN + agility + ANSI_RESET);
                        System.out.println("The " + fightNow.getTYPE() + " has damaged you!");

                        if (DungeonRun.getPlayerCharacter().getHealth() <= 0) {
                            DungeonRun.getPlayerCharacter().resetPointsDuringGame();
                            DungeonRun.getPlayerCharacter().resetTreasuresFoundDuringGame();
                            System.out.println(ANSI_RED + "You have been defeted, GAME OVER!" + ANSI_RESET);
                            System.out.println(ANSI_GREEN + "<PRESS ENTER TO GO BACK TO MENU>" + ANSI_RESET);
                            input.nextLine();

                            DungeonRun.getPlayerCharacter().restoreHP();
                            heroAlive = false;
                        } else {
                            System.out.println(ANSI_RED + "- 1 HP" + ANSI_RESET);
                            System.out.println("Your HP: " + DungeonRun.getPlayerCharacter().getHealth());
                            System.out.println(ANSI_GREEN + "<PRESS ENTER TO CONTINUE>" + ANSI_RESET);
                            input.nextLine();
                        }

                    } else if (attack < agility) {
                        System.out.println("Your agility value: " + ANSI_GREEN + agility + ANSI_RESET + " VS " + fightNow.getTYPE() + " attack value: " + ANSI_RED + attack + ANSI_RESET);
                        System.out.println(ANSI_GREEN + "You have dodged the attack!" + ANSI_RESET);
                        System.out.println(ANSI_GREEN + "<PRESS ENTER TO CONTINUE>" + ANSI_RESET);
                        input.nextLine();
                    }
                } else {
                    System.out.println(DungeonRun.getPlayerCharacter().getName() + " used the shield to block the attack!");
                    block = false;
                }
            }
        }
        return heroAlive;
    }

    public void fightSequence() {

        boolean fightOn = true;

        System.out.println("You and the monsters throw dice...");
        System.out.println(ANSI_GREEN + "<PRESS ENTER TO THROW DICE>" + ANSI_RESET);
        input.nextLine();
        sort();
        System.out.println("The battle going to follow this sequence: ");

        int i = 1;

        for (LivingCharacters warrior : battleCage) {
            System.out.println("[" + i + "]" + warrior);
            i++;
        }

        System.out.println(ANSI_GREEN + "<PRESS ENTER TO START THE BATTLE>" + ANSI_RESET);
        input.nextLine();

        while (fightOn) {
            for (LivingCharacters activeCharacter : battleCage) {

                fightNow = activeCharacter;

                if (activeCharacter instanceof PlayerCharacter) {
                    fightOn = fightMonster();
                    if (battleCage.size() <= 1 || !fightOn) {
                        break;
                    }

                } else {
                    fightOn = fightAgainstHero();
                    if (!fightOn) {
                        break;
                    }
                }
            }

            for (int j = battleCage.size() - 1; j >= 0; j--) {
                if (battleCage.get(j) instanceof Monster) {
                    if (battleCage.get(j).getHealth() <= 0) {
                        battleCage.remove(j);
                    }
                }
            }

            if (battleCage.size() <= 1) {
                monsterCages.clear();
            }

        }
    }

    public int visitRoom() {

        int outCome = 3;
        boolean isLeavingMap = false;

        if (DungeonRun.getPlayerCharacter() instanceof Knight) {
            block = true;
        }

        if (hasExit) {
            System.out.println("In the corridor you find an exit, do you want to leave the castle (collects your point and exit map)?");
            System.out.println("(Y/N)");
            isLeavingMap = UserInput.getYOrNFromUser();

            if (isLeavingMap) {
                DungeonRun.getPlayerCharacter().collectPoints();
                DungeonRun.getPlayerCharacter().collectTreasuresFound();
                outCome = 2;
            }
        }

        if (!hasExit || hasExit && !isLeavingMap) {
            if (!visited) {
                if (monsterCages.isEmpty()) {
                    System.out.println("Youre lucky one, no monsters in the room!");
                    System.out.println(ANSI_GREEN + "<PRESS ENTER TO CONTINUE>" + ANSI_RESET);
                    input.nextLine();
                } else {
                    System.out.println("There are " + monsterCages.size() + " monsters in the room!");
                    fightSequence();
                    if (battleCage.size() <= 1) {
                        battleCage.clear();
                    }
                }
            } else if (visited && monsterCages.isEmpty()) {
                System.out.println("You have already been here!");
                System.out.println(ANSI_GREEN + "<PRESS ENTER TO CONTINUE>" + ANSI_RESET);
                input.nextLine();
            } else if (visited && monsterCages.size() >= 1) {
                System.out.println("You have been here before and left monsters behind you!");
                System.out.println(ANSI_GREEN + "<PRESS ENTER TO START BATTLE AGAIN>" + ANSI_RESET);
                input.nextLine();
                battleCage.clear();
                escapeSuccess = false;

                for (Monster monster : monsterCages) {
                    if (monster.getHealth() <= 0) {
                        monsterCages.remove(monster);
                    }
                }
                fightSequence();
                if (battleCage.size() <= 1) {
                    battleCage.clear();
                }
            }

            if (!escapeSuccess && heroAlive) {
                if (!treasures.isEmpty()) {
                    System.out.println("There is a treasure in the room!");
                    System.out.println(ANSI_GREEN + "<PRESS ENTER TO PICKUP TREASURE>" + ANSI_RESET);
                    input.nextLine();
                    Map.treasureSound();

                    for (Treasure treasure : treasures) {
                        System.out.println("You picked up " + treasure);
                        DungeonRun.getPlayerCharacter().setPointsDuringGame(treasure.getPoints());
                        DungeonRun.getPlayerCharacter().increaseTreausureFound();
                    }

                    treasures.clear();
                    System.out.println("You now have " + DungeonRun.getPlayerCharacter().getPointsDuringGame() + " points collected!");
                    System.out.println(ANSI_GREEN + "<PRESS ENTER TO CONTINUE>" + ANSI_RESET);
                    input.nextLine();
                }
            }
        }

        if (escapeSuccess) {
            outCome = 1;
            escapeSuccess = false;
        }
        if (!heroAlive) {
            outCome = 2;
        }
        if (!visited) {
            DungeonRun.getPlayerCharacter().increaseTotalVisitedRooms();
        }
        if (!visited && battleCage.isEmpty()) {
            outCome = 0;
        }
        if (isLeavingMap) {
            outCome = 2;
        }

        visited = true;

        return outCome;

    }

    public boolean tryEscape() {

        escapeSuccess = false;

        int chansFlykt;
        System.out.println("You're trying to escape!");
        if (DungeonRun.getPlayerCharacter().getTYPE().equalsIgnoreCase("wizard")) {
            chansFlykt = 80;
        } else {
            int smidighet = DungeonRun.getPlayerCharacter().getAGILITY(); //Här ska vi get smidighet för den valda karaktären
            chansFlykt = smidighet * 10;
        }

        System.out.println("Your chance to escape is " + chansFlykt + "%");
        System.out.println(ANSI_GREEN + "<PRESS ENTER TO TRY ESCAPE>" + ANSI_RESET);
        input.nextLine();
        int randomInt = (int) (101 * Math.random());
        // System.out.println("Random int mellan 1- 100: " + randomInt);
        if (chansFlykt >= randomInt) {                                      //Om talet är mindre eller lika med (Random int) så kan du fly else måste du fortsätta stirda
            System.out.println("You managed to escape and end up in the room you were in before ");
            escapeSuccess = true;
            //Skrivmetod för detta
            // System.exit(0);

        } else {
            System.out.println("You failed to escape and must continue to fight ");
            //Monster:et/na kommer nu attackera spelaren 
            // System.exit(0);
        }

        return escapeSuccess;
    }

    public void sort() {

        battleCage.clear();

        if (!monsterCages.isEmpty()) {
            battleCage.addAll(monsterCages);
            battleCage.add(DungeonRun.getPlayerCharacter());

            for (LivingCharacters k : battleCage) {
                k.setInitiativeSum(LivingCharacters.roll(k.getINITIATIVE()));
            }

            //System.out.println("BattleCage before sorting: " + battleCage);
            for (int k = battleCage.size() - 2; k >= 0; k--) {

                int highestPos = k;

                for (int i = battleCage.size() - 1; i >= 0; i--) {
                    if (battleCage.get(i).getInitiativeSum() > battleCage.get(highestPos).getInitiativeSum()) {
                        highestPos = i;
                        LivingCharacters temp = battleCage.get(k);
                        battleCage.set(k, battleCage.get(highestPos));
                        battleCage.set(highestPos, temp);
                    }
                }
            }
            //System.out.println("BattleCage sorted after initiativeSum: " + battleCage);
        }
    }

    public void hasExit() {
        hasExit = true;
    }
}
