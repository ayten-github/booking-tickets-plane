package org.example.domain.dao.impl;

import org.example.domain.dao.FlightDao;
import org.example.domain.entity.FlightEntity;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FlightFileDao extends FlightDao {

    private final String FILE_PATH = "src/main/java/org/example/domain/files/Flight_records";
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public FlightEntity getById(Long id) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            FlightEntity flightEntity;
            while ((flightEntity = (FlightEntity) ois.readObject()) != null) {
                if (flightEntity.getId().equals(id)) {
                    return flightEntity;
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
    public Collection<FlightEntity> getAll() {
        List<FlightEntity> flights = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            FlightEntity flightEntity;
            while ((flightEntity = (FlightEntity) ois.readObject()) != null) {
                flights.add(flightEntity);
            }
        } catch (EOFException e) {
            // Reached the end of the file, no further processing is needed, so we can safely ignore this exception.
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading FlightEntity from file: " + e.getMessage());
        }
        return flights;
    }

    @Override
    public FlightEntity save(FlightEntity entity) {
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
    public FlightEntity update(FlightEntity entity) {
        List<FlightEntity> allFlights = new ArrayList<>(getAll());
        FlightEntity existingFlight = allFlights.stream()
                .filter(flight -> flight.getId().equals(entity.getId()))
                .findFirst()
                .orElse(null);

        if (existingFlight != null) {
            allFlights.remove(existingFlight);
            allFlights.add(entity);

            saveAll(allFlights);
            return entity;
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        List<FlightEntity> allFlights = new ArrayList<>(getAll());
        FlightEntity flightToDelete = allFlights.stream()
                .filter(flight -> flight.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (flightToDelete != null) {
            allFlights.remove(flightToDelete);
            saveAll(allFlights);
            System.out.println("FlightEntity with id " + id + " deleted.");
        }
    }

    private void saveAll(List<FlightEntity> flights) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            for (FlightEntity flight : flights) {
                oos.writeObject(flight);
            }
            System.out.println("All FlightEntities saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving all FlightEntities: " + e.getMessage());
        }
    }
}
