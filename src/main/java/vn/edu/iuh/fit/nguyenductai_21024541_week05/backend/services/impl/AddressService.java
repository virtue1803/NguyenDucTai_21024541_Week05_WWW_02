package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Address;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories.AddressRepository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.IServices;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService implements IServices<Address, Long> {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address add(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> addMany(List<Address> addresses) {
        return addressRepository.saveAll(addresses);
    }

    @Override
    public Address update(Address address) throws EntityIdNotFoundException {
        if (!addressRepository.existsById(address.getId())) {
            throw new EntityIdNotFoundException("Address not found with id: " + address.getId());
        }
        return addressRepository.save(address);
    }

    @Override
    public void delete(Long id) throws EntityIdNotFoundException {
        if (!addressRepository.existsById(id)) {
            throw new EntityIdNotFoundException("Address not found with id: " + id);
        }
        addressRepository.deleteById(id);
    }

    @Override
    public Optional<Address> getById(Long id) throws EntityIdNotFoundException {
        Optional<Address> address = addressRepository.findById(id);
        if (!address.isPresent()) {
            throw new EntityIdNotFoundException("Address not found with id: " + id);
        }
        return address;
    }

    @Override
    public Iterator<Address> getAll() {
        return addressRepository.findAll().iterator();
    }

    // Các phương thức tìm kiếm theo các tiêu chí khác
    public List<Address> findByCity(String city) {
        return addressRepository.findByCity(city);
    }

    public List<Address> findByCountry(String country) {
        return addressRepository.findByCountry(country);
    }

    public List<Address> findByStreet(String street) {
        return addressRepository.findByStreet(street);
    }
}
