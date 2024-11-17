package az.edu.turing;

import az.edu.turing.controller.BookingController;
import az.edu.turing.controller.FlightController;
import az.edu.turing.domain.dao.abstracts.BookingDao;
import az.edu.turing.domain.dao.abstracts.FlightDao;
import az.edu.turing.domain.dao.impl.file.BookingFileDao;
import az.edu.turing.domain.dao.impl.file.FlightFileDao;
import az.edu.turing.domain.dao.impl.file.PassengerFileDao;
import az.edu.turing.domain.entities.BookingEntity;
import az.edu.turing.domain.entities.FlightEntity;
import az.edu.turing.domain.entities.PassengerEntity;
import az.edu.turing.exception.DatabaseException;
import az.edu.turing.mapper.BookingMapper;
import az.edu.turing.mapper.FlightMapper;
import az.edu.turing.service.*;

import java.util.Optional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


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

    private final static Map<Integer, FlightEntity> flights = new HashMap<>();
    private final static Map<Integer, BookingEntity> bookings = new HashMap<>();
    private final static Map<Integer, List<BookingEntity>> passengerBookings = new HashMap<>();
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

//        final PassengerDao passengerDao =
////                new PassengerInMemoryDao();
//                new PassengerFileDao();
////          new PassengerDatabaseDao();
//        PassengerMapper passengerMapper = new PassengerMapper();
//        final PassengerService passengerService = new PassengerServiceImpl(passengerDao, passengerMapper);
//        final PassengerController passengerController = new PassengerController(passengerService);
//
//        run(passengerController);
//       passengerEntity = new PassengerEntity(34L, "Javad", "Nazirli");
//       passengerFileDao = new PassengerFileDao();
//       passengerFileDao.save(passengerEntity);
//
//    }
//
//    private static void runApplication() {
//        while (true) {
//            displayMainMenu();
//            int choice = scanner.nextInt();
//            scanner.nextLine();
//
//            try {
//                switch (choice) {
//                    case 1 -> showOnlineBoard();
//                    case 2 -> showFlightInfo();
//                    case 3 -> searchAndBookFlight();
//                    case 4 -> cancelBooking();
//                    case 5 -> displayPassengerFlights();
//                    case 6 -> exitApplication();
//                    default -> throw new InvalidOptionException("Invalid choice. Please try again.");
//                }
//            } catch (InvalidOptionException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//    }
//
//
//    private static void initializeData() {
//
//        FlightEntity flight1 = new FlightEntity(
//                1L,
//                LocalDateTime.of(2024, 12, 2, 9, 21),
//                "New York",
//                "Kiev",
//                100,
//                80
//        );
//
//        FlightEntity flight2 = new FlightEntity(
//                1L,
//                LocalDateTime.of(2024, 12, 1, 8, 22),
//                "Paris",
//                "Kiev",
//                150,
//                90
//        );
//
//        flights.put(1, flight1);
//        flights.put(2, flight2);
//
//        PassengerEntity p1 = new PassengerEntity(1L, "Ayten", "Babakishiyeva");
//        PassengerEntity p2 = new PassengerEntity(2L, "Javad", "Nazarli");
//        PassengerEntity p3 = new PassengerEntity(3L, "Emin", "Eyvazov");
//
//        BookingEntity booking1 = new BookingEntity(1L, flight1, List.of(p1), false);
//        BookingEntity booking2 = new BookingEntity(2L, flight1, List.of(p2), false);
//        BookingEntity booking3 = new BookingEntity(3L, flight1, List.of(p3), false);
//
//        bookings.put(1, booking1);
//        bookings.put(2, booking2);
//        bookings.put(3, booking3);
//    }
//
//
//    private static void displayMainMenu() {
//        System.out.println("\nMain Menu:");
//        System.out.println("1. Online-board");
//        System.out.println("2. Show the flight info");
//        System.out.println("3. Search and book a flight");
//        System.out.println("4. Cancel the booking");
//        System.out.println("5. My flights");
//        System.out.println("6. Exit");
//        System.out.print("Enter your choice: ");
//
//    }
//
//    public static void showFlightInfo() {
//        System.out.print("\nEnter flight ID: ");
//        int flightId = scanner.nextInt();
//        scanner.nextLine();
//        FlightEntity flight = flights.get(flightId);
//        if (flight != null) {
//            System.out.println(flight);
//        } else {
//            System.out.println("Flight not found.");
//        }
//    }
//
//    public static void showOnlineBoard() {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime twentyFourHoursLater = now.plusHours(24);
//
//        System.out.println("\nOnline-board - Flights from Kiev in the next 24 hours:");
//
//        for (FlightEntity flight : flights.values()) {
//            if (flight.getOrigin().equals("Kiev") &&
//                    flight.getDepartureDate().isAfter(now) &&
//                    flight.getDepartureDate().isBefore(twentyFourHoursLater)) {
//                System.out.println("Flight from " + flight.getOrigin() + " to " + flight.getDestination());
//            }
//
//        }
//    }
//
//    public static void searchAndBookFlight() {
//        System.out.print("\nEnter destination: ");
//        String destination = scanner.nextLine();
//        System.out.print("Enter date (YYYY-MM-DD): ");
//        String date = scanner.nextLine();
//        System.out.print("Enter number of people: ");
//        int numPeople = scanner.nextInt();
//        scanner.nextLine();
//
//        List<PassengerEntity> passengers = new ArrayList<>();
//        for (int i = 0; i < numPeople; i++) {
//            System.out.print("Enter passenger " + (i + 1) + " full name: ");
//            String firstName = scanner.nextLine();
//            PassengerEntity passenger = new PassengerEntity();
//            passenger.setFirstName(firstName);
//            passengers.add(passenger);
//        }
//
//
//        Optional<FlightEntity> flightOpt = flights.values().stream()
//                .filter(flight -> flight.getDestination().equalsIgnoreCase(destination)
//                        && flight.getDepartureDate().toLocalDate().toString().equals(date))
//                .findFirst();
//
//        if (flightOpt.isPresent()) {
//            FlightEntity flight = flightOpt.get();
//            BookingEntity booking = new BookingEntity();
//            booking.setFlightId(flight);
//            booking.setPassengers(passengers);
//            booking.setCancelled(false);
//
//            long newBookingId = bookings.size() + 1;
//            booking.setId(newBookingId);
//            bookings.put((int) newBookingId, booking);
//
//            System.out.println("Booking successful!");
//            System.out.println("Booking ID: " + newBookingId);
//        } else {
//            System.out.println("No flights found for the given destination and date.");
//        }
//
//
//    }
//
//    public static void cancelBooking() {
//        System.out.print("\nEnter booking ID: ");
//        long id = scanner.nextLong();
//        scanner.nextLine();
//
//        BookingEntity booking = bookings.get(id);
//        if (booking != null) {
//            booking.setCancelled(true);
//            System.out.println("Booking with ID " + id + " has been canceled.");
//        } else {
//            System.out.println("Booking ID " + id + " not found.");
//        }
//
//
//    }
//
//    private static void displayPassengerFlights() {
//        System.out.print("\nEnter your full name: ");
//        String fullName = scanner.nextLine();
//        passengerBookings.forEach((key, bookings) -> {
//            bookings.forEach(booking -> {
//                for (PassengerEntity passenger : booking.getPassengers()) {
//                    if (passenger.getFullName().equals(fullName)) {
//                        System.out.println("Flight ID: " + booking.getFlight());
//                        System.out.println("Flight: " + booking.getFlight().getOrigin() + " -> " + booking.getFlight().getDestination());
//                        System.out.println("Booking ID: " + booking.getId());
//                        System.out.println("Booking Status: " + (booking.isCancelled() ? "Canceled" : "Active"));
//                        System.out.println("Passengers: " + booking.getPassengers());
//                        System.out.println("====================================");
//                    }
//                }
//            });
//        });
//
//
//    }
//
//
//    private static void run(PassengerController passengerController) throws DatabaseException {
//        System.out.println(passengerController.create(new CreatePassengerRequest(1, "Gurbanli", "joshgun123")));
//        System.out.println(passengerController.findById(1));
//    }
//
//    private static void exitApplication() {
//        System.out.println("Exiting the application.");
//        System.exit(0);
//    }
//
//
//}
    }
}