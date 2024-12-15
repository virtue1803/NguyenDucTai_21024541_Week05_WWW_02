package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources;


import org.springframework.http.ResponseEntity;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;

import java.util.List;

public interface IResources<T, P> {
    ResponseEntity<Response> insert(T t);
    ResponseEntity<Response> insertAll(List<T> list);
//    ResponseEntity<Response> update(P p, T t)
//    ResponseEntity<Response> delete(P p);
//    ResponseEntity<Response> getById(P p);
    ResponseEntity<Response> getAll();
}
