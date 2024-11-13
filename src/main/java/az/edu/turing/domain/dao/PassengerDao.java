package az.edu.turing.domain.dao;

import az.edu.turing.domain.entities.PassengerEntity;

public abstract class PassengerDao
        implements Dao<PassengerEntity, Long> {

    public abstract boolean existById(long id);

}
