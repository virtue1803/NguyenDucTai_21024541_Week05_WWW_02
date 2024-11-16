package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Company;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories.CompanyRepository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.IServices;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService implements IServices<Company,Long> {

    @Autowired
    private CompanyRepository cr;

    @Override
    public Company add(Company company) {
        return cr.save(company);
    }

    @Override
    public List<Company> addMany(List<Company> list) {
        List<Company> results = new ArrayList<>();
        Iterator<Company> output = cr.saveAll(list).iterator();
        output.forEachRemaining(results::add);
        return results;
    }

    @Override
    public Company update(Company company) {
        return cr.save(company);
    }

    @Override
    public void delete(Long aLong) throws EntityIdNotFoundException {
        cr.delete(cr.findById(aLong).orElseThrow(() -> new EntityIdNotFoundException(String.valueOf(aLong))));
    }

    @Override
    public Optional<Company> getById(Long aLong) throws EntityIdNotFoundException {
        return Optional.of(cr.findById(aLong).orElseThrow(() -> new EntityIdNotFoundException(String.valueOf(aLong))));
    }

    @Override
    public Iterator<Company> getAll() {
        return cr.findAll().iterator();
    }
}
