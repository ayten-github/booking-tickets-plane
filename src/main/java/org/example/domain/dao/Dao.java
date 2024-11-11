package org.example.domain.dao;

import java.util.Collection;

public interface Dao <T,I>{

    T getById(I id);
    Collection<T> getAll();
    T save(T entity);
    T update(T entity);
    void delete(I id);
}
