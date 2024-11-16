package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services;

import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface IServices<T, P> {
    T add(T t);
    List<T> addMany(List<T> list);
    T update(T t);
    void delete(P p) throws EntityIdNotFoundException;
    Optional<T> getById(P p) throws EntityIdNotFoundException;
    Iterator<T> getAll();
}
