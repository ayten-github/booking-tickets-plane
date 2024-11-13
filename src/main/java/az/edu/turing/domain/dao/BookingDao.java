package az.edu.turing.domain.dao;

import az.edu.turing.domain.entities.BookingEntity;

public abstract class BookingDao
        implements Dao<BookingEntity, Long> {

    public abstract boolean existById(long id);


}


