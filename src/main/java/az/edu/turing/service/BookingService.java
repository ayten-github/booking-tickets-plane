package az.edu.turing.service;

import az.edu.turing.exception.DatabaseException;
import az.edu.turing.model.dto.BookingDto;

public interface BookingService {

    BookingDto createBooking(BookingDto bookingDto) throws DatabaseException;

    BookingDto findBookingById(long id) throws DatabaseException;
}
