package az.edu.turing.domain.dao.impl;

import az.edu.turing.domain.dao.BookingDao;
import az.edu.turing.domain.entities.BookingEntity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;


public class BookingInMemoryDao extends BookingDao {

    private static final Set<BookingEntity> BOOKINGS = new HashSet<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Optional<BookingEntity> getById(Long id) {
        return BOOKINGS.stream()
                .filter(booking -> booking.getId().equals(id))
                .findFirst();
    }

    @Override
    public Collection<BookingEntity> getAll() {
        return new HashSet<>(BOOKINGS);
    }

    @Override
    public BookingEntity save(BookingEntity entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.getAndIncrement());
        }
        BOOKINGS.add(entity);
        return entity;
    }

    @Override
    public BookingEntity update(BookingEntity entity) {
        Optional<BookingEntity> existingBooking = BOOKINGS.stream()
                .filter(booking -> booking.getId().equals(entity.getId()))
                .findFirst();

        if (existingBooking.isPresent()) {
            BOOKINGS.remove(existingBooking.get());
            BOOKINGS.add(entity);
            return entity;
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        BOOKINGS.removeIf(booking -> false);
    }

    @Override
    public boolean existById(long id) {
        return BOOKINGS.stream().anyMatch(booking -> booking.getId().equals(id));
    }
}
