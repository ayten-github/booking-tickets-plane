package az.edu.turing.domain.dao;

import az.edu.turing.exception.DatabaseException;

import java.util.Collection;
import java.util.Optional;

public interface Dao<T, E> {

    Optional<T> getById(E id) throws DatabaseException;

    Collection<T> getAll() throws DatabaseException;

    T save(T entity) throws DatabaseException;

    T update(T entity);

    void delete(E id);
}
