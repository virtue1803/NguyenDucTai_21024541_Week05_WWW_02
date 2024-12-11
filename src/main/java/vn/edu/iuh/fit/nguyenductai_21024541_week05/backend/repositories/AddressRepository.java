package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Address;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    // Tìm địa chỉ theo thành phố
    List<Address> findByCity(String city);

    // Tìm địa chỉ theo quốc gia
    List<Address> findByCountry(String country);

    // Tìm địa chỉ theo đường phố
    List<Address> findByStreet(String street);
}