package az.edu.turing.domain.dao.abstracts;

import az.edu.turing.domain.dao.Dao;
import az.edu.turing.domain.entities.FlightEntity;
import az.edu.turing.exception.DatabaseException;


public abstract class FlightDao implements Dao<FlightEntity, Long> {

    public abstract boolean existsById(long id) throws DatabaseException;
}
