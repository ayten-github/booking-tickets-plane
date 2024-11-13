package az.edu.turing.domain.dao;

import az.edu.turing.domain.entities.FlightEntity;
import az.edu.turing.exception.DatabaseException;


public abstract class FlightDao
        implements Dao<FlightEntity, Long> {

    public abstract boolean existById(long id) throws DatabaseException;

}
