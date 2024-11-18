package az.edu.turing.domain.file;

import az.edu.turing.domain.dao.impl.file.FlightFileDao;
import az.edu.turing.domain.entities.FlightEntity;
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

public class FlightFileDaoTest {

    private FlightFileDao flightDao;

    @BeforeEach
    public void setUp() {
        flightDao = new FlightFileDao();
        String FILE_PATH = "src/main/java/az/edu/turing/files/Flight_records.json";

        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testSave() {
        FlightEntity flight = new FlightEntity();
        flight.setId(1L);
        flight.setDestination("New York");

        FlightEntity savedFlight = flightDao.save(flight);

        assertNotNull(savedFlight);
        assertNotNull(savedFlight.getId(), "ID should not be null after saving");
        assertEquals("New York", savedFlight.getDestination(),
                "Flight destination should match the saved flight");
    }

    @Test
    void testGetById() {
        FlightEntity flight = new FlightEntity();
        flight.setId(1L);
        flight.setDestination("New York");
        FlightEntity savedFlight = flightDao.save(flight);

        Optional<FlightEntity> retrievedFlight = flightDao.getById(savedFlight.getId());

        assertTrue(retrievedFlight.isPresent(), "FlightEntity should be present");
        assertEquals(savedFlight.getId(), retrievedFlight.get().getId(), "IDs should match");
        assertEquals("New York", retrievedFlight.get().getDestination(),
                "Flight destinations should match");
    }

    @Test
    void testGetAll() {
        FlightEntity flight1 = new FlightEntity();
        flight1.setId(1L);
        flight1.setDestination("New York");
        flightDao.save(flight1);

        FlightEntity flight2 = new FlightEntity();
        flight2.setId(5L);
        flight2.setDestination("Los Angeles");
        flightDao.save(flight2);

        Collection<FlightEntity> flights = flightDao.getAll();
        assertEquals(2, flights.size(), "There should be 2 flights");
    }

    @Test
    void testUpdate() {
        FlightEntity flight = new FlightEntity();
        flight.setId(1L);
        flight.setDestination("New York");
        FlightEntity savedFlight = flightDao.save(flight);

        savedFlight.setDestination("Los Angeles"); // Update the destination
        FlightEntity updatedFlight = flightDao.update(savedFlight);

        assertNotNull(updatedFlight, "Updated flight should not be null");

        Optional<FlightEntity> retrievedFlight = flightDao.getById(savedFlight.getId());
        assertTrue(retrievedFlight.isPresent(), "Flight should be present after update");
        assertEquals("Los Angeles", retrievedFlight.get().getDestination(),
                "Destination should match the updated destination");
    }

    @Test
    void testDelete() {
        FlightEntity flight = new FlightEntity();
        flight.setId(1L);
        flight.setDestination("New York");
        FlightEntity savedFlight = flightDao.save(flight);

        assertTrue(flightDao.existsById(savedFlight.getId()), "Flight should exist before deletion");

        flightDao.delete(savedFlight.getId());

        assertFalse(flightDao.existsById(savedFlight.getId()), "Flight should not exist after deletion");
    }

    @Test
    void testExistById() {
        FlightEntity flight = new FlightEntity();
        flight.setId(1L);
        flight.setDestination("New York");
        FlightEntity savedFlight = flightDao.save(flight);

        assertTrue(flightDao.existsById(savedFlight.getId()), "Flight should exist");
        assertFalse(flightDao.existsById(999L), "Non-existing ID should return false");
    }

    @AfterEach
    public void tearDown() {
        String FILE_PATH = "src/main/java/az/edu/turing/files/Flight_records.json";

        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }
}











