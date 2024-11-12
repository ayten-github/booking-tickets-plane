package az.edu.turing.mapper;

public interface EntityMapper<E, T> {

    E toEntity(T t);

    T toDto(E e);
}
