package az.edu.turing.memory;

import az.edu.turing.domain.dao.impl.memory.FlightInMemoryDao ;
import az.edu.turing.domain.entities.FlightEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

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
    public void testSaveNewFlight() {
        FlightEntity flight = new FlightEntity();
        flight.setAvailabilitySeats(100);

        FlightEntity savedFlight = flightDao.save(flight);

        assertNotNull(savedFlight.getId());
        assertEquals(100, savedFlight.getAvailabilitySeats());
        assertTrue(flightDao.existsById(savedFlight.getId()));
    }

    @Test
    public void testGetById() {
        FlightEntity flight = new FlightEntity();
        flight.setAvailabilitySeats(100);
        FlightEntity savedFlight = flightDao.save(flight);

        Optional<FlightEntity> foundFlight = flightDao.getById(savedFlight.getId());

        assertTrue(foundFlight.isPresent());
        assertEquals(savedFlight.getId(), foundFlight.get().getId());
        assertEquals(100, foundFlight.get().getAvailabilitySeats());
    }

    @Test
    public void testGetAll() {
        FlightEntity flight1 = new FlightEntity();
        flight1.setAvailabilitySeats(100);
        flightDao.save(flight1);

        FlightEntity flight2 = new FlightEntity();
        flight2.setAvailabilitySeats(200);
        flightDao.save(flight2);

        Set<FlightEntity> allFlights = flightDao.getAll();

        assertEquals(2, allFlights.size());
    }

    @Test
    public void testUpdateExistingFlight() {
        FlightEntity flight = new FlightEntity();
        flight.setAvailabilitySeats(100);
        FlightEntity savedFlight = flightDao.save(flight);

        savedFlight.setAvailabilitySeats(50);
        FlightEntity updatedFlight = flightDao.update(savedFlight);

        assertNotNull(updatedFlight);
        assertEquals(50, updatedFlight.getAvailabilitySeats());
    }

    @Test
    public void testDelete() {
        FlightEntity flight = new FlightEntity();
        flight.setAvailabilitySeats(100);
        FlightEntity savedFlight = flightDao.save(flight);

        flightDao.delete(savedFlight.getId());

        assertFalse(flightDao.existsById(savedFlight.getId()));
    }

    @Test
    public void testExistById() {
        FlightEntity flight = new FlightEntity();
        flight.setAvailabilitySeats(100);
        FlightEntity savedFlight = flightDao.save(flight);

        assertTrue(flightDao.existsById(savedFlight.getId()));
        assertFalse(flightDao.existsById(999L));
    }
}

