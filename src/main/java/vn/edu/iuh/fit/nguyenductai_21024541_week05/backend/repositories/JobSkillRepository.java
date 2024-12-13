package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillLevel;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.JobSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.JobSkill;

import java.util.List;
@Repository
public interface JobSkillRepository extends JpaRepository<JobSkill, JobSkillId> {
    List<JobSkill> findById_Skill_Id(Long id);
    // Tìm kỹ năng yêu cầu cho công việc theo mức độ kỹ năng
    List<JobSkill> findBySkillLevel(SkillLevel skillLevel);

    // Tìm kỹ năng theo công việc (job)
    List<JobSkill> findById_Job_Id(Long jobId);




}