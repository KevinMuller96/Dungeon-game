package dungeonrun;

import java.util.Scanner;

public class UserInput {

    private static final Scanner INPUT = new Scanner(System.in);
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static int getIntFromUser() {

        int input = 0;
        String inputString = "";
        boolean tryInput = true;

        inputString = INPUT.nextLine();

        do {
            try {
                input = Integer.parseInt(inputString);
                tryInput = false;
            } catch (Exception e) {
                System.out.println(ANSI_RED + "Wrong input, try again!" + ANSI_RESET);
                inputString = INPUT.nextLine();
            }
        } while (tryInput);

        return input;
    }

    public static double getDoubleFromUser() {

        double input = 0;
        String inputString;
        boolean tryInput = true;

        inputString = INPUT.nextLine();

        do {
            try {
                input = Double.parseDouble(inputString);
                tryInput = false;
                if (input < 0) {
                    tryInput = true;
                    System.out.println(ANSI_RED + "Invalid num, try again: " + ANSI_RESET);
                    inputString = INPUT.nextLine();
                }
            } catch (Exception e) {
                System.out.println(ANSI_RED + "Wrong input, try again!" + ANSI_RESET);
                inputString = INPUT.nextLine();
            }
        } while (tryInput);

        return input;
    }

    public static Boolean getYOrNFromUser() {

        String input;
        boolean tryAgain = true;
        boolean answer = false;

        input = INPUT.nextLine();

        do {
            if (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N")) {
                System.out.println(ANSI_RED + "Invalid choice, try again (Y/N): " + ANSI_RESET);
                input = INPUT.nextLine();
            } else if (input.equalsIgnoreCase("Y")) {
                answer = true;
                tryAgain = false;
            } else {
                answer = false;
                tryAgain = false;
            }
        } while (tryAgain);

        return answer;
    }

    public static String checkName() {

        boolean nameIscorrect = false;
        boolean tryInput = true;

        String name = INPUT.nextLine();

        name = name.trim();

        while (tryInput) {

            if (name.contains("-")) {
                String name1 = name.substring(name.indexOf("-"));
                String name2 = name.substring(0, name.indexOf("-"));

                for (int j = 0; j < name1.length(); j++) {
                    if (Character.isLetter(name1.charAt(j))) {
                        nameIscorrect = true;
                    } else {
                        nameIscorrect = false;
                    }
                }
                for (int k = 0; k < name2.length(); k++) {
                    if (Character.isLetter(name2.charAt(k))) {
                        nameIscorrect = true;
                    } else {
                        nameIscorrect = false;
                    }
                }
            } else {

                for (int i = 0; i < name.length(); i++) {
                    if (Character.isLetter(name.charAt(i))) {
                        nameIscorrect = true;
                    } else {
                        nameIscorrect = false;
                        System.out.println(ANSI_RED + "You have to enter a valid name, try again: " + ANSI_RESET);
                        name = INPUT.nextLine();
                        name = name.trim();
                        i = 0;
                    }
                }
            }

            if (nameIscorrect) {
                tryInput = false;
            } else {
                System.out.println(ANSI_RED + "You have to enter a valid name, try again" + ANSI_RESET);
                name = INPUT.nextLine();
                name = name.trim();
            }

        }
        return name;
    }
}
