package az.edu.turing.domain.dao.impl;

import az.edu.turing.domain.dao.FlightDao;
import az.edu.turing.domain.entity.FlightEntity;


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
       return Optional.ofNullable(FLIGHTS.stream()
               .filter(flightEntity -> flightEntity.getId().equals(id))
               .findFirst().orElse(null));
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
            flight.setSeatAvailability(flightEntity.getSeatAvailability());
            return flight;
        }

        return null;
    }

    @Override
    public void delete(Long id) {
        FLIGHTS.removeIf(flightEntity -> flightEntity.getId().equals(id));
    }
}
