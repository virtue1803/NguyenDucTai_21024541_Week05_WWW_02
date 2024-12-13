package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillLevel;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.CandidateSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.CandidateSkill;

import java.util.List;
@Repository
public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, CandidateSkillId> {
    // Tìm kỹ năng của ứng viên theo ID ứng viên
    List<CandidateSkill> findById_Candidate_Id(Long candidateId);
    // Tìm kỹ năng của ứng viên theo mức độ kỹ năng
    List<CandidateSkill> findBySkillLevel(SkillLevel skillLevel);


}