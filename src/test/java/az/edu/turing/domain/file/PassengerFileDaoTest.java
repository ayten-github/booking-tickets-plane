package az.edu.turing.domain.file;

import az.edu.turing.domain.dao.impl.file.PassengerFileDao;
import az.edu.turing.domain.entities.PassengerEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassengerFileDaoTest {

    private PassengerFileDao passengerDao;

    @BeforeEach
    public void setUp() {
        passengerDao = new PassengerFileDao();
        String FILE_PATH = "src/main/java/az/edu/turing/files/PassengerFile.json";

        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testSave() {
        PassengerEntity passenger = new PassengerEntity();
        passenger.setFirstName("John");
        passenger.setId(4637L);

        PassengerEntity savedPassenger = passengerDao.save(passenger);

        assertNotNull(savedPassenger);
        assertNotNull(savedPassenger.getId(), "ID should not be null after saving");
        assertEquals("John", savedPassenger.getFirstName(),
                "First name should match the saved passenger");
    }

    @Test
    void testGetById() {
        PassengerEntity passenger = new PassengerEntity();
        passenger.setFirstName("Salih");
        passenger.setId(3L);
        PassengerEntity savedPassenger = passengerDao.save(passenger);

        Optional<PassengerEntity> retrievedPassenger = passengerDao.getById(savedPassenger.getId());

        assertTrue(retrievedPassenger.isPresent(), "PassengerEntity should be present");
        assertEquals(savedPassenger.getId(), retrievedPassenger.get().getId(), "IDs should match");
        assertEquals("Salih", retrievedPassenger.get().getFirstName(), "Names should match");
    }

    @Test
    void testGetAll() {
        PassengerEntity passenger1 = new PassengerEntity();
        passenger1.setFirstName("Javad");
        passenger1.setLastName("Nazirli");
        passenger1.setId(4L);
        passengerDao.save(passenger1);

        PassengerEntity passenger2 = new PassengerEntity();
        passenger2.setFirstName("Jabir");
        passenger2.setLastName("Babayev");
        passenger2.setId(5L);
        passengerDao.save(passenger2);

        Collection<PassengerEntity> passengers = passengerDao.getAll();
        assertEquals(2, passengers.size(), "There should be 2 passengers");
    }

    @Test
    void testUpdate() {
        PassengerEntity passenger = new PassengerEntity();
        passenger.setFirstName("Theo");
        passenger.setId(9L);
        PassengerEntity savedPassenger = passengerDao.save(passenger);

        savedPassenger.setFirstName("Leonardo");
        PassengerEntity updatedPassenger = passengerDao.update(savedPassenger);

        assertNotNull(updatedPassenger, "Updated passenger should not be null");

        Optional<PassengerEntity> retrievedPassenger = passengerDao.getById(savedPassenger.getId());
        assertTrue(retrievedPassenger.isPresent(), "Passenger should be present after update");
        assertEquals("Leonardo", retrievedPassenger.get().getFirstName(),
                "Name should match the updated name");
    }

    @Test
    void testDelete() {
        PassengerEntity passenger = new PassengerEntity();
        passenger.setFirstName("Martha");
        passenger.setId(89L);
        PassengerEntity savedPassenger = passengerDao.save(passenger);

        assertTrue(passengerDao.existsById(savedPassenger.getId()),
                "Passenger should exist before deletion");

        passengerDao.delete(savedPassenger.getId());

        assertFalse(passengerDao.existsById(savedPassenger.getId()),
                "Passenger should not exist after deletion");
    }

    @Test
    void testExistById() {
        PassengerEntity passenger = new PassengerEntity();
        passenger.setFirstName("Jonas");
        passenger.setLastName("Jackson");
        passenger.setId(23L);
        PassengerEntity savedPassenger = passengerDao.save(passenger);

        assertTrue(passengerDao.existsById(savedPassenger.getId()), "Passenger should exist");
        assertFalse(passengerDao.existsById(999L), "Non-existing ID should return false");
    }

    @AfterEach
    public void tearDown() {
        String FILE_PATH = "src/main/java/az/edu/turing/files/PassengerFile.json";

        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }
}




