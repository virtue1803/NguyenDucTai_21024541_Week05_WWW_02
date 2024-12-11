package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Company;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories.CompanyRepository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.IServices;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService implements IServices<Company, Long> {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company add(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public List<Company> addMany(List<Company> companies) {
        return companyRepository.saveAll(companies);
    }

    @Override
    public Company update(Company company) throws EntityIdNotFoundException {
        if (companyRepository.existsById(company.getId())) {
            return companyRepository.save(company);
        } else {
            throw new EntityIdNotFoundException("Company not found with ID: " + company.getId());
        }
    }

    @Override
    public void delete(Long id) throws EntityIdNotFoundException {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
        } else {
            throw new EntityIdNotFoundException("Company not found with ID: " + id);
        }
    }

    @Override
    public Optional<Company> getById(Long id) throws EntityIdNotFoundException {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            return company;
        } else {
            throw new EntityIdNotFoundException("Company not found with ID: " + id);
        }
    }

    @Override
    public Iterator<Company> getAll() {
        return companyRepository.findAll().iterator();
    }

    // Tìm kiếm công ty theo tên
    public List<Company> findByCompName(String compName) {
        return companyRepository.findByCompNameContainingIgnoreCase(compName);
    }

    // Tìm kiếm công ty theo email
    public Optional<Company> findByEmail(String email) {
        return companyRepository.findByEmail(email);
    }
}
