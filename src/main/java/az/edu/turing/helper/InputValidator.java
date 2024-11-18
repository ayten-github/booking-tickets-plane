package az.edu.turing.helper;

import java.util.Scanner;

public class InputValidator {

    private static final Scanner scanner = new Scanner(System.in);

    public static String getValidatedStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static int getValidatedIntInput(String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }
}
