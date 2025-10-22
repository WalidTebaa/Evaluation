package ma.projet.dao;

import java.util.List;
import java.util.Optional;

public interface IDao<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
}
