package az.edu.turing.domain.dao.impl;

import az.edu.turing.domain.dao.PassengerDao;
import az.edu.turing.domain.entity.PassengerEntity;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class PassengerInMemoryDao extends PassengerDao {

    private static final Map<Long,PassengerEntity> PASSENGERS = new HashMap<>();
    private final AtomicLong counter = new AtomicLong(0);

    @Override
    public Optional<PassengerEntity> getById(Long id) {
        return PASSENGERS.equals(id) ? Optional.of(PASSENGERS.get(id)) : Optional.empty();
    }

    @Override
    public Collection<PassengerEntity> getAll() {
        return PASSENGERS.values();
    }

    @Override
    public PassengerEntity save(PassengerEntity entity) {
        entity.setId(counter.incrementAndGet());
        PASSENGERS.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public PassengerEntity update(PassengerEntity entity) {
        PASSENGERS.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void delete(Long id) {
        PASSENGERS.remove(id);
    }

}
