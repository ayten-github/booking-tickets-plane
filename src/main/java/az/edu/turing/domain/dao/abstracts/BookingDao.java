package az.edu.turing.domain.dao.abstracts;

import az.edu.turing.domain.dao.Dao;
import az.edu.turing.domain.entities.BookingEntity;
import az.edu.turing.exception.DatabaseException;

public abstract class BookingDao implements Dao<BookingEntity, Long> {

    public abstract boolean existsById(long id) throws DatabaseException;
}


