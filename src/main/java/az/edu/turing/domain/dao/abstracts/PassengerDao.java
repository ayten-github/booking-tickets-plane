package az.edu.turing.domain.dao.abstracts;

import az.edu.turing.domain.dao.Dao;
import az.edu.turing.domain.entities.PassengerEntity;
import az.edu.turing.exception.DatabaseException;

public abstract class PassengerDao implements Dao<PassengerEntity, Long> {

    public abstract boolean existsById(long id) throws DatabaseException;
}
