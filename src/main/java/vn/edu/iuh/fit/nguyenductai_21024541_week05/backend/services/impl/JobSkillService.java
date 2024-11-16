package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.JobSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.JobSkill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories.JobSkillRepository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.IServices;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class JobSkillService implements IServices<JobSkill, JobSkillId> {

    @Autowired
    private JobSkillRepository jsr;

    @Override
    public JobSkill add(JobSkill jobSkill) {
        return jsr.save(jobSkill);
    }

    @Override
    public List<JobSkill> addMany(List<JobSkill> list) {
        List<JobSkill> results = new ArrayList<>();
        Iterator<JobSkill> output = jsr.saveAll(list).iterator();
        output.forEachRemaining(results::add);
        return results;
    }

    @Override
    public JobSkill update(JobSkill jobSkill) {
        return jsr.save(jobSkill);
    }

    @Override
    public void delete(JobSkillId jobSkillId) throws EntityIdNotFoundException {
        jsr.delete(getById(jobSkillId).orElseThrow(() -> new EntityIdNotFoundException("JobId: " + jobSkillId.getJob().getId() + " SkillId: " + jobSkillId.getSkill().getId())));
    }

    @Override
    public Optional<JobSkill> getById(JobSkillId jobSkillId) throws EntityIdNotFoundException {
        return Optional.of(jsr.findById(jobSkillId).orElseThrow(() -> new EntityIdNotFoundException("JobId: " + jobSkillId.getJob().getId() + " SkillId: " + jobSkillId.getSkill().getId())));
    }

    @Override
    public Iterator<JobSkill> getAll() {
        return jsr.findAll().iterator();
    }

    public List<JobSkill> getAllJobsBySkill(Long skillId) {
        return jsr.findById_Skill_Id(skillId);
    }
}
