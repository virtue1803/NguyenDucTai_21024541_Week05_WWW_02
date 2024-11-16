package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Experience;

import java.util.List;
@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByCandidate_Id(Long id);
}