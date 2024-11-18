package az.edu.turing.controller;

import az.edu.turing.domain.dao.Dao;
import az.edu.turing.domain.entities.BookingEntity;
import az.edu.turing.domain.entities.FlightEntity;
import az.edu.turing.domain.entities.PassengerEntity;
import az.edu.turing.exception.DatabaseException;
import az.edu.turing.exception.NotFoundException;
import az.edu.turing.model.dto.BookingDto;
import az.edu.turing.model.dto.FlightDto;
import az.edu.turing.model.dto.request.CreateBookingRequest;
import az.edu.turing.service.BookingService;
import az.edu.turing.service.FlightService;

import java.util.List;


public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;

    }

    public BookingDto create(BookingDto request) throws DatabaseException {
        return bookingService.createBooking(request);

    }

    public BookingDto findById(long id) throws DatabaseException {
        return bookingService.findBookingById(id);
    }

    public List<BookingDto> findAll() throws DatabaseException {
        return bookingService.findAllBookings();
    }

    public BookingDto getBookingDetails(long id) throws DatabaseException {
        return bookingService.getBookingDetail(id);
    }

    public BookingDto findPassengerByFullName(String fullName) {
        try {
            return bookingService.findNameSurname(fullName);
        } catch (DatabaseException e) {
            System.out.println("Error finding passenger: " + e.getMessage());
            return null;
        }
    }


    public BookingDto createBooking(long id, String fullName) throws DatabaseException {
        return bookingService.createBooking(id, fullName);

    }
}
