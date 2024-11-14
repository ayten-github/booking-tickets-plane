package az.edu.turing.memory;

import az.edu.turing.domain.dao.impl.BookingInMemoryDao;
import az.edu.turing.domain.entities.BookingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BookingMemoryDaoTest {

    private BookingInMemoryDao bookingDao;

    @BeforeEach
    public void setUp() {
        bookingDao = new BookingInMemoryDao();
    }

    @Test
    public void testSaveNewBooking() {
        BookingEntity booking = new BookingEntity();
        booking.setId(null);

        BookingEntity savedBooking = bookingDao.save(booking);

        assertNotNull(savedBooking.getId());
        assertTrue(bookingDao.existById(savedBooking.getId()));
    }

    @Test
    public void testGetById() {
        BookingEntity booking = new BookingEntity();
        booking.setId(null);
        BookingEntity savedBooking = bookingDao.save(booking);

        Optional<BookingEntity> foundBooking = bookingDao.getById(savedBooking.getId());

        assertTrue(foundBooking.isPresent());
        assertEquals(savedBooking.getId(), foundBooking.get().getId());
    }

    @Test
    public void testGetAll() {
        BookingEntity booking1 = new BookingEntity();
        booking1.setId(null);
        bookingDao.save(booking1);

        BookingEntity booking2 = new BookingEntity();
        booking2.setId(null);
        bookingDao.save(booking2);

        Collection<BookingEntity> allBookings = bookingDao.getAll();

        assertEquals(2, allBookings.size());
    }

    @Test
    public void testDelete() {
        BookingEntity booking = new BookingEntity();
        booking.setId(null);
        BookingEntity savedBooking = bookingDao.save(booking);

        bookingDao.delete(savedBooking.getId());

        assertFalse(bookingDao.existById(savedBooking.getId()));
    }

    @Test
    public void testExistById() {
        BookingEntity booking = new BookingEntity();
        booking.setId(null);
        BookingEntity savedBooking = bookingDao.save(booking);

        assertTrue(bookingDao.existById(savedBooking.getId()));
        assertFalse(bookingDao.existById(999L));
    }
}
