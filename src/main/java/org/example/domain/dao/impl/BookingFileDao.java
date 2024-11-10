package org.example.domain.dao.impl;

import org.example.domain.BookingEntity;
import org.example.domain.dao.BookingDao;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BookingFileDao extends BookingDao {
    private final AtomicInteger idGenerator = new AtomicInteger(1);
    private final String FILE_PATH = "src/main/java/org/example/domain/files/Booking_records";

    @Override
    public BookingEntity getById(Long id) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            BookingEntity bookingEntity;
            while ((bookingEntity = (BookingEntity) ois.readObject()) != null) {
                if (bookingEntity.getId().equals(id.intValue())) {
                    return bookingEntity;
                }
            }
        } catch (EOFException e) {
            // Reached the end of the file, no further processing is needed, so we can safely ignore this exception.
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading FlightEntity from file: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Collection<BookingEntity> getAll() {
        List<BookingEntity> bookings = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            BookingEntity bookingEntity;
            while ((bookingEntity = (BookingEntity) ois.readObject()) != null) {
                bookings.add(bookingEntity);
            }
        } catch (EOFException e) {
            // Reached the end of the file, no further processing is needed, so we can safely ignore this exception.
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading FlightEntity from file: " + e.getMessage());
        }
        return bookings;
    }

    @Override
    public BookingEntity save(BookingEntity entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.incrementAndGet());
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH, true))) {
            oos.writeObject(entity);
            System.out.println("FlightEntity saved to file: " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error saving FlightEntity: " + e.getMessage());
        }
        return entity;
    }

    @Override
    public BookingEntity update(BookingEntity entity) {
        List<BookingEntity> allBookings = new ArrayList<>(getAll());
        BookingEntity existingBookings = allBookings.stream()
                .filter(flight -> flight.getId().equals(entity.getId()))
                .findFirst()
                .orElse(null);

        if (existingBookings != null) {
            allBookings.remove(existingBookings);
            allBookings.add(entity);

            saveAll(allBookings);
            return entity;
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        List<BookingEntity> allBookings = new ArrayList<>(getAll());
        BookingEntity bookingtoDelete = allBookings.stream()
                .filter(flight -> flight.getId().equals(id.intValue()))
                .findFirst()
                .orElse(null);

        if (bookingtoDelete != null) {
            allBookings.remove(bookingtoDelete);
            saveAll(allBookings);
            System.out.println("FlightEntity with id " + id + " deleted.");
        }
    }

    private void saveAll(List<BookingEntity> bookings) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            for (BookingEntity booking : bookings) {
                oos.writeObject(booking);
            }
            System.out.println("All FlightEntities saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving all FlightEntities: " + e.getMessage());
        }
    }
}
