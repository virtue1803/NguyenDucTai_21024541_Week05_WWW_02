package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.JobService;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Job;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.resources.IResources;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/jobs")  // Đường dẫn API cho Job
public class JobResources implements IResources<Job, Long> {

    @Autowired
    private JobService jobService;

    @Override
    @PostMapping("/insert")
    public ResponseEntity<Response> insert(@RequestBody Job job) {
        try {
            Job createdJob = jobService.add(job);
            log.info("Job created successfully: {}", createdJob);
            return ResponseEntity.status(201).body(new Response(201, "Job created successfully", createdJob));
        } catch (Exception e) {
            log.error("Error while creating job: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Failed to create job", null));
        }
    }

    @Override
    @PostMapping("/insertAll")
    public ResponseEntity<Response> insertAll(@RequestBody List<Job> jobs) {
        try {
            List<Job> createdJobs = jobService.addMany(jobs);
            log.info("Jobs created successfully: {}", createdJobs);
            return ResponseEntity.status(201).body(new Response(201, "Jobs created successfully", createdJobs));
        } catch (Exception e) {
            log.error("Error while creating jobs: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Failed to create jobs", null));
        }
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody Job job) {
        try {
            job.setId(id);  // Set the ID for updating
            Job updatedJob = jobService.update(job);
            log.info("Job updated successfully: {}", updatedJob);
            return ResponseEntity.status(200).body(new Response(200, "Job updated successfully", updatedJob));
        } catch (EntityIdNotFoundException e) {
            log.error("Job not found with ID: {}", id);
            return ResponseEntity.status(404).body(new Response(404, "Job not found", null));
        } catch (Exception e) {
            log.error("Error while updating job: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Failed to update job", null));
        }
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        try {
            jobService.delete(id);
            log.info("Job deleted successfully with ID: {}", id);
            return ResponseEntity.status(200).body(new Response(200, "Job deleted successfully", null));
        } catch (EntityIdNotFoundException e) {
            log.error("Job not found with ID: {}", id);
            return ResponseEntity.status(404).body(new Response(404, "Job not found", null));
        } catch (Exception e) {
            log.error("Error while deleting job: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Failed to delete job", null));
        }
    }

    @Override
    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        try {
            Optional<Job> job = jobService.getById(id);
            if (job.isPresent()) {
                log.info("Job found with ID: {}", id);
                return ResponseEntity.status(200).body(new Response(200, "Job found", job.get()));
            } else {
                log.warn("Job not found with ID: {}", id);
                return ResponseEntity.status(404).body(new Response(404, "Job not found", null));
            }
        } catch (Exception e) {
            log.error("Error while retrieving job: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Failed to retrieve job", null));
        }
    }

    @Override
    @GetMapping("/getAll")
    public ResponseEntity<Response> getAll() {
        try {
            Iterator<Job> jobs = jobService.getAll();
            log.info("Fetched all jobs successfully");
            return ResponseEntity.status(200).body(new Response(200, "Jobs retrieved successfully", jobs));
        } catch (Exception e) {
            log.error("Error while fetching jobs: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Failed to retrieve jobs", null));
        }
    }

    // Tìm kiếm công việc theo tên công việc
    @GetMapping("/searchByJobName")
    public ResponseEntity<Response> findByJobName(@RequestParam String jobName) {
        try {
            List<Job> jobs = jobService.findByJobName(jobName);
            return ResponseEntity.status(200).body(new Response(200, "Jobs retrieved successfully", jobs));
        } catch (Exception e) {
            log.error("Error while searching for jobs by name: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Failed to search jobs", null));
        }
    }

    // Tìm công việc theo công ty
    @GetMapping("/searchByCompanyName")
    public ResponseEntity<Response> findByCompanyName(@RequestParam String companyName) {
        try {
            List<Job> jobs = jobService.findByCompanyName(companyName);
            return ResponseEntity.status(200).body(new Response(200, "Jobs retrieved successfully", jobs));
        } catch (Exception e) {
            log.error("Error while searching for jobs by company name: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Failed to search jobs", null));
        }
    }

    // Tìm công việc theo mô tả
    @GetMapping("/searchByJobDesc")
    public ResponseEntity<Response> findByJobDesc(@RequestParam String jobDesc) {
        try {
            List<Job> jobs = jobService.findByJobDesc(jobDesc);
            return ResponseEntity.status(200).body(new Response(200, "Jobs retrieved successfully", jobs));
        } catch (Exception e) {
            log.error("Error while searching for jobs by description: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Failed to search jobs", null));
        }
    }
}
