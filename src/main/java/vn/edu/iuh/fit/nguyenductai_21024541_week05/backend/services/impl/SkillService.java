package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Skill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories.SkillRepository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.IServices;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillType;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class SkillService implements IServices<Skill, Long> {

    @Autowired
    private SkillRepository skillRepository;

    // Thêm kỹ năng
    @Override
    public Skill add(Skill skill) {
        return skillRepository.save(skill);
    }

    // Thêm nhiều kỹ năng
    @Override
    public List<Skill> addMany(List<Skill> list) {
        return skillRepository.saveAll(list);
    }

    // Cập nhật kỹ năng
    @Override
    public Skill update(Skill skill) throws EntityIdNotFoundException {
        if (skillRepository.existsById(skill.getId())) {
            return skillRepository.save(skill);
        } else {
            throw new EntityIdNotFoundException("Skill not found with id: " + skill.getId());
        }
    }

    // Xóa kỹ năng
    @Override
    public void delete(Long id) throws EntityIdNotFoundException {
        if (skillRepository.existsById(id)) {
            skillRepository.deleteById(id);
        } else {
            throw new EntityIdNotFoundException("Skill not found with id: " + id);
        }
    }

    // Lấy kỹ năng theo ID
    @Override
    public Optional<Skill> getById(Long id) throws EntityIdNotFoundException {
        Optional<Skill> skill = skillRepository.findById(id);
        if (skill.isPresent()) {
            return skill;
        } else {
            throw new EntityIdNotFoundException("Skill not found with id: " + id);
        }
    }

    // Lấy tất cả kỹ năng
    @Override
    public Iterator<Skill> getAll() {
        return skillRepository.findAll().iterator();
    }

    // Tìm kiếm kỹ năng theo tên (không phân biệt hoa thường)
    public List<Skill> findBySkillName(String skillName) {
        return skillRepository.findBySkillNameContainingIgnoreCase(skillName);
    }

    // Tìm kiếm kỹ năng theo loại
    public List<Skill> findBySkillType(SkillType skillType) {
        return skillRepository.findByType(skillType);
    }
}
