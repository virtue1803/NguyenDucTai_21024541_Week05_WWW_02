package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Experience;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories.ExperienceRepository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.IServices;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ExperienceService implements IServices<Experience, Long> {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Override
    public Experience add(Experience experience) {
        return experienceRepository.save(experience);
    }

    @Override
    public List<Experience> addMany(List<Experience> experiences) {
        return experienceRepository.saveAll(experiences);
    }

    @Override
    public Experience update(Experience experience) throws EntityIdNotFoundException {
        if (!experienceRepository.existsById(experience.getExpId())) {
            throw new EntityIdNotFoundException("Experience not found with id: " + experience.getExpId());
        }
        return experienceRepository.save(experience);
    }

    @Override
    public void delete(Long id) throws EntityIdNotFoundException {
        if (!experienceRepository.existsById(id)) {
            throw new EntityIdNotFoundException("Experience not found with id: " + id);
        }
        experienceRepository.deleteById(id);
    }

    @Override
    public Optional<Experience> getById(Long id) throws EntityIdNotFoundException {
        Optional<Experience> experience = experienceRepository.findById(id);
        if (experience.isEmpty()) {
            throw new EntityIdNotFoundException("Experience not found with id: " + id);
        }
        return experience;
    }

    @Override
    public Iterator<Experience> getAll() {
        List<Experience> experiences = experienceRepository.findAll();
        return experiences.iterator();
    }

    // Các phương thức tìm kiếm thêm dựa trên các tiêu chí
    public List<Experience> findByCandidateId(Long candidateId) {
        return experienceRepository.findByCandidate_Id(candidateId);
    }

    public List<Experience> findByCompanyName(String companyName) {
        return experienceRepository.findByCompanyNameContainingIgnoreCase(companyName);
    }

    public List<Experience> findByRole(String role) {
        return experienceRepository.findByRoleContainingIgnoreCase(role);
    }

    public List<Experience> findByCompanyNameAndRole(String companyName, String role) {
        return experienceRepository.findByCompanyNameContainingIgnoreCaseAndRoleContainingIgnoreCase(companyName, role);
    }
}
