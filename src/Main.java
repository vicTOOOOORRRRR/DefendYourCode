import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String firtName = getValidName(input, "FIRST");
        String lastName = getValidName(input, "LAST");
        int firstInt = getValidInt(input, "First Integer");
        int secondInt = getValidInt(input, "Second Integer");
        String inputFileName = getValidFileName(input, "INPUT");
        String outputFileName = getValidFileName(input, "OUTPUT");
        String userPassword = getValidPassword(input, "Enter");
        String retypedUserPassword = getValidPassword(input, "Re-enter");

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

    public static String getValidFileName(Scanner input, String IO) {

        // i dont this i did the regex corrently 
        String regex = "^[a-z0-9-]{1,26}\\.txt$";

        //reserved files names that are now allowed on windows (example: CON.txt is NOT allowed)
        String[] reservedNames = {
                "CON","PRN","AUX","NUL",
                "COM1","COM2","COM3","COM4","COM5","COM6","COM7","COM8","COM9",
                "LPT1","LPT2","LPT3","LPT4","LPT5","LPT6","LPT7","LPT8","LPT9"
        };       

        while (true) {
            System.out.println(IO + "File Name");
            System.out.println("Requirements:");
            System.out.println("- Must be a text file ending in .txt");
            System.out.println("- Maximum 30 characters total");
            System.out.println("- Letters, numbers, underscores, or dashes only");
            System.out.println("- Cannot start or end with a hyphen");
            System.out.println("- Cannot be a reserved Windows file name");
            System.out.println("- No spaces, control, or special characters allowed");
            System.out.println("Enter the " + IO + " file name:");

            String fileName = input.nextLine();

            // Null or empty check
            if (fileName == null || fileName.isEmpty()) {
                System.out.println("ERROR: File name cannot be empty.\n");
                continue;
            }

            // Make sure there is no control characters (ASCII 0 through 31)
            boolean hasControlChar = false;
            for (char c : fileName.toCharArray()) {
                if (c <= 31) {
                    hasControlChar = true;
                    break;
                }
            }
            if (hasControlChar) {
                System.out.println("ERROR: File name contains invalid control characters.\n");
                continue;
            }

            // Regex validation
            if (!fileName.matches(regex)) {
                System.out.println("ERROR: File name does not meet formatting rules.\n");
                continue;
            }

            // Cannot start or end with hyphen
            if (fileName.startsWith("-") || fileName.substring(0, fileName.length() - 4).endsWith("-")) {
                System.out.println("ERROR: File name cannot start or end with a hyphen.\n");
                continue;
            }

            // Reserved name check (remove .txt before checking)
            String baseName = fileName.substring(0, fileName.length() - 4).toUpperCase();
            boolean isReserved = false;

            for (String reserved : reservedNames) {
                if (baseName.equals(reserved)) {
                    isReserved = true;
                    break;
                }
            }

            if (isReserved) {
                System.out.println("ERROR: That is a reserved Windows file name.\n");
                continue;
            }
        }

    }

    public static String getValidPassword(Scanner input, String attempt) {
        System.out.println("Password Check");
        System.out.println("Requirements:");
        System.out.println("- Minimum of 8 characters");
        System.out.println("- Maximum of 128 characters");
        System.out.println("Must contain at least 1 upper case, 1 lower case, 1 number, 1 special character");
        System.out.println(attempt + "your password");

    }
}