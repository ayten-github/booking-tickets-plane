package az.edu.turing;

import az.edu.turing.controller.PassengerController;
import az.edu.turing.domain.dao.PassengerDao;
import az.edu.turing.domain.dao.impl.PassengerDatabaseDao;
import az.edu.turing.domain.dao.impl.PassengerInMemoryDao;
import az.edu.turing.domain.entities.BookingEntity;
import az.edu.turing.domain.entities.FlightEntity;
import az.edu.turing.domain.entities.PassengerEntity;
import az.edu.turing.exception.DatabaseException;
import az.edu.turing.mapper.PassengerMapper;
import az.edu.turing.model.dto.request.CreatePassengerRequest;
import az.edu.turing.service.PassengerService;
import az.edu.turing.service.PassengerServiceImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    private static Map<Integer, FlightEntity> flights = new HashMap<>();
    private static Map<Integer, BookingEntity> bookings = new HashMap<>();
    private static Map<Integer, List<BookingEntity>> passengerBookings = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws DatabaseException {
//        PassengerEntity passengerEntity
//                = new PassengerEntity("Cavad","Nazirli","javadnazirli123","21321");
//        PassengerFileDao passengerFileDao = new PassengerFileDao();
//        passengerFileDao.save(passengerEntity);
//        Optional<PassengerEntity> passengerOptional = passengerFileDao.getById(1L);
//        if (passengerOptional.isPresent()) {
//            PassengerEntity passenger = passengerOptional.get();
//            System.out.println("Passenger found: " + passenger.getFirstName() + " " + passenger.getLastName());
//        } else {
//            System.out.println("Passenger with ID 1 not found.");
//        }

//        final PassengerDao passengerDao =
//                new PassengerInMemoryDao();
////                    new PassengerFileDao();
//      //  new PassengerDatabaseDao();
//        PassengerMapper passengerMapper = new PassengerMapper();
//        final PassengerService passengerService = new PassengerServiceImpl(passengerDao, passengerMapper);
//        final PassengerController passengerController = new PassengerController(passengerService);
//
//        run(passengerController);
        FlightEntity flight1 = new FlightEntity(
                LocalDateTime.of(2024, 12, 2, 9, 21, 0, 0),
                "New York",
                "Kiev",
                100,
                80
        );
        FlightEntity flight2 = new FlightEntity(
                LocalDateTime.of(2024, 12, 1, 8, 22, 2, 0),
                "Paris",
                "Kiev",
                150,
                90
        );
        flights.put(1, flight1);
        flights.put(2, flight2);

        PassengerEntity p1 = new PassengerEntity(1L, "Ayten", "Babakishiyeva");
        PassengerEntity p2 = new PassengerEntity(2L, "Javad", "Nazarli");
        PassengerEntity p3 = new PassengerEntity(3L, "Emin", "Eyvazov");


        BookingEntity booking1 = new BookingEntity(1L, flight1, List.of(p1), false);
        BookingEntity booking2 = new BookingEntity(2L, flight1, List.of(p2), false);
        BookingEntity booking3 = new BookingEntity(3L, flight1, List.of(p3), false);

        while (true) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    showOnlineBoard();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    System.out.println("Exiting the application.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");


            }
        }
    }

    public static void showFlightInfo() {
        System.out.print("\nEnter flight ID: ");
        String flightId = scanner.nextLine();
        FlightEntity flight = flights.get(flightId);
        if (flight != null) {
            System.out.println(flight);
        } else {
            System.out.println("Flight not found.");
        }
    }

    public static void showOnlineBoard() {
        System.out.println("\nOnline-board - Flights from Kiev in the next 24 hours:");

    }

    public static void searchAndBookFlight() {
        System.out.print("\nEnter destination: ");
        String destination = scanner.nextLine();
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter number of people: ");
        int numPeople = scanner.nextInt();
        scanner.nextLine();


    }

    public static void cancelBooking() {
        System.out.print("\nEnter booking ID: ");
        long id = scanner.nextLong();


    }

    public static void displayMainMenu() {
        System.out.printf("\nMain Menu:\n1. Online-board\n2. Show the flight info\n3. Search and book a flight\n4. Cancel the booking\n5. My flights\n6. Exit%n");
        System.out.print("Enter your choice: ");
    }

    private static void run(PassengerController passengerController) throws DatabaseException {
        System.out.println(passengerController.create(new CreatePassengerRequest(1, "Gurbanli", "joshgun123")));
        System.out.println(passengerController.findById(1));
    }
}
