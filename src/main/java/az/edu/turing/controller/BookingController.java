package az.edu.turing.controller;

import az.edu.turing.exception.DatabaseException;
import az.edu.turing.model.dto.BookingDto;
import az.edu.turing.model.dto.request.CreateBookingRequest;
import az.edu.turing.service.BookingService;

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


}
