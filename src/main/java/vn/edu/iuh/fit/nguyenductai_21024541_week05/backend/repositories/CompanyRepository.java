package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Company;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    // Tìm công ty theo tên
    List<Company> findByCompNameContainingIgnoreCase(String compName);

    // Tìm công ty theo email
    Optional<Company> findByEmail(String email);
}