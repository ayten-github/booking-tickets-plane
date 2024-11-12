package az.edu.turing.domain.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import az.edu.turing.domain.dao.PassengerDao;
import az.edu.turing.domain.entity.PassengerEntity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class PassengerFileDao extends PassengerDao {

    private final String FILE_PATH = "src/main/java/org/example/files/PassengerFile.json";
    private final AtomicLong idGenerator = new AtomicLong(0);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Optional<PassengerEntity> getById(Long id) {
        Collection<PassengerEntity> passengers = getAll();

        return passengers.stream()
                .filter(passenger -> passenger.getId().equals(id))
                .findFirst();
    }

    @Override
    public Collection<PassengerEntity> getAll() {
        List<PassengerEntity> passengers = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (file.exists()) {
            try {
                passengers = mapper.readValue(file, new TypeReference<List<PassengerEntity>>() {});
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
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), passengers);
            System.out.println("All PassengerEntities saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving all PassengerEntities: " + e.getMessage());
        }
    }

    @Override
    public boolean existByLogin(String login) {
        return false;
    }
}
