package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Company;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.IResources;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.CompanyService;

import java.util.List;

@RestController
@RequestMapping("api/company")
@Slf4j
public class CompanyResources implements IResources<Company,Long> {
    @Autowired
    private CompanyService cs;
    @Override
    @PostMapping
    public ResponseEntity<Response> insert(@RequestBody Company company) {
        return null;
    }

    @Override
    public ResponseEntity<Response> insertAll(@RequestBody List<Company> list) {
        return null;
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Response> update(@PathVariable("id") Long aLong, @RequestBody Company company) {
        return null;
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Response> delete(@PathVariable("id") Long aLong) {
        return null;
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Response> getById(@PathVariable("id") Long aLong) {
        return null;
    }

    @GetMapping
    @Override
    public ResponseEntity<Response> getAll() {
        log.info("Calling get all companies");
        return ResponseEntity.ok(new Response(
                HttpStatus.OK.value(),
                "Get all companies successfully",
                cs.getAll()
        ));
    }
}
