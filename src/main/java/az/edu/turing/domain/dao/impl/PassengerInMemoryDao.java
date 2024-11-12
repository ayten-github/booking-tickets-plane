package az.edu.turing.domain.dao.impl;

import az.edu.turing.domain.dao.PassengerDao;
import az.edu.turing.domain.entity.PassengerEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class PassengerInMemoryDao extends PassengerDao {

    private static final List<PassengerEntity> PASSENGERS = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(0);

    @Override
    public Optional<PassengerEntity> getById(Long id) {
        return Optional.ofNullable(PASSENGERS.stream()
                .filter(passenger -> passenger.getId().equals(id))
                .findFirst()
                .orElse(null));
    }

    @Override
    public Collection<PassengerEntity> getAll() {
        return new ArrayList<>(PASSENGERS);
    }

    @Override
    public PassengerEntity save(PassengerEntity entity) {
        entity.setId(counter.incrementAndGet());
        PASSENGERS.add(entity);
        return entity;
    }

    @Override
    public PassengerEntity update(PassengerEntity entity) {
        Optional<PassengerEntity> passengerOptional = getById(entity.getId());

        if (passengerOptional.isPresent()) {
            PassengerEntity passenger = passengerOptional.get();
            passenger.setFirstName(entity.getFirstName());
            passenger.setLastName(entity.getLastName());
            return passenger;
        }

        return null;
    }

    @Override
    public void delete(Long id) {
        PASSENGERS.removeIf(passenger -> passenger.getId().equals(id));
    }

    @Override
    public boolean existByLogin(String login) {
        return PASSENGERS.stream()
                .anyMatch(passengerEntity -> passengerEntity.getLogin().equals(login));
    }
}
