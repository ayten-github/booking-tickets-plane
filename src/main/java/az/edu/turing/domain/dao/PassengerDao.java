package az.edu.turing.domain.dao;

import az.edu.turing.domain.entities.PassengerEntity;
import az.edu.turing.exception.DatabaseException;

import java.sql.SQLException;

public abstract class PassengerDao
        implements Dao<PassengerEntity, Long> {

    public abstract boolean existById(long id) throws DatabaseException;



}
