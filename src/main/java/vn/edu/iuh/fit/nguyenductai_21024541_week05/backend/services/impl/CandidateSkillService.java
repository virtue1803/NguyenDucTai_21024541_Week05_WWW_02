package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillLevel;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.CandidateSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.CandidateSkill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories.CandidateSkillRepository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.IServices;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CandidateSkillService implements IServices<CandidateSkill, CandidateSkillId> {

    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    // Thêm một kỹ năng mới
    @Override
    public CandidateSkill add(CandidateSkill candidateSkill) {
        return candidateSkillRepository.save(candidateSkill);
    }

    // Thêm nhiều kỹ năng
    @Override
    public List<CandidateSkill> addMany(List<CandidateSkill> list) {
        return candidateSkillRepository.saveAll(list);
    }

    // Cập nhật kỹ năng
    @Override
    public CandidateSkill update(CandidateSkill candidateSkill) throws EntityIdNotFoundException {
        if (!candidateSkillRepository.existsById(candidateSkill.getId())) {
            throw new EntityIdNotFoundException("CandidateSkill ID not found");
        }
        return candidateSkillRepository.save(candidateSkill);
    }

    // Xóa kỹ năng theo ID
    @Override
    public void delete(CandidateSkillId candidateSkillId) throws EntityIdNotFoundException {
        if (!candidateSkillRepository.existsById(candidateSkillId)) {
            throw new EntityIdNotFoundException("CandidateSkill ID not found");
        }
        candidateSkillRepository.deleteById(candidateSkillId);
    }

    // Lấy kỹ năng theo ID
    @Override
    public Optional<CandidateSkill> getById(CandidateSkillId candidateSkillId) throws EntityIdNotFoundException {
        Optional<CandidateSkill> candidateSkill = candidateSkillRepository.findById(candidateSkillId);
        if (candidateSkill.isEmpty()) {
            throw new EntityIdNotFoundException("CandidateSkill ID not found");
        }
        return candidateSkill;
    }

    // Lấy tất cả kỹ năng
    @Override
    public Iterator<CandidateSkill> getAll() {
        return candidateSkillRepository.findAll().iterator();
    }

    // Tìm kỹ năng của ứng viên theo ID ứng viên
    public List<CandidateSkill> findByCandidateId(Long candidateId) {
        return candidateSkillRepository.findById_Candidate_Id(candidateId);
    }

    // Tìm kỹ năng của ứng viên theo trình độ kỹ năng (SkillLevel)
    public List<CandidateSkill> findBySkillLevel(SkillLevel skillLevel) {
        return candidateSkillRepository.findBySkillLevel(skillLevel);
    }

    public Optional<CandidateSkill> findByCandidateIdAndSkillId(Long candidateId, Long skillId) {
        return candidateSkillRepository.findById_Candidate_IdAndId_Skill_Id(candidateId, skillId);
    }
}
