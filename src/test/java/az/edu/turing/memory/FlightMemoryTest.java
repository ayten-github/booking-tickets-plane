package az.edu.turing.memory;

import az.edu.turing.domain.dao.impl.FlightInMemoryDao;
import az.edu.turing.domain.entities.FlightEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlightMemoryTest {

    private FlightInMemoryDao flightDao;

    @BeforeEach
    public void setUp() {

        flightDao = new FlightInMemoryDao();
    }

    @Test
    public void testSaveAndGetById() {

        FlightEntity flight = new FlightEntity();
        flight.setAvailabilitySeats(100);
        flight = flightDao.save(flight);

        Optional<FlightEntity> retrievedFlight = flightDao.getById(flight.getId());
        assertTrue(retrievedFlight.isPresent());
        assertEquals(100, retrievedFlight.get().getAvailabilitySeats());
    }

    @Test
    public void testGetByIdReturnsEmptyWhenNotFound() {

        Optional<FlightEntity> retrievedFlight = flightDao.getById(999L);
        assertFalse(retrievedFlight.isPresent());
    }

    @Test
    public void testUpdateFlight() {

        FlightEntity flight = new FlightEntity();
        flight.setAvailabilitySeats(100);
        flight = flightDao.save(flight);

        flight.setAvailabilitySeats(80);
        FlightEntity updatedFlight = flightDao.update(flight);

        assertNotNull(updatedFlight);
        assertEquals(80, updatedFlight.getAvailabilitySeats());

        Optional<FlightEntity> retrievedFlight = flightDao.getById(flight.getId());
        assertTrue(retrievedFlight.isPresent());

    }
}
