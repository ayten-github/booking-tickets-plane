package az.edu.turing.memory;

import az.edu.turing.domain.dao.impl.memory.PassengerInMemoryDao;
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
    public void testSaveNewPassenger() {
        PassengerEntity passenger = new PassengerEntity();
        passenger.setFirstName("John");

        PassengerEntity savedPassenger = passengerDao.save(passenger);

        assertNotNull(savedPassenger.getId());
        assertEquals("John", savedPassenger.getFirstName());
        assertTrue(passengerDao.existsById(savedPassenger.getId()));
    }

    @Test
    public void testGetById() {
        PassengerEntity passenger = new PassengerEntity();
        passenger.setFirstName("Jane");
        PassengerEntity savedPassenger = passengerDao.save(passenger);

        Optional<PassengerEntity> foundPassenger = passengerDao.getById(savedPassenger.getId());

        assertTrue(foundPassenger.isPresent());
        assertEquals(savedPassenger.getId(), foundPassenger.get().getId());
        assertEquals("Jane", foundPassenger.get().getFirstName());
    }

    @Test
    public void testGetAll() {
        PassengerEntity passenger1 = new PassengerEntity();
        passenger1.setFirstName("Alice");
        passengerDao.save(passenger1);

        PassengerEntity passenger2 = new PassengerEntity();
        passenger2.setFirstName("Bob");
        passengerDao.save(passenger2);

        Collection<PassengerEntity> allPassengers = passengerDao.getAll();

        assertEquals(2, allPassengers.size());
    }

    @Test
    public void testUpdateExistingPassenger() {
        PassengerEntity passenger = new PassengerEntity();
        passenger.setFirstName("Charlie");
        PassengerEntity savedPassenger = passengerDao.save(passenger);

        savedPassenger.setFirstName("Charlie Brown");
        PassengerEntity updatedPassenger = passengerDao.update(savedPassenger);

        assertNotNull(updatedPassenger);
        assertEquals("Charlie Brown", updatedPassenger.getFirstName());
    }

    @Test
    public void testDelete() {
        PassengerEntity passenger = new PassengerEntity();
        passenger.setFirstName("David");
        PassengerEntity savedPassenger = passengerDao.save(passenger);

        passengerDao.delete(savedPassenger.getId());

        assertFalse(passengerDao.existsById(savedPassenger.getId()));
    }

    @Test
    public void testExistById() {
        PassengerEntity passenger = new PassengerEntity();
        passenger.setFirstName("Eve");
        PassengerEntity savedPassenger = passengerDao.save(passenger);

        assertTrue(passengerDao.existsById(savedPassenger.getId()));
        assertFalse(passengerDao.existsById(999L));
    }
}

