package az.edu.turing.controller;

import az.edu.turing.exception.DatabaseException;
import az.edu.turing.model.dto.BookingDto;
import az.edu.turing.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookingControllerTest {

    private BookingService bookingService;
    private BookingController bookingController;

    @BeforeEach
    void setUp() {
        bookingService = mock(BookingService.class);
        bookingController = new BookingController(bookingService);
    }

    @Test
    void create_ShouldReturnBookingDto_WhenSuccessful() throws DatabaseException {

        BookingDto request = new BookingDto();
        BookingDto expectedResponse = new BookingDto();

        when(bookingService.createBooking(request)).thenReturn(expectedResponse);

        BookingDto actualResponse = bookingController.create(request);

        assertEquals(expectedResponse, actualResponse);
        verify(bookingService).createBooking(request);
    }

    @Test
    void findById_ShouldReturnBookingDto_WhenBookingExists() throws DatabaseException {

        long bookingId = 1L;
        BookingDto expectedResponse = new BookingDto();

        when(bookingService.findBookingById(bookingId)).thenReturn(expectedResponse);

        BookingDto actualResponse = bookingController.findById(bookingId);

        assertEquals(expectedResponse, actualResponse);
        verify(bookingService).findBookingById(bookingId);
    }

    @Test
    void findAll_ShouldReturnListOfBookingDto_WhenBookingsExist() throws DatabaseException {

        List<BookingDto> expectedResponse = new ArrayList<>();

        when(bookingService.findAllBookings()).thenReturn(expectedResponse);

        List<BookingDto> actualResponse = bookingController.findAll();

        assertEquals(expectedResponse, actualResponse);
        verify(bookingService).findAllBookings();
    }

    @Test
    void getBookingDetails_ShouldReturnBookingDto_WhenBookingExists() throws DatabaseException {

        long bookingId = 1L;
        BookingDto expectedResponse = new BookingDto();

        when(bookingService.getBookingDetail(bookingId)).thenReturn(expectedResponse);

        BookingDto actualResponse = bookingController.getBookingDetails(bookingId);

        assertEquals(expectedResponse, actualResponse);
        verify(bookingService).getBookingDetail(bookingId);
    }

    @Test
    void findPassengerByFullName_ShouldReturnBookingDto_WhenPassengerExists() throws DatabaseException {

        String fullName = "John Doe";
        BookingDto expectedResponse = new BookingDto();

        try {
            when(bookingService.findNameSurname(fullName)).thenReturn(expectedResponse);
        } catch (DatabaseException e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }

        BookingDto actualResponse = bookingController.findPassengerByFullName(fullName);

        assertEquals(expectedResponse, actualResponse);
        verify(bookingService).findNameSurname(fullName);
    }

    @Test
    void findPassengerByFullName_ShouldReturnNull_WhenDatabaseExceptionOccurs() throws DatabaseException {

        String fullName = "John Doe";

        try {
            when(bookingService.findNameSurname(fullName)).thenThrow(new DatabaseException("Database error"));
        } catch (DatabaseException e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }

        BookingDto actualResponse = bookingController.findPassengerByFullName(fullName);

        assertNull(actualResponse);
        verify(bookingService).findNameSurname(fullName);
    }

    @Test
    void createBooking_ShouldReturnBookingDto_WhenSuccessful() throws DatabaseException {

        long flightId = 1L;
        String fullName = "John Doe";
        BookingDto expectedResponse = new BookingDto();

        when(bookingService.createBooking(flightId, fullName)).thenReturn(expectedResponse);

        BookingDto actualResponse = bookingController.createBooking(flightId, fullName);

        assertEquals(expectedResponse, actualResponse);
        verify(bookingService).createBooking(flightId, fullName);
    }
}