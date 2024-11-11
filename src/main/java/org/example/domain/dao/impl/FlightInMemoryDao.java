package org.example.domain.dao.impl;

import org.example.domain.FlightEntity;
import org.example.domain.dao.FlightDao;


import java.util.HashSet;
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
    public FlightEntity getById(Long id) {
       return FLIGHTS.stream()
                .filter(flightEntity -> flightEntity.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public Set<FlightEntity> getAll() {
        return Set.copyOf(FLIGHTS);
    }

    @Override
    public FlightEntity update(FlightEntity flightEntity) {
        FlightEntity flight = getById(flightEntity.getId());
        if (flight!=null) {
             flight.setSeatAvailability(flightEntity.getSeatAvailability());
        }
        return flightEntity;
    }

    @Override
    public void delete(Long id) {
        FLIGHTS.removeIf(flightEntity -> flightEntity.getId().equals(id));
    }
}
