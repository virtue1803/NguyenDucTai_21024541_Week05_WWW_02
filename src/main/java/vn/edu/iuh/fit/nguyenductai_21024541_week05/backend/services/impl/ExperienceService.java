package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Experience;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories.ExperienceRepository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.IServices;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ExperienceService implements IServices<Experience,Long> {

    @Autowired
    private ExperienceRepository er;

    @Override
    public Experience add(Experience experience) {
        return er.save(experience);
    }

    @Override
    public List<Experience> addMany(List<Experience> list) {
        List<Experience> results = new ArrayList<>();
        Iterator<Experience> output = er.saveAll(list).iterator();
        output.forEachRemaining(results::add);
        return results;
    }

    @Override
    public Experience update(Experience experience) {
        return er.save(experience);
    }

    @Override
    public void delete(Long aLong) throws EntityIdNotFoundException {
        er.delete(er.findById(aLong).orElseThrow(() -> new EntityIdNotFoundException(String.valueOf(aLong))));
    }

    @Override
    public Optional<Experience> getById(Long aLong) throws EntityIdNotFoundException {
        return Optional.of(er.findById(aLong).orElseThrow(() -> new EntityIdNotFoundException(String.valueOf(aLong))));
    }

    @Override
    public Iterator<Experience> getAll() {
        return er.findAll().iterator();
    }
}
