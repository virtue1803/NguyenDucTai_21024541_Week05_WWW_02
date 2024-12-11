package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillLevel;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.JobSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.JobSkill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories.JobSkillRepository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.IServices;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class JobSkillService implements IServices<JobSkill, JobSkillId> {

    @Autowired
    private JobSkillRepository jobSkillRepository;

    @Override
    public JobSkill add(JobSkill jobSkill) {
        return jobSkillRepository.save(jobSkill);
    }

    @Override
    public List<JobSkill> addMany(List<JobSkill> jobSkills) {
        return jobSkillRepository.saveAll(jobSkills);
    }

    @Override
    public JobSkill update(JobSkill jobSkill) throws EntityIdNotFoundException {
        // Kiểm tra xem JobSkill có tồn tại hay không
        if (!jobSkillRepository.existsById(jobSkill.getId())) {
            throw new EntityIdNotFoundException("JobSkill with ID " + jobSkill.getId() + " not found.");
        }
        return jobSkillRepository.save(jobSkill);
    }

    @Override
    public void delete(JobSkillId id) throws EntityIdNotFoundException {
        // Kiểm tra xem JobSkill có tồn tại không
        if (!jobSkillRepository.existsById(id)) {
            throw new EntityIdNotFoundException("JobSkill with ID " + id + " not found.");
        }
        jobSkillRepository.deleteById(id);
    }

    @Override
    public Optional<JobSkill> getById(JobSkillId id) throws EntityIdNotFoundException {
        Optional<JobSkill> jobSkill = jobSkillRepository.findById(id);
        if (jobSkill.isEmpty()) {
            throw new EntityIdNotFoundException("JobSkill with ID " + id + " not found.");
        }
        return jobSkill;
    }

    @Override
    public Iterator<JobSkill> getAll() {
        return jobSkillRepository.findAll().iterator();
    }

    // Tìm kỹ năng yêu cầu cho công việc theo SkillId
    public List<JobSkill> findBySkillId(Long skillId) {
        return jobSkillRepository.findById_Skill_Id(skillId);
    }

    // Tìm kỹ năng yêu cầu cho công việc theo mức độ kỹ năng
    public List<JobSkill> findBySkillLevel(SkillLevel skillLevel) {
        return jobSkillRepository.findBySkillLevel(skillLevel);
    }

    // Tìm kỹ năng yêu cầu cho công việc theo JobId
    public List<JobSkill> findByJobId(Long jobId) {
        return jobSkillRepository.findByJobId(jobId);
    }
}
