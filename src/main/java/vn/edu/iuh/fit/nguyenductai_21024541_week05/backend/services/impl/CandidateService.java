package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.CandidateRole;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Candidate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories.CandidateRepository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.IServices;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CandidateService implements IServices<Candidate, Long> {

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public Candidate add(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public List<Candidate> addMany(List<Candidate> list) {
        return candidateRepository.saveAll(list);
    }

    @Override
    public Candidate update(Candidate candidate) throws EntityIdNotFoundException {
        if (!candidateRepository.existsById(candidate.getId())) {
            throw new EntityIdNotFoundException("Candidate not found with id: " + candidate.getId());
        }
        return candidateRepository.save(candidate);
    }

    @Override
    public void delete(Long id) throws EntityIdNotFoundException {
        if (!candidateRepository.existsById(id)) {
            throw new EntityIdNotFoundException("Candidate not found with id: " + id);
        }
        candidateRepository.deleteById(id);
    }

    @Override
    public Optional<Candidate> getById(Long id) throws EntityIdNotFoundException {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        if (!candidate.isPresent()) {
            throw new EntityIdNotFoundException("Candidate not found with id: " + id);
        }
        return candidate;
    }

    @Override
    public Iterator<Candidate> getAll() {
        return candidateRepository.findAll().iterator();
    }

    // Tìm kiếm ứng viên theo email và password
    public Optional<Candidate> findByEmailAndPassword(String email, String password) {
        return candidateRepository.findByEmailAndPassword(email, password);
    }

    // Tìm ứng viên theo vai trò
    public List<Candidate> findByRole(CandidateRole role) {
        return candidateRepository.findByRole(role);
    }

    // Tìm ứng viên theo tên
    public List<Candidate> findByNameContainingIgnoreCase(String name) {
        return candidateRepository.findByFullNameContainingIgnoreCase(name);
    }

    // Tìm ứng viên theo ngày sinh trong khoảng thời gian
    public List<Candidate> findByDobBetween(LocalDate startDate, LocalDate endDate) {
        return candidateRepository.findByDobBetween(startDate, endDate);
    }
}
