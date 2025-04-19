import java.util.Scanner;
public class SafeInput {

    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retString = "";
        do {
            System.out.print("\n" + prompt + ": ");
            retString = pipe.nextLine();
        } while (retString.length() == 0);
        return retString;
    }

    public static int getInt(Scanner pipe, String prompt) {
        int retValue;
        while (true) {
            System.out.print("\n" + prompt + ": ");
            if (pipe.hasNextInt()) {
                retValue = pipe.nextInt();
                pipe.nextLine();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                pipe.nextLine();
            }
        }
        return retValue;
    }

    public static double getDouble(Scanner pipe, String prompt) {
        double retValue;
        while (true) {
            System.out.print("\n" + prompt + ": ");
            if (pipe.hasNextDouble()) {
                retValue = pipe.nextDouble();
                pipe.nextLine();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid double.");
                pipe.nextLine();
            }
        }
        return retValue;
    }

    public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
        int result = 0;
        boolean done = false;
        String trash = "";
        do {
            System.out.println(prompt + "[" + low + "-" + high + "]:");
            if (pipe.hasNextInt()) {
                result = pipe.nextInt();
                pipe.nextLine();
                if (result >= low && result <= high) {
                    done = true;
                } else {
                    System.out.println("You must enter a value in the range [" + low + "-" + high + "]");
                }
            } else {
                System.out.println("You must enter a value in the range [" + low + "-" + high + "]");
            }
        } while (!done);
        return result;
    }

    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {
        double retValue;
        while (true) {
            System.out.print("\n" + prompt + " [" + low + " - " + high + "]: ");
            retValue = getDouble(pipe, prompt);
            if (retValue >= low && retValue <= high) {
                break;
            } else {
                System.out.println("Input must be between " + low + " and " + high);
            }
        }
        return retValue;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        String response;
        while (true) {
            System.out.print("\n" + prompt + " (y/n): ");
            response = pipe.nextLine().toLowerCase();
            if (response.equals("y")) {
                return true;
            } else if (response.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter y or n.");
            }
        }
    }

    public static String getRegExString (Scanner pipe, String prompt, String regExPattern) {
        String value = "";
        boolean done = false;
        do{
            System.out.println(prompt +":");
            value = pipe.nextLine();
            if(value.matches(regExPattern)){
                done = true;
            }
            else{
                System.out.println("\n Invalid input: " +value);
            }
        }while(!done);
        return value;
    }

    public static void prettyHeader(String msg) {
        int width = 60;
        int padding = (width - msg.length() - 6) / 2;

        for (int i = 0; i < width; i++) {
            System.out.print("*");
        }
        System.out.println();

        System.out.print("***");
        for (int i = 0; i < padding; i++) {
            System.out.print(" ");
        }
        System.out.print(msg);
        for (int i = 0; i < padding; i++) {
            System.out.print(" ");
        }
        System.out.println("***");

        for (int i = 0; i < width; i++) {
            System.out.print("*");
        }
        System.out.println();
    }
}
