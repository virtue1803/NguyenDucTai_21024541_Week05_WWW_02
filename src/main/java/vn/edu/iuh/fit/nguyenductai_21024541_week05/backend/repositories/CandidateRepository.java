package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.CandidateRole;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Candidate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Optional<Candidate> findByEmailAndPassword(String email, String password);
    // Tìm kiếm ứng viên theo vai trò
    List<Candidate> findByRole(CandidateRole role);

    // Tìm ứng viên theo ID
    Optional<Candidate> findById(Long id);

    // Tìm ứng viên theo tên (nếu cần)
    List<Candidate> findByFullNameContainingIgnoreCase(String fullName);

    // Tìm ứng viên theo ngày sinh (nếu cần)
    List<Candidate> findByDobBetween(LocalDate startDate, LocalDate endDate);

}