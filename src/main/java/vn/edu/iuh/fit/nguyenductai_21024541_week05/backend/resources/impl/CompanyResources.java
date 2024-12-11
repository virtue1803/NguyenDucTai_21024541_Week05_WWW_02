package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Company;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.CompanyService;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.IResources;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/companies")
public class CompanyResources implements IResources<Company, Long> {

    @Autowired
    private CompanyService companyService;

    // Thêm một công ty mới
    @Override
    @PostMapping
    public ResponseEntity<Response> insert(@RequestBody Company company) {
        try {
            Company savedCompany = companyService.add(company);
            return ResponseEntity.ok(new Response(200, "Company added successfully", savedCompany));
        } catch (Exception e) {
            log.error("Error adding company: ", e);
            return ResponseEntity.status(500).body(new Response(500, "Failed to add company", null));
        }
    }

    // Thêm nhiều công ty
    @Override
    @PostMapping("/bulk")
    public ResponseEntity<Response> insertAll(@RequestBody List<Company> companies) {
        try {
            List<Company> savedCompanies = companyService.addMany(companies);
            return ResponseEntity.ok(new Response(200, "Companies added successfully", savedCompanies));
        } catch (Exception e) {
            log.error("Error adding companies: ", e);
            return ResponseEntity.status(500).body(new Response(500, "Failed to add companies", null));
        }
    }

    // Cập nhật công ty
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody Company company) {
        try {
            company.setId(id);
            Company updatedCompany = companyService.update(company);
            return ResponseEntity.ok(new Response(200, "Company updated successfully", updatedCompany));
        } catch (Exception e) {
            log.error("Error updating company: ", e);
            return ResponseEntity.status(500).body(new Response(500, "Failed to update company", null));
        }
    }

    // Xóa công ty
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        try {
            companyService.delete(id);
            return ResponseEntity.ok(new Response(200, "Company deleted successfully", null));
        } catch (Exception e) {
            log.error("Error deleting company: ", e);
            return ResponseEntity.status(500).body(new Response(500, "Failed to delete company", null));
        }
    }

    // Lấy công ty theo ID
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        try {
            Optional<Company> company = companyService.getById(id);
            if (company.isPresent()) {
                return ResponseEntity.ok(new Response(200, "Company found", company.get()));
            } else {
                return ResponseEntity.status(404).body(new Response(404, "Company not found", null));
            }
        } catch (Exception e) {
            log.error("Error fetching company: ", e);
            return ResponseEntity.status(500).body(new Response(500, "Failed to fetch company", null));
        }
    }

    // Lấy tất cả công ty
    @Override
    @GetMapping
    public ResponseEntity<Response> getAll() {
        try {
            Iterator<Company> companies = companyService.getAll();
            return ResponseEntity.ok(new Response(200, "Companies retrieved successfully", companies));
        } catch (Exception e) {
            log.error("Error fetching companies: ", e);
            return ResponseEntity.status(500).body(new Response(500, "Failed to fetch companies", null));
        }
    }

    // Tìm công ty theo tên
    @GetMapping("/search/name")
    public ResponseEntity<Response> findByCompName(@RequestParam String name) {
        try {
            List<Company> companies = companyService.findByCompName(name);
            return ResponseEntity.ok(new Response(200, "Companies found", companies));
        } catch (Exception e) {
            log.error("Error searching companies by name: ", e);
            return ResponseEntity.status(500).body(new Response(500, "Failed to search companies by name", null));
        }
    }

    // Tìm công ty theo email
    @GetMapping("/search/email")
    public ResponseEntity<Response> findByEmail(@RequestParam String email) {
        try {
            Optional<Company> company = companyService.findByEmail(email);
            if (company.isPresent()) {
                return ResponseEntity.ok(new Response(200, "Company found", company.get()));
            } else {
                return ResponseEntity.status(404).body(new Response(404, "Company not found", null));
            }
        } catch (Exception e) {
            log.error("Error searching company by email: ", e);
            return ResponseEntity.status(500).body(new Response(500, "Failed to search company by email", null));
        }
    }
}
