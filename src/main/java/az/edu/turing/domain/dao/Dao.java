package az.edu.turing.domain.dao;

import java.util.Collection;
import java.util.Optional;

public interface Dao<T, I> {

    Optional<T> getById(I id);

    Collection<T> getAll();

    T save(T entity);

    T update(T entity);

    void delete(I id);
}
