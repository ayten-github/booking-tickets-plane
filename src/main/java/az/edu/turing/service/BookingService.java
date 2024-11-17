package az.edu.turing.service;

import az.edu.turing.exception.DatabaseException;
import az.edu.turing.model.dto.BookingDto;

import java.util.List;

public interface BookingService {

    BookingDto createBooking(BookingDto bookingDto) throws DatabaseException;

    BookingDto findBookingById(long id) throws DatabaseException;

    List<BookingDto> findAllBookings() throws DatabaseException;

    BookingDto getBookingDetail(long id) throws DatabaseException;
}
