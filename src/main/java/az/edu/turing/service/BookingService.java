package az.edu.turing.service;

import az.edu.turing.model.dto.BookingDto;

public interface BookingService {

    BookingDto createBooking(BookingDto bookingDto);

    BookingDto findBookingById(long id);
}
