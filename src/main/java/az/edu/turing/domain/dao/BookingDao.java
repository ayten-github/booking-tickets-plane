package az.edu.turing.domain.dao;

import az.edu.turing.domain.entities.BookingEntity;
import az.edu.turing.exception.DatabaseException;

public abstract class BookingDao
        implements Dao<BookingEntity, Long> {

    public abstract boolean existById(long id) throws DatabaseException;


}


