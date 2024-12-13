package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillType;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Candidate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Skill;

import java.util.List;
import java.util.Optional;
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    // Tìm kỹ năng theo tên
    List<Skill> findBySkillNameContainingIgnoreCase(String skillName);

    // Tìm kỹ năng theo loại
    List<Skill> findByType(SkillType skillType);




}