package org.example.domain.dao.impl;

import org.example.domain.dao.PassengerDao;
import org.example.domain.entity.PassengerEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


public class PassengerFileDao extends PassengerDao {

    private final String FILE_PATH = "src/main/java/org/example/domain/files/PassengerFile.txt";
    private final AtomicLong idGenerator = new AtomicLong(0);


    @Override
    public PassengerEntity getById(Long id) {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            PassengerEntity passengerEntity;
            while (true) {
                try {
                    passengerEntity = (PassengerEntity) ois.readObject();

                    if (passengerEntity.getId().equals(id)) {
                        return passengerEntity;
                    }
                } catch (ClassNotFoundException e) {
                    System.err.println("Class not found: " + e.getMessage());
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading PassengerEntity from file: " + e.getMessage());
        }
        return null;
    }


    @Override
    public Collection<PassengerEntity> getAll() {
        List<PassengerEntity> bookings = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            PassengerEntity passengerEntity;
            while ((passengerEntity = (PassengerEntity) ois.readObject()) != null) {
                bookings.add(passengerEntity);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading PassengerEntity from file: " + e.getMessage());
        }
        return bookings;
    }

    @Override
    public PassengerEntity save(PassengerEntity entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.incrementAndGet());
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH, true))) {
            oos.writeObject(entity);
            System.out.println("PassengerEntity saved to file: " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error saving PassengerEntity: " + e.getMessage());
        }
        return entity;
    }

    @Override
    public PassengerEntity update(PassengerEntity entity) {
        List<PassengerEntity> allBookings = new ArrayList<>(getAll());
        PassengerEntity existingPassengers = allBookings.stream()
                .filter(flight -> flight.getId().equals(entity.getId()))
                .findFirst()
                .orElse(null);

        if (existingPassengers != null) {
            allBookings.remove(existingPassengers);
            allBookings.add(entity);

            saveAll(allBookings);
            return entity;
        }
        return null;

    }

    @Override
    public void delete(Long id) {
        List<PassengerEntity> allBookings = new ArrayList<>(getAll());
        PassengerEntity toPassengerDelete = allBookings.stream()
                .filter(flight -> flight.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (toPassengerDelete != null) {
            allBookings.remove(toPassengerDelete);
            saveAll(allBookings);
            System.out.println("PassengerEntity with id " + id + " deleted.");
        }

    }


    private void saveAll(List<PassengerEntity> passengers) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            for (PassengerEntity passenger : passengers) {
                oos.writeObject(passenger);
            }
            System.out.println("All PassengerEntities saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving all PassengerEntities: " + e.getMessage());
        }
    }
}
