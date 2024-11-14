package az.edu.turing.memory;

import az.edu.turing.domain.dao.impl.PassengerInMemoryDao;
import az.edu.turing.domain.entities.PassengerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassengerMemoryTest {

    private PassengerInMemoryDao passengerDao;

    @BeforeEach
    public void setUp() {
        passengerDao = new PassengerInMemoryDao();
    }

    @Test
    public void testSave() {
        PassengerEntity passenger = new PassengerEntity(1L, "John", "Doe");
        PassengerEntity savedPassenger = passengerDao.save(passenger);

        assertNotNull(savedPassenger.getId(), "Saved passenger should have an ID");
        assertEquals(1L, savedPassenger.getId(), "Saved passenger should have an ID");
        assertEquals("John", savedPassenger.getFirstName(), "Passenger name should match");
        assertEquals("Doe", savedPassenger.getLastName(), "Passenger last name should match");
    }

    @Test
    public void testGetById() {
        PassengerEntity passenger = new PassengerEntity(1L, "Jane", "Doe");
        PassengerEntity savedPassenger = passengerDao.save(passenger);
        Long id = savedPassenger.getId();

        Optional<PassengerEntity> retrievedPassenger = passengerDao.getById(id);
        assertTrue(retrievedPassenger.isPresent(), "Passenger should be found");
        assertEquals(1L, retrievedPassenger.get().getId(), "Passenger should have an ID");
        assertEquals("Jane", retrievedPassenger.get().getFirstName(), "Retrieved passenger name should match");
        assertEquals("Doe",retrievedPassenger.get().getLastName(), "Retrieved passenger last name should match");
    }

    @Test
    public void testGetAll() {

        passengerDao.save(new PassengerEntity(1L, "Jane", "Doe"));
        passengerDao.save(new PassengerEntity(2L, "Bob", "Marli"));

        Collection<PassengerEntity> passengers = passengerDao.getAll();
        assertEquals(2, passengers.size(), "There should be 2 passengers in the collection");
    }

    @Test
    public void testUpdate() {
        PassengerEntity passenger = new PassengerEntity(1L, "Jane", "Doe");
        PassengerEntity savedPassenger = passengerDao.save(passenger);
        savedPassenger.setFirstName("Charlie Updated");

        PassengerEntity updatedPassenger = passengerDao.update(savedPassenger);
        assertEquals("Charlie Updated", updatedPassenger.getFirstName(), "Passenger name should be updated");
    }

    @Test
    public void testDelete() {
        PassengerEntity passenger = new PassengerEntity(1L, "Jane", "Doe");
        PassengerEntity savedPassenger = passengerDao.save(passenger);
        Long id = savedPassenger.getId();

        passengerDao.delete(id);
        Optional<PassengerEntity> deletedPassenger = passengerDao.getById(id);
        assertFalse(deletedPassenger.isPresent(), "Passenger should be deleted");
    }

    @Test
    public void testExistById() {
        PassengerEntity passenger = new PassengerEntity(1L, "Jane", "Doe");
        PassengerEntity savedPassenger = passengerDao.save(passenger);
        Long id = savedPassenger.getId();

        assertTrue(passengerDao.existById(id), "Passenger should exist by ID");
        passengerDao.delete(id);
        assertFalse(passengerDao.existById(id), "Passenger should not exist after deletion");
    }
}
