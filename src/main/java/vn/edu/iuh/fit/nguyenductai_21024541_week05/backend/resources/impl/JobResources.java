package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Job;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.IResources;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.JobService;

import java.util.List;

@RestController
@RequestMapping("api/jobs")
@Slf4j
public class JobResources implements IResources<Job,Long> {

    @Autowired
    private JobService js;
    @PostMapping
    @Override
    public ResponseEntity<Response> insert(@RequestBody Job job) {
        return null;
    }

    @Override
    public ResponseEntity<Response> insertAll(@RequestBody List<Job> list) {
        return null;
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Response> update(@PathVariable("id") Long aLong, @RequestBody Job job) {
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

    @Override
    @GetMapping
    public ResponseEntity<Response> getAll() {
        return null;
    }
}
