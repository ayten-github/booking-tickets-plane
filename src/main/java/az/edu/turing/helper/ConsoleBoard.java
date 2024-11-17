package az.edu.turing.helper;

public class ConsoleBoard {

    public static void runApplication() {

        boolean running = true;
        while (running) {
            displayMainMenu();
            try {
                int choice = InputValidator.getValidatedIntInput("Enter your choice: ");
                switch (choice) {
                    case 1:
                        MenuHelper.handleOnlineBoard();// hell olundu
                        displayMenu();
                        break;
                    case 2:
                        MenuHelper.showFlightInfo();
                        break;
                    case 3:
                        MenuHelper.searchAndBookFlight();
                        break;
                    case 4:
                        MenuHelper.cancelBooking();
                        break;
                    case 5:
                        MenuHelper.showMyFlights();
                        break;
                    case 6:
                        running = false;// hell olundu
                        break;
                    default:
                        System.out.println("Invalid choice");//hell olundu
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("""
                Main Menu:
                1. Online-board
                2. Show the flight info
                3. Search and book a flight
                4. Cancel the booking
                5. My flights
                6. Exit
                """);
    }

    public static void displayMenu() {
        System.out.println("""
                Make choice:
                0: Return back to main menu
                1: Select one of them
                """
        );
    }
}
