package az.edu.turing.domain.dao.impl.file;

import az.edu.turing.config.FilePath;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import az.edu.turing.domain.dao.abstracts.BookingDao;
import az.edu.turing.domain.entities.BookingEntity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class BookingFileDao extends BookingDao {

    private final AtomicLong idGenerator = new AtomicLong(1);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Optional<BookingEntity> getById(Long id) {
        return getAll().stream()
                .filter(booking -> booking.getId().equals(id))
                .findFirst();
    }

    @Override
    public Collection<BookingEntity> getAll() {
        Collection<BookingEntity> bookings = new ArrayList<>();
        File file = new File(FilePath.BOOKING_FILE_PATH);

        if (file.exists()) {
            try {
                bookings = mapper.readValue(file, new TypeReference<>() {
                });
            } catch (IOException e) {
                System.err.println("Error reading BookingEntity from file: " + e.getMessage());
            }
        }
        return bookings;
    }

    @Override
    public BookingEntity save(BookingEntity entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.incrementAndGet());
        }

        Collection<BookingEntity> bookings = new ArrayList<>(getAll());
        bookings.add(entity);

        saveAll(bookings);
        return entity;
    }

    @Override
    public BookingEntity update(BookingEntity entity) {
        Collection<BookingEntity> bookings = new ArrayList<>(getAll());
        BookingEntity existingBooking = bookings.stream()
                .filter(booking -> booking.getId().equals(entity.getId()))
                .findFirst()
                .orElse(null);

        if (existingBooking != null) {
            bookings.remove(existingBooking);
            bookings.add(entity);

            saveAll(bookings);
            return entity;
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Collection<BookingEntity> bookings = new ArrayList<>(getAll());
        BookingEntity bookingToDelete = bookings.stream()
                .filter(booking -> booking.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (bookingToDelete != null) {
            bookings.remove(bookingToDelete);
            saveAll(bookings);
            System.out.println("BookingEntity with id " + id + " deleted.");
        }
    }

    private void saveAll(Collection<BookingEntity> bookings) {
        try {
            mapper.writeValue(new File(FilePath.BOOKING_FILE_PATH), bookings);
            System.out.println("All BookingEntities saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving all BookingEntities: " + e.getMessage());
        }
    }

    @Override
    public boolean existsById(long id) {
        return getById(id).isPresent();

    }
}
