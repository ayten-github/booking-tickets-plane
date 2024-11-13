package az.edu.turing.memory;

import az.edu.turing.domain.dao.impl.BookingInMemoryDao;
import az.edu.turing.domain.entities.BookingEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BookingMemoryTest {


    private BookingInMemoryDao bookingDao;

    @BeforeEach
    public void setUp() {
        bookingDao = new BookingInMemoryDao();
    }

    @Test
    public void testSaveAndGetById() {
        BookingEntity booking = new BookingEntity();
        booking.setPassengerName("Alice");
        booking = bookingDao.save(booking);

        Optional<BookingEntity> retrievedBooking = bookingDao.getById(booking.getId());
        assertTrue(retrievedBooking.isPresent());
        assertEquals("Alice", retrievedBooking.get().getPassengerName());
    }

    @Test
    public void testGetByIdReturnsEmptyWhenNotFound() {
        Optional<BookingEntity> retrievedBooking = bookingDao.getById(999L);
        assertFalse(retrievedBooking.isPresent());
    }

    @Test
    public void testUpdateBooking() {
        BookingEntity booking = new BookingEntity();
        booking.setPassengerName("Alice");
        booking = bookingDao.save(booking);

        booking.setPassengerName("Alice Smith");
        BookingEntity updatedBooking = bookingDao.update(booking);

        assertNotNull(updatedBooking);
        assertEquals("Alice Smith", updatedBooking.getPassengerName());

        Optional<BookingEntity> retrievedBooking = bookingDao.getById(booking.getId());
        assertTrue(retrievedBooking.isPresent());
        assertEquals("Alice Smith", retrievedBooking.get().getPassengerName());
    }

    @Test
    public void testUpdateNonExistentBooking() {
        BookingEntity booking = new BookingEntity();
        booking.setId(999L); // Non-existent ID
        BookingEntity updatedBooking = bookingDao.update(booking);
        assertNull(updatedBooking); // Should return null
    }

    @Test
    public void testDeleteBooking() {
        BookingEntity booking = new BookingEntity();
        booking.setPassengerName("Alice");
        booking = bookingDao.save(booking);

        assertTrue(bookingDao.existById(booking.getId()));

        bookingDao.delete(booking.getId());

        assertFalse(bookingDao.existById(booking.getId()));
    }

    @Test
    public void testExistById() {
        BookingEntity booking = new BookingEntity();
        booking.setPassengerName("Alice");
        booking = bookingDao.save(booking);

        assertTrue(bookingDao.existById(booking.getId()));
        assertFalse(bookingDao.existById(999L));
    }

    @Test
    public void testGetAllBookings() {
        BookingEntity booking1 = new BookingEntity();
        booking1.setPassengerName("Alice");
        bookingDao.save(booking1);

        BookingEntity booking2 = new BookingEntity();
        booking2.setPassengerName("Bob");
        bookingDao.save(booking2);

        var allBookings = bookingDao.getAll();

        assertEquals(2, allBookings.size());
        assertTrue(allBookings.stream().anyMatch(b -> b.getPassengerName().equals("Alice")));
        assertTrue(allBookings.stream().anyMatch(b -> b.getPassengerName().equals("Bob")));
    }

}

