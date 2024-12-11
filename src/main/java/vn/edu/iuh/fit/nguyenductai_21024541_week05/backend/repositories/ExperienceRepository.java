package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Experience;

import java.util.List;
@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByCandidate_Id(Long id);
    // Tìm kiếm kinh nghiệm theo công ty
    List<Experience> findByCompanyNameContainingIgnoreCase(String companyName);

    // Tìm kiếm kinh nghiệm theo vai trò
    List<Experience> findByRoleContainingIgnoreCase(String role);

    // Tìm kiếm kinh nghiệm theo công ty và vai trò
    List<Experience> findByCompanyNameContainingIgnoreCaseAndRoleContainingIgnoreCase(String companyName, String role);
}