package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.CandidateSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.CandidateSkill;

import java.util.List;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, CandidateSkillId> {
    List<CandidateSkill> findById_Candidate_Id(Long id);
}