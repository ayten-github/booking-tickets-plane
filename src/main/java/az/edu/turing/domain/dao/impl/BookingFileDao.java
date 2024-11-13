package az.edu.turing.domain.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import az.edu.turing.domain.dao.BookingDao;
import az.edu.turing.domain.entities.BookingEntity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class BookingFileDao extends BookingDao {

    private final String FILE_PATH = "src/main/java/az/edu/turing/files/Booking_records.json";
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Optional<BookingEntity> getById(Long id) {
        Collection<BookingEntity> bookings = getAll();

        return bookings.stream()
                .filter(booking -> booking.getId().equals(id))
                .findFirst();
    }

    @Override
    public Collection<BookingEntity> getAll() {
        Collection<BookingEntity> bookings = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (file.exists()) {
            try {
                bookings = mapper.readValue(file, new TypeReference<Collection<BookingEntity>>() {});
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
            mapper.writeValue(new File(FILE_PATH), bookings);
            System.out.println("All BookingEntities saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving all BookingEntities: " + e.getMessage());
        }
    }

    @Override
    public boolean existById(long id) {
        return getById(id).isPresent();

    }
}
