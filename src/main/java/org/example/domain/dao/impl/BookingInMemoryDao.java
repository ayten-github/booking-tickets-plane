package org.example.domain.dao.impl;

import org.example.domain.dao.BookingDao;
import org.example.domain.entity.BookingEntity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;



public class BookingInMemoryDao extends BookingDao {
    private static final Set<BookingEntity> BOOKINGS = new HashSet<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public BookingEntity getById(Long id) {
        return BOOKINGS.stream()
                .filter(booking -> booking.getId().equals(id.intValue()))
                .findFirst()
                .orElse(null);
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
        BOOKINGS.removeIf(booking -> booking.getId().equals(id.intValue()));
    }
}
