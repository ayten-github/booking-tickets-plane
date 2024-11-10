package org.example.domain.dao;

import java.util.Collection;

public interface Dao <T,Long>{

    T getById(java.lang.Long id);
    Collection<T> getAll();
    T save(T entity);
    T update(T entity);
    void delete(java.lang.Long id);
}
