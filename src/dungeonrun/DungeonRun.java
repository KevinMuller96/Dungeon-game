package dungeonrun;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class DungeonRun {

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
    private static ArrayList<PlayerCharacter> characters = new ArrayList<>();

    private static final Scanner INPUT = new Scanner(System.in);

    private static ObjectInputStream objectInput;
    private static FileInputStream fileInput;
    private static PlayerCharacter playerCharacter;

    public static void main(String[] args) throws IOException {
        readFromFile();
        startScreen();
    }

    public static void startScreen() throws IOException {
        boolean gameIsOn = true;

        PlaySound bg = new PlaySound("bgMusic.wav");
        Thread bgMusic = new Thread(bg, "T1");
        bgMusic.start();
        
        while (gameIsOn) {

            System.out.println("                                  |>>>");
            System.out.println("                                  |");
            System.out.println("                    |>>>      _   |_  _         |>>>");
            System.out.println("                    |        |;| |;| |;|        |");
            System.out.println("                _  _|_  _    \\\\.    .  /    _  _|_  _         /`\\");
            System.out.println("               |;|_|;|_|;|    \\\\:. ,  /    |;|_|;|_|;|");
            System.out.println("               \\\\..      /    ||;   . |    \\\\.    .  /     \\,/");
            System.out.println("                \\\\.  ,  /     ||:  .  |     \\\\:  .  /");
            System.out.println("                 ||:   |_   _ ||_ . _ | _   _||:   |");
            System.out.println("                 ||:  .|||_|;|_|;|_|;|_|;|_|;||:.  |");
            System.out.println("                 ||:   ||.    .     .      . ||:  .|");
            System.out.println(" ____  __  __  _ ||  __|| ____  _____  _  _  || ___| __  __  _  _ ");
            System.out.println("(  _ \\(  )(  )( \\( )/ __)( ___)(  _  )( \\( )  (  _ \\(  )(  )( \\( )");
            System.out.println(ANSI_GREEN + " )(_) ))(__)(  )  (( (_-. )__)  )(_)(  )  (    )   / )(__)(  )  ( ");
            System.out.println(ANSI_RESET + "(____/(______)(_)\\_)\\___/(____)(_____)(_)\\_)  (_)\\_)(______)(_)\\_)" + ANSI_RESET);
            System.out.println("                 ||:   ||:  ,  _______   .   ||: , |            ");
            System.out.println("                 ||:   || .   /+++++++\\    . ||:   |");
            System.out.println("                 ||:   ||.    |+++++++| .    ||: . |");
            System.out.println("              __ ||: . ||: ,  |+++++++|.  . _||_   |");
            System.out.println("     ____--`~    '--~~__|.    |+++++__|----~    ~`---,              ___");
            System.out.println("-~--~                   ~---__|,--~'                  ~~----_____-~'   `~----~~");

            if (playerCharacter != null) {
                System.out.println("Your character: ");
                System.out.println("Name: " + playerCharacter.getName());
                System.out.println("Character type: " + playerCharacter.getTYPE());
                System.out.println();
            }

            System.out.println(ANSI_YELLOW + "\t\t  [1] - Start adventure" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "\t\t  [2] - Select character" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "\t\t  [3] - Dungeon shop" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "\t\t  [4] - Highscore" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "\t\t  [5] - Stop background music" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "\t\t  [0] - Quit" + ANSI_RESET);

            int choice = UserInput.getIntFromUser();

            switch (choice) {
                case 0:
                    gameIsOn = false;
                    bgMusic.stop();
                    break;
                case 1:
                    if (playerCharacter == null) {
                        System.out.println(ANSI_YELLOW + "You first have to select playercharacter!" + ANSI_RESET);
                        System.out.println(ANSI_GREEN + "<PRESS ENTER TO CONTINUE>" + ANSI_RESET);
                        INPUT.nextLine();
                    } else {
                        play();
                    }

                    break;
                case 2:
                    selectCharacter();
                    break;
                case 3:
                    shop();
                    break;
                case 4:
                    highscore();
                    break;
                case 5:
                    bgMusic.stop();
                    break;
            }
        }
    }

    public static void selectCharacter() {
        int choice;

        System.out.println(ANSI_YELLOW + "\t\t  [1] - Create new character" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "\t\t  [2] - Load existing character" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "\t\t  [3] - Remove saved characters" + ANSI_RESET);

        choice = UserInput.getIntFromUser();
        switch (choice) {
            case 1:
                chooseHero();
                break;
            case 2:
                loadCharacter();
//                if (playerCharacter != null) {
//                    for (int i = 0; i < characters.size() - 1; i++) {
//                        if (characters.get(i).getName().equals(playerCharacter.getName())) {
//                            characters.set(i, playerCharacter);
//                        }
//                    }
//                }
                break;
            case 3:
                removeSavedCharacters();
                break;
            case 4:
                break;
        }
    }

    public static void chooseHero() {

        int choice;

        System.out.println("[1] - Knight♚\t\t[2] - Wizard☠\t\t[3] - Thief☤\t");

        System.out.println("       !          ║ \t         /^\\      ║           ____");
        System.out.println("      .-.         ║ \t    /\\   \"V\"      ║         _[____]_");
        System.out.println("    __|=|__       ║ \t   /__\\   I       ║          ( '7')");
        System.out.println("   (_/`-`\\_)      ║ \t  //..\\\\  I       ║         __)(^_");
        System.out.println("   //\\___/\\\\      ║ \t  \\].`[/  I       ║        / ,C^D,\\");
        System.out.println("   <>/   \\<>      ║ \t  /l\\/j\\  (]      ║       / /||:||\\\\");
        System.out.println("    \\|_._|/       ║ \t /. ~~ ,\\/I       ║       \\ \\|/:\\|//");
        System.out.println("     <_I_>        ║ \t \\\\L__j^\\/I       ║        `\\\\~~~|/");
        System.out.println("      |||         ║ \t  \\/--v}  I       ║       ,##\\7|\\ \\");
        System.out.println("     /_|_\\        ║ \t  |    |  I       ║          |#| \\ \\");
        System.out.println("\n≫ ─────────────── ≪•◦ ✯❈✯ ◦•≫ ───────────────  ≪");
        System.out.println("≫ ☉Initiative:\t5 ║  ☉Initiative:\t6 ║     ☉Initiative:\t7    ≪");
        System.out.println("≫ ❤Endurance:\t9 ║  ❤Endurance:\t4 ║     ❤Endurance:\t5    ≪");
        System.out.println("≫ ✒Attack:\t6 ║  ✒Attack:\t\t9 ║     ✒Attack:\t5    ≪");
        System.out.println("≫ ☯Agility:\t4 ║  ☯Agility:\t\t5 ║     ☯Agility:\t7    ≪");
        System.out.println("≫ ─────────────── ≪•◦ ✯❈✯ ◦•≫ ───────────────  ≪");
        System.out.println("\nMake a choice: ");

        do {

            choice = UserInput.getIntFromUser();

        } while (choice < 0 || choice > 3);

        switch (choice) {

            case 1:
                playerCharacter = new Knight(setNameCharacter());
                characters.add(playerCharacter);
                break;
            case 2:
                playerCharacter = new Wizard(setNameCharacter());
                characters.add(playerCharacter);
                break;
            case 3:
                playerCharacter = new Thief(setNameCharacter());
                characters.add(playerCharacter);
                break;
        }

    }

    public static void loadCharacter() {

        //PlayerCharacter playerChoice = null;
        int i = 0;
        boolean tryInput = true;

        if (characters.isEmpty()) {
            System.out.println(ANSI_RED + "No characters to load" + ANSI_RESET);
        } else {
            for (PlayerCharacter obj : characters) {
                System.out.println(ANSI_GREEN + "Character " + (i + 1) + ":\n" + ANSI_RESET + obj);
                i++;
            }
            System.out.println("Make a choice: ");

            int choice = 0;

            while (tryInput) {
                choice = UserInput.getIntFromUser();
                if (choice < 1 || choice > i) {
                    System.out.println(ANSI_RED + "Not an option, try again!" + ANSI_RESET);
                } else {
                    tryInput = false;
                }
            }

            playerCharacter = characters.get(choice - 1);
        }

    }

    public static void saveToFile() {

        if (!characters.isEmpty()) {
            try {
                FileOutputStream fileOut = new FileOutputStream("players.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                for (PlayerCharacter c : characters) {
                    out.writeObject(c);
                }
                out.close();
                fileOut.close();
            } catch (IOException i) {
                System.out.println("FAIL: " + i.getMessage());
            }
        }
    }

    public static void readFromFile() throws IOException {

        PlayerCharacter c = null;

        try {
            fileInput = new FileInputStream("players.ser");
            objectInput = new ObjectInputStream(fileInput);

            characters.clear();

            while (true) {
                c = (PlayerCharacter) objectInput.readObject();
                characters.add(c);
            }

        } catch (IOException i) {
            if (objectInput != null) {
                objectInput.close();
                fileInput.close();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Player class not found: " + e.getMessage());
        }
    }

    public static String setNameCharacter() {

        boolean tryInput = false;

        System.out.print("Set a name to your character:");

        String characterName = null;
        do {
            characterName = UserInput.checkName();
            tryInput = false;

            for (PlayerCharacter character : characters) {
                if (character.getName().equalsIgnoreCase(characterName)) {
                    System.out.println("Name already exist, choose another:");
                    tryInput = true;
                }
            }
        } while (tryInput);

        return characterName;
    }

    public static void removeSavedCharacters() {

        int i = 0;
        boolean tryInput = true;

        if (characters.isEmpty()) {
            System.out.println(ANSI_RED + "No characters to remove" + ANSI_RESET);
        } else {
            for (PlayerCharacter obj : characters) {
                System.out.println(ANSI_GREEN + "Character " + (i + 1) + ":\n" + ANSI_RESET + obj);
                i++;
            }
            System.out.println("Choose wich to delete: ");

            int choice = 0;

            while (tryInput) {
                choice = UserInput.getIntFromUser();
                if (choice < 1 || choice > i) {
                    System.out.println(ANSI_RED + "Not an option, try again!" + ANSI_RESET);
                } else {
                    tryInput = false;
                }
            }
            characters.remove(choice - 1);
            try {
                FileOutputStream fileOut = new FileOutputStream("players.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                for (LivingCharacters c : characters) {
                    out.writeObject(c);
                }
                out.close();
                fileOut.close();
            } catch (IOException e) {
                System.out.println("FAIL: " + e.getMessage());
            }
        }
    }

    public static PlayerCharacter getPlayerCharacter() {
        return playerCharacter;
    }

    public static void play() {

        boolean play = true;

        String text = "Hi " + playerCharacter.getName() + "! You play as the " + playerCharacter.getTYPE();
        char[] charArr = text.toCharArray();

        for (int i = 0; i <= charArr.length - 1; i++) {
            System.out.print(charArr[i]);
            try {
                Thread.sleep(250);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(250);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();

        playerCharacter.printStory();

        System.out.println(ANSI_GREEN + "<PRESS ENTER TO CONTINUE>" + ANSI_RESET);
        INPUT.nextLine();

        Map map = new Map();

        map.createRooms();

        map.chooseStartPos();

        while (play) {
            int outCome = map.goInRoom();
            saveToFile();
            if (outCome == 1) {
                play = false;
                break;
            } else if (outCome == 2) {
                map.printMap();
            } else {
                map.printMap();
                map.move();
            }
        }
        saveToFile();
    }

    public static void highscore() {

        boolean showMenu = true;

        System.out.println("Welcome to scoreboard!");

        if (!characters.isEmpty()) {

            while (showMenu) {
                System.out.println("Choose how to sort scoreboard: ");
                System.out.println("[1] - By monster deafeated");
                System.out.println("[2] - By most treasure found");
                System.out.println("[3] - By most visited rooms");
                System.out.println("[4] - By most points collected");
                System.out.println("[0] - Go back to main menu");
                System.out.println("Make a choice:");

                int choice = 0;
                do {
                    choice = UserInput.getIntFromUser();
                    if (choice > 4 || choice < 0) {
                        System.out.println(ANSI_RED + "Not an option" + ANSI_RESET);
                    }
                } while (choice > 4 || choice < 0);

                switch (choice) {
                    case 0:
                        showMenu = false;
                        break;
                    case 1:
                        Highscore.monstersDeafeated(characters);
                        break;
                    case 2:
                        Highscore.mostTreasureFound(characters);
                        break;
                    case 3:
                        Highscore.mostVisitedRooms(characters);
                        break;
                    case 4:
                        Highscore.mostPoints(characters);
                        break;
                }
            }

        } else {
            System.out.println(ANSI_RED + "No highscore yet, start new adventure!" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "<PRESS ENTER TO GO BACK TO MAIN MENU>" + ANSI_RESET);
            INPUT.nextLine();
        }

    }

    public static void shop() {

        // How many points it cost to upgrade
        int costAttack = 15;
        int costAgility = 15;
        int maxUpgrade = 4;

        System.out.println("Welcome to the gameshop!");
        System.out.println("Here you can buy extras to your character:\n");

        if (playerCharacter != null) {
            System.out.println("***************************************");
            System.out.println("Your character: ");
            System.out.println("Name: " + playerCharacter.getName());
            System.out.println("Type: " + playerCharacter.getTYPE());
            
            if (playerCharacter.getATTACK() == playerCharacter.getSTARTATTACK() + maxUpgrade) {
                System.out.println("Attack: " + playerCharacter.getATTACK() + ANSI_GREEN + "\tMAXIMUM UPGRADED" + ANSI_RESET);
            }else {
                System.out.println("Attack: " + playerCharacter.getATTACK());
            }
            
            
            if (playerCharacter.getAGILITY() == playerCharacter.getSTARTAGILITY() + maxUpgrade) {
                System.out.println("Agility: " + playerCharacter.getAGILITY() + ANSI_GREEN + "\tMAXIMUM UPGRADED" + ANSI_RESET);
            }else {
                System.out.println("Agility: " + playerCharacter.getAGILITY());
            }
            System.out.println(ANSI_GREEN + "Credits to spend: " + playerCharacter.getCredits() + ANSI_RESET);
            System.out.println("***************************************");

            if (playerCharacter instanceof Knight) {
                System.out.println("[1] - Excalibur \n"
                        + "Excalibur is the legendary sword of king Arthur attributed with magical powers \n"
                        + "Excalibur is the famous sword in the stone and is said to crush everything in its way. \n"
                        + ANSI_GREEN + "Excalibur increases your attack power with +1!\n" + ANSI_RESET
                        + ANSI_RED + "COST: " + costAttack + " credits" + ANSI_RESET);//LÄGG TILL

                System.out.println("\n[2] - The Talaria of Mercury \n"
                        + "Also known as The Winged Sandals of Hermes worn by the greek god Hermes him self. \n"
                        + "They were said to be made by the god Hephaestus of imperishable gold and \n"
                        + "they flew the god as swift as any bird. \n"
                        + ANSI_GREEN + "The Talaria increases you agility with +1!\n" + ANSI_RED
                        + ANSI_RED + "COST: " + costAgility + " credits" + ANSI_RESET);//Lägg till

            } else if (playerCharacter instanceof Wizard) {
                System.out.println("[1] - The wicked wand \n"
                        + "This magic staff is possessed by the power of Harry Potter him self \n"
                        + "has the ability to destroy any monster that comes in the way. \n "
                        + ANSI_GREEN + "The wicked wand increases your attack power with +1!\n" + ANSI_RESET
                        + ANSI_RED + "COST: " + costAttack + " credits" + ANSI_RESET);

                System.out.println("\n[2] - Cloak of invisibility \n "
                        + "An invisibility cloak is a magical garment which renders \n "
                        + "whomever or whatever it covers invisible. Invisibility \n "
                        + "cloaks are exceptionally rare and valuable within the \n"
                        + "wizarding world. \n"
                        + ANSI_GREEN + "The Cloak of invisibility increases your agility with +1! " + ANSI_RESET
                        + ANSI_RED + "COST: " + costAgility + " credits" + ANSI_RESET);

            } else if (playerCharacter instanceof Thief) {
                System.out.println("[1] - The shiruken of darkness \n "
                        + "These shirukens where handmade in an ancient village in \n"
                        + "japan 3500 years  ago. They are made out of a special \n "
                        + "unbreakable material possessed with samuraj souls. \n "
                        + ANSI_GREEN + "The shiruken of drakness increases your attack power with +1\n! " + ANSI_RESET
                        + ANSI_RED + "COST: " + costAttack + " credits" + ANSI_RESET);

                System.out.println("\\n[2] - Moccasins of lightning\n"
                        + "The moccasins of lightning was given to the human rase\n" 
                        + "from the god of lightning him self.\n" 
                        + "The one who wears the moccasins can define gravity\n" 
                        + "and move 100 times faster than a any other human. "
                        + "The moccasins of lightning increases your ability your agility with +1!\n"
                        + ANSI_RED + "COST: " + costAgility + " credits" + ANSI_RESET);

            }

            System.out.println("What do you want to buy?");
            int choice = UserInput.getIntFromUser();

            switch (choice) {
                case 1:
                    if (playerCharacter.getCredits() >= costAttack && playerCharacter.getATTACK() != playerCharacter.getSTARTATTACK() + maxUpgrade) {
                        System.out.println(ANSI_GREEN + "Congrats, you now have +1 attack on your character!" + ANSI_RESET);
                        playerCharacter.buyAttack(costAttack);
                    } else {
                        System.out.println(ANSI_RED + "Sorry, you dont have enough points to buy this!" + ANSI_RESET);
                    }
                    break;
                case 2:
                    if (playerCharacter.getCredits() >= costAgility && playerCharacter.getAGILITY() != playerCharacter.getSTARTAGILITY() + maxUpgrade) {
                        System.out.println(ANSI_GREEN + "Congrats, you now have +1 agility on your character!" + ANSI_RESET);
                        playerCharacter.buyAgility(costAgility);
                    } else {
                        System.out.println(ANSI_RED + "Sorry, you dont have enough points to buy this!" + ANSI_RESET);
                    }
                    break;
            }

        } else {
            System.out.println("You must first create or load an existing character to access the shop");
        }

        System.out.println(ANSI_GREEN + "<PRESS ENTER TO CONTINUE>" + ANSI_RESET);
        INPUT.nextLine();
        saveToFile();
    }
}
