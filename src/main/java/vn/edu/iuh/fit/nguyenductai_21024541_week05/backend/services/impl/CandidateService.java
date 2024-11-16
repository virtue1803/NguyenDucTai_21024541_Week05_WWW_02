package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Candidate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.CandidateSkill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Experience;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories.CandidateRepository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories.CandidateSkillRepository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories.ExperienceRepository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.IServices;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CandidateService implements IServices<Candidate,Long> {

    @Autowired
    private CandidateRepository cr;
    @Autowired
    private CandidateSkillRepository csr;
    @Autowired
    private ExperienceRepository er;

    @Override
    public Candidate add(Candidate candidate) {
        return cr.save(candidate);
    }

    @Override
    public List<Candidate> addMany(List<Candidate> list) {
        List<Candidate> results = new ArrayList<>();
        Iterator<Candidate> output = cr.saveAll(list).iterator();
        output.forEachRemaining(results::add);
        return results;
    }

    @Override
    public Candidate update(Candidate candidate) {
        return cr.save(candidate);
    }

    @Override
    public void delete(Long aLong) throws EntityIdNotFoundException {
        cr.delete(cr.findById(aLong).orElseThrow(() -> new EntityIdNotFoundException(aLong + "")));
    }

    @Override
    public Optional<Candidate> getById(Long aLong) throws EntityIdNotFoundException {
        return Optional.of(cr.findById(aLong).orElseThrow(() -> new EntityIdNotFoundException(aLong + "")));
    }

    @Override
    public Iterator<Candidate> getAll() {
        return cr.findAll().iterator();
    }

    public Iterator<Candidate> getAll(Pageable pageable) {
        return cr.findAll((org.springframework.data.domain.Pageable) pageable).iterator();
    }

    public Candidate checkLoginAccount(String email, String password) {
        return cr.findByEmailAndPassword(email, password).orElse(null);
    }

    public List<CandidateSkill> getCandidateSkill(Long canId) {
        List<CandidateSkill> results = new ArrayList<>();
        csr.findById_Candidate_Id(canId).iterator().forEachRemaining(results::add);
        return results;
    }

    public List<Experience> getCandidateExperience(Long canId) {
        List<Experience> results = new ArrayList<>();
        er.findByCandidate_Id(canId).iterator().forEachRemaining(results::add);
        return results;
    }
}
