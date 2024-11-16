package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.JobSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.JobSkill;

import java.util.List;

public interface JobSkillRepository extends JpaRepository<JobSkill, JobSkillId> {
    List<JobSkill> findById_Skill_Id(Long id);
}