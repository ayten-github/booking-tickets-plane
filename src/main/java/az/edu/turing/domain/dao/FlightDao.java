package az.edu.turing.domain.dao;

import az.edu.turing.domain.entities.FlightEntity;


public abstract class FlightDao
        implements Dao<FlightEntity, Long> {

    public abstract boolean existById(long id);

}
