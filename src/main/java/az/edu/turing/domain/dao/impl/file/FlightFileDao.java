package az.edu.turing.domain.dao.impl.file;

import az.edu.turing.config.FilePath;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import az.edu.turing.domain.dao.abstracts.FlightDao;
import az.edu.turing.domain.entities.FlightEntity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FlightFileDao extends FlightDao {

    private final AtomicLong idGenerator = new AtomicLong(0);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Optional<FlightEntity> getById(Long id) {
        return getAll().stream()
                .filter(flight -> flight.getId().equals(id))
                .findFirst();
    }

    @Override
    public Collection<FlightEntity> getAll() {
        Collection<FlightEntity> flights = new ArrayList<>();
        File file = new File(FilePath.FLIGHT_FILE_PATH);

        if (file.exists()) {
            try {
                flights = mapper.readValue(file, new TypeReference<>() {
                });
            } catch (IOException e) {
                System.err.println("Error reading FlightEntity from file: " + e.getMessage());
            }
        }
        return flights;
    }

    @Override
    public FlightEntity save(FlightEntity entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.incrementAndGet());
        }

        Collection<FlightEntity> flights = new ArrayList<>(getAll());
        flights.add(entity);

        saveAll(flights);
        return entity;
    }

    @Override
    public FlightEntity update(FlightEntity entity) {
        Collection<FlightEntity> flights = new ArrayList<>(getAll());
        FlightEntity existingFlight = flights.stream()
                .filter(flight -> flight.getId().equals(entity.getId()))
                .findFirst()
                .orElse(null);

        if (existingFlight != null) {
            flights.remove(existingFlight);
            flights.add(entity);

            saveAll(flights);
            return entity;
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Collection<FlightEntity> flights = new ArrayList<>(getAll());
        FlightEntity flightToDelete = flights.stream()
                .filter(flight -> flight.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (flightToDelete != null) {
            flights.remove(flightToDelete);
            saveAll(flights);
            System.out.println("FlightEntity with id " + id + " deleted.");
        }
    }

    private void saveAll(Collection<FlightEntity> flights) {
        try {
            mapper.writeValue(new File(FilePath.FLIGHT_FILE_PATH), flights);
            System.out.println("All FlightEntities saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving all FlightEntities: " + e.getMessage());
        }
    }

    @Override
    public boolean existsById(long id) {
        Collection<FlightEntity> flights = getAll();
        return flights.stream().anyMatch(flight -> flight.getId().equals(id));
    }
}
