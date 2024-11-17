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
import az.edu.turing.domain.entities.PassengerEntity;
import az.edu.turing.exception.DatabaseException;
import az.edu.turing.exception.NotFoundException;
import az.edu.turing.mapper.BookingMapper;
import az.edu.turing.mapper.FlightMapper;
import az.edu.turing.mapper.PassengerMapper;
import az.edu.turing.model.dto.BookingDto;
import az.edu.turing.model.dto.FlightDto;
import az.edu.turing.model.dto.request.CreateBookingRequest;
import az.edu.turing.service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Application {

    // Make these static so they can be accessed in static context
    private static final FlightDao flightDao =
            new FlightFileDao();
    private static final FlightMapper flightMapper = new FlightMapper();
    private static final FlightService flightService = new FlightServiceImpl(flightDao, flightMapper);
    private static final FlightController flightController = new FlightController(flightService);

    private static final BookingDao bookingDao =
            new BookingFileDao();
    private static final BookingMapper bookingMapper = new BookingMapper();
    private static final BookingService bookingService = new BookingServiceImpl(bookingDao, bookingMapper);
    private static final BookingController bookingController = new BookingController(bookingService);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws DatabaseException {

        // Initialize data or start the application
        runApplication();

        // Example Passenger-related code
        PassengerEntity passengerEntity = new PassengerEntity("Nazirli", "javadnazirli123");
        PassengerFileDao passengerFileDao = new PassengerFileDao();
        passengerFileDao.save(passengerEntity);
        Optional<PassengerEntity> passengerOptional = passengerFileDao.getById(1L);
        if (passengerOptional.isPresent()) {
            PassengerEntity passenger = passengerOptional.get();
            System.out.println("Passenger found: " + passenger.getFirstName() + " " + passenger.getLastName());
        } else {
            System.out.println("Passenger with ID 1 not found.");
        }

        final PassengerDao passengerDao = new PassengerFileDao();
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
                        String dateString = scanner.nextLine();
                        LocalDate date=LocalDate.parse(dateString);
                        System.out.print("Enter number of people: ");
                        int numPeople = scanner.nextInt();
                        scanner.nextLine();
                        List<FlightDto> flights =
                                flightController.findFlightsToDateNumber(destination, date, numPeople);
                        if (flights.isEmpty()) System.out.println("No flights found");
                        flights.forEach(System.out::println);
                        displayMenu();
                        int choice2 = scanner.nextInt();
                        switch (choice2) {
                            case 0:
                                System.out.println("Return back to menu");
                                break;
                            case 1:
                                System.out.println("Enter flight id ");
                                long id = Long.parseLong(scanner.nextLine());
                                for (int i = 0; i < flights.size(); i++) {
                                    System.out.println("Please enter Name");
                                    String name = scanner.nextLine();
                                    System.out.println("Please enter Surname");
                                    String surname = scanner.nextLine();
                              //      bookingController.create();
                                }
                                break;
                        }
                        break;
                    case 4:
                        System.out.println("Enter flight id ");
                        long id = Long.parseLong(scanner.nextLine());
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
            } catch (NotFoundException | IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
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
                Enter your choice:
                """
        );
    }

    private static void displayMenu() {
        System.out.println("""
                Make choice:
                0: Return back to main menu
                1: Select one of them
                """
        );
    }
}
