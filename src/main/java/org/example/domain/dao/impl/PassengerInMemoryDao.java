package org.example.domain.dao.impl;

import org.example.domain.dao.PassengerDao;
import org.example.domain.entity.PassengerEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class PassengerInMemoryDao extends PassengerDao {

    private final List<PassengerEntity> PASSENGER = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(0);

    @Override
    public PassengerEntity getById(Long id) {
        return PASSENGER.stream()
                .filter(passenger -> passenger.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<PassengerEntity> getAll() {
        return new ArrayList<>(PASSENGER);
    }

    @Override
    public PassengerEntity save(PassengerEntity entity) {
        entity.setId(counter.incrementAndGet());
        PASSENGER.add(entity);
        return entity;
    }

    @Override
    public PassengerEntity update(PassengerEntity entity) {
        PassengerEntity byId = getById(entity.getId());
        if (byId != null) {
            byId.setFullName(entity.getFullName());
            byId.setSurname(entity.getSurname());
        }
        return byId;
    }

    @Override
    public void delete(Long id) {
        PASSENGER.removeIf(passenger -> passenger.getId().equals(id));
    }
}
