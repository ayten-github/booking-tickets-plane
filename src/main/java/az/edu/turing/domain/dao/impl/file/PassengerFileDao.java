package az.edu.turing.domain.dao.impl.file;

import az.edu.turing.exception.AlreadyExistsException;
import az.edu.turing.config.FilePath;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import az.edu.turing.domain.dao.abstracts.PassengerDao;
import az.edu.turing.domain.entities.PassengerEntity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class PassengerFileDao extends PassengerDao {

    private final AtomicLong idGenerator = new AtomicLong(0);
    private final ObjectMapper mapper = new ObjectMapper();

    public PassengerFileDao() {
        initializeIdGenerator();
    }

    @Override
    public Optional<PassengerEntity> getById(Long id) {
        return getAll().stream()
                .filter(passenger -> passenger.getId().equals(id))
                .findFirst();
    }

    @Override
    public Collection<PassengerEntity> getAll() {
        List<PassengerEntity> passengers = new ArrayList<>();
        File file = new File(FilePath.PASSENGER_FILE_PATH);

        if (file.exists()) {
            try {
                passengers = mapper.readValue(file, new TypeReference<>() {
                });
            } catch (IOException e) {
                System.err.println("Error reading PassengerEntity from file: " + e.getMessage());
            }
        }

        return passengers;
    }

    @Override
    public PassengerEntity save(PassengerEntity entity) {

        if (entity.getId() == null) {
            entity.setId(idGenerator.incrementAndGet());
        }

        Collection<PassengerEntity> passengers = getAll();

        boolean exists = passengers.stream()
                .anyMatch(passenger -> passenger.getId().equals(entity.getId()));

        if (exists) {
            throw new AlreadyExistsException("Passenger already exists with ID: " + entity.getId());
        }

        passengers.add(entity);

        saveAll(passengers);
        return entity;
    }

    @Override
    public PassengerEntity update(PassengerEntity entity) {
        List<PassengerEntity> passengers = new ArrayList<>(getAll());
        PassengerEntity existingPassenger = passengers.stream()
                .filter(passenger -> passenger.getId().equals(entity.getId()))
                .findFirst()
                .orElse(null);

        if (existingPassenger != null) {
            passengers.remove(existingPassenger);
            passengers.add(entity);

            saveAll(passengers);
            return entity;
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        List<PassengerEntity> passengers = new ArrayList<>(getAll());
        PassengerEntity toDelete = passengers.stream()
                .filter(passenger -> passenger.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (toDelete != null) {
            passengers.remove(toDelete);
            saveAll(passengers);
            System.out.println("PassengerEntity with id " + id + " deleted.");
        }
    }

    private void saveAll(Collection<PassengerEntity> passengers) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FilePath.PASSENGER_FILE_PATH), passengers);
            System.out.println("All PassengerEntities saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving all PassengerEntities: " + e.getMessage());
        }
    }
    
    @Override
    public boolean existsById(long id) {
        Collection<PassengerEntity> passengers = getAll();
        return passengers.stream().anyMatch(passenger -> passenger.getId() == id);
    }

    private void initializeIdGenerator() {
        Collection<PassengerEntity> passengers = getAll();
        long maxId = passengers.stream()
                .mapToLong(PassengerEntity::getId)
                .max()
                .orElse(0);
        idGenerator.set(maxId);
    }
}
