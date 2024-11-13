package az.edu.turing.domain.dao.impl;

import az.edu.turing.domain.dao.FlightDao;
import az.edu.turing.domain.entities.FlightEntity;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class FlightInMemoryDao extends FlightDao {

    private static final Set<FlightEntity> FLIGHTS = new HashSet<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public FlightEntity save(FlightEntity flightEntity) {
        flightEntity.setId(idGenerator.incrementAndGet());
        FLIGHTS.add(flightEntity);
        return flightEntity;
    }

    @Override
    public Optional<FlightEntity> getById(Long id) {
       return FLIGHTS.stream()
               .filter(flightEntity -> flightEntity.getId().equals(id))
               .findFirst();
    }

    @Override
    public Set<FlightEntity> getAll() {
        return Set.copyOf(FLIGHTS);
    }

    @Override
    public FlightEntity update(FlightEntity flightEntity) {
        Optional<FlightEntity> flightOptional = getById(flightEntity.getId());

        if (flightOptional.isPresent()) {
            FlightEntity flight = flightOptional.get();
            flight.setAvailabilitySeats(flightEntity.getAvailabilitySeats());
            return flight;
        }

        return null;
    }

    @Override
    public void delete(Long id) {
        FLIGHTS.removeIf(flightEntity -> flightEntity.getId().equals(id));
    }

    @Override
    public boolean existById(long id) {
        return FLIGHTS.stream().anyMatch(flightEntity -> flightEntity.getId() == id);
    }
}
