//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String firtName = getValidName(input, "FIRST");
        String lastName = getValidName(input, "LAST");
        int firstInt = getValidInt(input, "First Integer");
        int secondInt = getValidInt(input, "Second Integer");


    }
    public static String getValidName(Scanner input, String type) {

        String regex = "^[A-Z][a-z]{0,49}$";

        while (true) {

            System.out.println("Enter your " + type + " name:");
            System.out.println("Requirements:");
            System.out.println("- 1 to 50 letters");
            System.out.println("- Letters only (A-Z or a-z)");
            System.out.println("- Must start with uppercase letter");
            System.out.println("Enter name here: ");
            String name = input.nextLine();

            if (name.matches(regex)) {
                return name;
            } else {
                System.out.println("ERROR: Invalid " + type + " name format.\n");
            }
        }
    }

    public static int getValidInt(Scanner input, String label) {
        while (true) {
            System.out.println("Enter " + label);
            System.out.println("Requirements:");
            System.out.println("Must be 4-byte integer between -2147483648 to 2147483647");
            System.out.println("Enter number here: ");
            String line = input.nextLine();

            try {
                long temp = Long.parseLong(line);

                if (temp < Integer.MIN_VALUE || temp > Integer.MAX_VALUE) {
                    System.out.println("ERROR: Value out of 4-byte integer range.");
                    continue;
                }
                return (int) temp;

            } catch (NumberFormatException e) {
                System.out.println("ERROR: You must enter a valid integer.");
                //logError(e);
            }
        }
    }
}