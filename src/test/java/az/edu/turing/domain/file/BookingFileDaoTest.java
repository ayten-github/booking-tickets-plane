package az.edu.turing.domain.file;

import az.edu.turing.domain.dao.impl.file.BookingFileDao;
import az.edu.turing.domain.entities.BookingEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingFileDaoTest {

    private BookingFileDao bookingDao;

    @BeforeEach
    public void setUp() {
        String FILE_PATH = "src/main/java/az/edu/turing/files/Booking_records.json";

        bookingDao = new BookingFileDao();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testSave() {
        BookingEntity booking = new BookingEntity();
        booking.setId(1L);

        BookingEntity savedBooking = bookingDao.save(booking);

        assertNotNull(savedBooking);
        assertNotNull(savedBooking.getId(), "ID should not be null after saving");
        assertEquals(1L, savedBooking.getId(), "Name should match the saved booking");
    }

    @Test
    void testGetById() {
        BookingEntity booking = new BookingEntity();
        booking.setId(2L);
        BookingEntity savedBooking = bookingDao.save(booking);

        Optional<BookingEntity> retrievedBooking = bookingDao.getById(savedBooking.getId());

        assertTrue(retrievedBooking.isPresent(), "BookingEntity should be present");
        assertEquals(savedBooking.getId(), retrievedBooking.get().getId(), "IDs should match");
        assertEquals(2L, retrievedBooking.get().getId(), "Names should match");
    }

    @Test
    void testGetAll() {
        BookingEntity booking1 = new BookingEntity();
        booking1.setId(1L);
        bookingDao.save(booking1);

        BookingEntity booking2 = new BookingEntity();
        booking2.setId(1L);
        bookingDao.save(booking2);

        Collection<BookingEntity> bookings = bookingDao.getAll();
        assertEquals(2, bookings.size(), "There should be 2 bookings");
    }

    @Test
    void testUpdate() {
        BookingEntity booking = new BookingEntity();
        booking.setId(1L);
        BookingEntity savedBooking = bookingDao.save(booking);

        savedBooking.setId(1L);
        BookingEntity updatedBooking = bookingDao.update(savedBooking);

        assertNotNull(updatedBooking, "Updated booking should not be null");
        assertEquals(1L, updatedBooking.getId(), "Updated name should match");

        Optional<BookingEntity> retrievedBooking = bookingDao.getById(savedBooking.getId());
        assertTrue(retrievedBooking.isPresent(), "Booking should be present after update");
        assertEquals(1L, retrievedBooking.get().getId(), "Name should match the updated name");
    }

    @Test
    void testDelete() {
        BookingEntity booking = new BookingEntity();
        booking.setId(3L);
        BookingEntity savedBooking = bookingDao.save(booking);

        assertTrue(bookingDao.existsById(savedBooking.getId()), "Booking should exist before deletion");

        bookingDao.delete(savedBooking.getId());

        assertFalse(bookingDao.existsById(savedBooking.getId()), "Booking should not exist after deletion");
    }

    @Test
    void testExistById() {
        BookingEntity booking = new BookingEntity();
        booking.setId(4L);
        BookingEntity savedBooking = bookingDao.save(booking);

        assertTrue(bookingDao.existsById(savedBooking.getId()), "Booking should exist");
        assertFalse(bookingDao.existsById(999L), "Non-existing ID should return false");
    }

    @AfterEach
    public void tearDown() {

        String FILE_PATH = "src/main/java/az/edu/turing/files/Booking_records.json";

        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }
}

