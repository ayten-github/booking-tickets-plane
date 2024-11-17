package az.edu.turing.helper;

import az.edu.turing.controller.BookingController;
import az.edu.turing.controller.FlightController;
import az.edu.turing.domain.dao.abstracts.BookingDao;
import az.edu.turing.domain.dao.abstracts.FlightDao;
import az.edu.turing.domain.dao.impl.file.BookingFileDao;
import az.edu.turing.domain.dao.impl.file.FlightFileDao;
import az.edu.turing.exception.DatabaseException;
import az.edu.turing.mapper.BookingMapper;
import az.edu.turing.mapper.FlightMapper;
import az.edu.turing.model.dto.BookingDto;
import az.edu.turing.model.dto.FlightDto;
import az.edu.turing.service.BookingService;
import az.edu.turing.service.BookingServiceImpl;
import az.edu.turing.service.FlightService;
import az.edu.turing.service.FlightServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static az.edu.turing.helper.ConsoleBoard.displayMenu;

public class MenuHelper {

    private static final FlightDao flightDao = new FlightFileDao();
    private static final FlightMapper flightMapper = new FlightMapper();
    private static final FlightService flightService = new FlightServiceImpl(flightDao, flightMapper);
    private static final FlightController flightController = new FlightController(flightService);

    private static final BookingDao bookingDao = new BookingFileDao();
    private static final BookingMapper bookingMapper = new BookingMapper();
    private static final BookingService bookingService = new BookingServiceImpl(bookingDao, bookingMapper);
    private static final BookingController bookingController = new BookingController(bookingService);

    static Scanner scanner = new Scanner(System.in);

    public static void handleOnlineBoard() throws DatabaseException {
        flightController.findFlightsFromKievInADay();
    }

    public static void showFlightInfo() throws DatabaseException {
        System.out.println("Enter flight id: ");
        long flightId = scanner.nextLong();
        System.out.println(flightController.findById(flightId));
    }

    public static void searchAndBookFlight() throws DatabaseException {
        String destination = InputValidator.getValidatedStringInput("Enter destination: ");
        String dateString = InputValidator.getValidatedStringInput("Enter date (YYYY-MM-DD): ");
        LocalDate date = DateUtils.parseDate(dateString);
        int numPeople = InputValidator.getValidatedIntInput("Enter number of people: ");

        List<FlightDto> flights = flightController.findFlightsToDateNumber(destination, date, numPeople);
        if (flights.isEmpty()) System.out.println("No flights found");

        flights.forEach(System.out::println);
        displayMenu();

        int choice2 = scanner.nextInt();
        switch (choice2) {
            case 0:
                System.out.println("Return back to menu");
                break;
            case 1:
                handleFlightBooking(flights);
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    public static void cancelBooking() throws DatabaseException {
        System.out.println("Enter flight id: ");
        long id = Long.parseLong(scanner.nextLine());
        bookingController.findById(id).setCancelled(true);
    }

    public static void showMyFlights() {
        System.out.println("Enter your full name: ");
        String fullName = scanner.nextLine().trim();
        BookingDto bookingDto = bookingController.findPassengerByFullName(fullName);
        if (bookingDto != null) {
            System.out.println("Booking details: " + bookingDto);
        } else {
            System.out.println("No booking found for the given full name.");
        }

    }

    private static void handleFlightBooking(List<FlightDto> flights) throws DatabaseException {
        System.out.println("Enter flight id: ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            System.out.println("Error: Flight ID cannot be empty.");
            return;
        }

        if (!input.matches("\\d+")) {
            System.out.println("Error: Flight ID must contain only numbers.");
            return;
        }

        long id = Long.parseLong(input);

        for (int i = 0; i < flights.size(); i++) {
            System.out.println("Please enter Name: ");
            String name = scanner.nextLine().trim();
            System.out.println("Please enter Surname: ");
            String surname = scanner.nextLine().trim();

            String fullName = name + " " + surname;

            BookingDto booking = bookingController.createBooking(id, fullName);
            System.out.println("Booking successfully created: " + booking);

            break;
        }

    }

}
