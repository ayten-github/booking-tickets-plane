package az.edu.turing;

import az.edu.turing.controller.BookingController;
import az.edu.turing.controller.FlightController;
import az.edu.turing.controller.PassengerController;
import az.edu.turing.domain.dao.abstracts.BookingDao;
import az.edu.turing.domain.dao.abstracts.FlightDao;
import az.edu.turing.domain.dao.abstracts.PassengerDao;
import az.edu.turing.domain.dao.impl.file.BookingFileDao;
import az.edu.turing.domain.dao.impl.file.FlightFileDao;
import az.edu.turing.domain.dao.impl.file.PassengerFileDao;
import az.edu.turing.domain.entities.BookingEntity;
import az.edu.turing.domain.entities.FlightEntity;
import az.edu.turing.domain.entities.PassengerEntity;
import az.edu.turing.exception.DatabaseException;
import az.edu.turing.exception.NotFoundException;
import az.edu.turing.mapper.BookingMapper;
import az.edu.turing.mapper.FlightMapper;
import az.edu.turing.mapper.PassengerMapper;
import az.edu.turing.model.dto.FlightDto;
import az.edu.turing.model.dto.request.CreateBookingRequest;
import az.edu.turing.service.*;

import java.time.LocalDateTime;
import java.util.*;


public class Application {

    private final FlightDao flightDao =
//            new FlightInMemoryDao();
            // new FlightDatabaseDao();
            new FlightFileDao();
    private final FlightMapper flightMapper = new FlightMapper();
    private final FlightService flightService = new FlightServiceImpl(flightDao, flightMapper);
    private final FlightController flightController = new FlightController(flightService);

    private final BookingDao bookingDao =
//            new BookingInMemoryDao();
//                 new BookingDatabaseDao();
            new BookingFileDao();
    private final BookingMapper bookingMapper = new BookingMapper();
    private final BookingService bookingservice = new BookingServiceImpl(bookingDao, bookingMapper);
    private final BookingController bookingController = new BookingController(bookingservice);
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws DatabaseException {

//        initializeData();
//        runApplication();

        PassengerEntity passengerEntity
                = new PassengerEntity("Nazirli", "javadnazirli123");
        PassengerFileDao passengerFileDao = new PassengerFileDao();
        passengerFileDao.save(passengerEntity);
        Optional<PassengerEntity> passengerOptional = passengerFileDao.getById(1L);
        if (passengerOptional.isPresent()) {
            PassengerEntity passenger = passengerOptional.get();
            System.out.println("Passenger found: " + passenger.getFirstName() + " " + passenger.getLastName());
        } else {
            System.out.println("Passenger with ID 1 not found.");
        }

        final PassengerDao passengerDao =
//                new PassengerInMemoryDao();
                new PassengerFileDao();
//          new PassengerDatabaseDao();
        PassengerMapper passengerMapper = new PassengerMapper();
        final PassengerService passengerService = new PassengerServiceImpl(passengerDao, passengerMapper);
        final PassengerController passengerController = new PassengerController(passengerService);




    }

    private static void runApplication() {
        boolean running = true;
        while (running) {
            displayMainMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        flightController.findFlightsFromKievInADay();
                        break;
                    case 2:
                        System.out.println("Enter flight id ");
                        long flightId = scanner.nextLong();
                        System.out.println(flightController.findById(flightId));
                        break;
                    case 3:
                        System.out.print("\nEnter destination: ");
                        String destination = scanner.nextLine();
                        System.out.print("Enter date (YYYY-MM-DD): ");
                        String date = scanner.nextLine();
                        System.out.print("Enter number of people: ");
                        int numPeople = scanner.nextInt();
                        scanner.nextLine();
                        List<FlightDto> flights =
                                flightController.findFlightsToDateNumber(destination, date, numPeople);
                        if (flights.isEmpty()) System.out.println("No flights found");
                        flights.forEach(System.out::println);
                        displayMenu();
                        int choice2= scanner.nextInt();
                        switch (choice2) {
                            case 0:
                                System.out.println("Return back to menu");
                                break;
                            case 1:
                                System.out.println("Enter flight id ");
                                long id=Long.parseLong(scanner.nextLine());
                                for (int i=0; i<flights.size(); i++) {
                                    System.out.println("pls enter Name");
                                    String name = scanner.nextLine();
                                    System.out.println("pls enter Surname" );
                                    String surname = scanner.nextLine();
                                    bookingController.create(new CreateBookingRequest(i,name,surname));
                                }
                                break;
                        }
                        break;
                        case 4:
                            System.out.println("Enter flight id ");
                            long id=Long.parseLong(scanner.nextLine());
                            bookingController.findById(id).setCancelled(true);
                            break;
                            case 5:
                                System.out.println("Enter your full name");
                                String fullName = scanner.nextLine();
                                break;
                    case 6:
                        running = false;
                        break;
                        default:
                            System.out.println("Invalid choice");
                            break;
                }
            } catch (NotFoundException exception| IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }


    private static void initializeData() {

        FlightEntity flight1 = new FlightEntity(
                1L,
                LocalDateTime.of(2024, 12, 2, 9, 21),
                "New York",
                "Kiev",
                100,
                80
        );

        FlightEntity flight2 = new FlightEntity(
                1L,
                LocalDateTime.of(2024, 12, 1, 8, 22),
                "Paris",
                "Kiev",
                150,
                90
        );

    }


    private static void displayMainMenu() {
        System.out.println("""
                "Main Menu:",
                "1. Online-board",
                "2. Show the flight info",
                "3. Search and book a flight",
                "4. Cancel the booking",
                "5. My flights",
                "6. Exit",
                "Enter your choice:
                """
        );
    }

    private static void displayMenu() {
        System.out.println("""
                Make choice:
                0:Return back main menu
                1:Select one of them
                """
        );
    }

}
