package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Address;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories.AddressRepository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.IServices;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
@Service
public class AddressService implements IServices<Address,Long> {

    @Autowired
    private AddressRepository ar;
    @Override
    public Address add(Address address) {
        return ar.save(address);
    }



    @Override
    public List<Address> addMany(List<Address> list) {
        List<Address> results = new ArrayList<>();
        Iterator<Address> output = ar.saveAll(list).iterator();
        output.forEachRemaining(results::add);
        return results;
    }

    @Override
    public Address update(Address address) {
        return ar.save(address);
    }

    @Override
    public void delete(Long aLong) throws EntityIdNotFoundException {
        ar.delete(getById(aLong).orElseThrow(() -> new EntityIdNotFoundException(aLong + "")));
    }

    @Override
    public Optional<Address> getById(Long aLong) throws EntityIdNotFoundException {
        return Optional.of(ar.findById(aLong).orElseThrow(() -> new EntityIdNotFoundException(aLong + "")));
    }

    @Override
    public Iterator<Address> getAll() {
        return ar.findAll().iterator();
    }
}
