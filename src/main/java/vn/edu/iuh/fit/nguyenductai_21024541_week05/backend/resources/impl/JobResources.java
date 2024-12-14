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
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto.JobDTO;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RestController
@RequestMapping("/api/jobs")  // Update API path for Job resources
public class JobResources implements IResources<Job, Long> {

    @Autowired
    private JobService jobService;

    @Override
    @PostMapping
    public ResponseEntity<Response> insert(@RequestBody Job job) {
        try {
            Job createdJob = jobService.add(job);
            log.info("Job created successfully: {}", createdJob);
            JobDTO jobDTO = new JobDTO(createdJob.getId(), createdJob.getJobDesc(), createdJob.getJobName());
            return ResponseEntity.status(201).body(new Response(201, "Job created successfully", jobDTO));
        } catch (Exception e) {
            log.error("Error while creating job: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Failed to create job", null));
        }
    }

    @Override
    @PostMapping("/bulk")
    public ResponseEntity<Response> insertAll(@RequestBody List<Job> jobs) {
        try {
            List<Job> createdJobs = jobService.addMany(jobs);
            List<JobDTO> jobDTOs = createdJobs.stream()
                    .map(job -> new JobDTO(job.getId(), job.getJobDesc(), job.getJobName()))
                    .collect(Collectors.toList());
            log.info("Jobs created successfully: {}", createdJobs);
            return ResponseEntity.status(201).body(new Response(201, "Jobs created successfully", jobDTOs));
        } catch (Exception e) {
            log.error("Error while creating jobs: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Failed to create jobs", null));
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody Job job) {
        try {
            job.setId(id);  // Set the ID for updating
            Job updatedJob = jobService.update(job);
            log.info("Job updated successfully: {}", updatedJob);
            JobDTO jobDTO = new JobDTO(updatedJob.getId(), updatedJob.getJobDesc(), updatedJob.getJobName());
            return ResponseEntity.status(200).body(new Response(200, "Job updated successfully", jobDTO));
        } catch (EntityIdNotFoundException e) {
            log.error("Job not found with ID: {}", id);
            return ResponseEntity.status(404).body(new Response(404, "Job not found", null));
        } catch (Exception e) {
            log.error("Error while updating job: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Failed to update job", null));
        }
    }

    @Override
    @DeleteMapping("/{id}")
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
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        try {
            Optional<Job> job = jobService.getById(id);
            if (job.isPresent()) {
                log.info("Job found with ID: {}", id);
                JobDTO jobDTO = new JobDTO(job.get().getId(), job.get().getJobDesc(), job.get().getJobName());
                return ResponseEntity.status(200).body(new Response(200, "Job found", jobDTO));
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
    @GetMapping
    public ResponseEntity<Response> getAll() {
        try {
            // Fetch all jobs from the jobService
            Iterator<Job> jobsIterator = jobService.getAll();

            // Convert Iterator to List using StreamSupport
            List<Job> jobsList = StreamSupport.stream(((Iterable<Job>) () -> jobsIterator).spliterator(), false)
                    .toList();  // Collect to a List

            if (jobsList.isEmpty()) {
                return ResponseEntity.status(404).body(new Response(404, "No jobs found", null));
            }

            // Convert the List of jobs to a List of JobDTO
            List<JobDTO> jobDTOs = jobsList.stream()
                    .map(job -> new JobDTO(job.getId(), job.getJobDesc(), job.getJobName()))
                    .collect(Collectors.toList());

            return ResponseEntity.status(200).body(new Response(200, "Jobs retrieved successfully", jobDTOs));
        } catch (Exception e) {
            log.error("Error while fetching jobs: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Failed to retrieve jobs", null));
        }
    }


    // Search jobs by job name
    @GetMapping("/search/by-name")
    public ResponseEntity<Response> findByJobName(@RequestParam String jobName) {
        try {
            List<Job> jobs = jobService.findByJobName(jobName);
            List<JobDTO> jobDTOs = jobs.stream()
                    .map(job -> new JobDTO(job.getId(), job.getJobDesc(), job.getJobName()))
                    .collect(Collectors.toList());
            return ResponseEntity.status(200).body(new Response(200, "Jobs retrieved successfully", jobDTOs));
        } catch (Exception e) {
            log.error("Error while searching for jobs by name: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Failed to search jobs", null));
        }
    }

    // Search jobs by company name
    @GetMapping("/search/by-company")
    public ResponseEntity<Response> findByCompanyName(@RequestParam String companyName) {
        try {
            List<Job> jobs = jobService.findByCompanyName(companyName);
            List<JobDTO> jobDTOs = jobs.stream()
                    .map(job -> new JobDTO(job.getId(), job.getJobDesc(), job.getJobName()))
                    .collect(Collectors.toList());
            return ResponseEntity.status(200).body(new Response(200, "Jobs retrieved successfully", jobDTOs));
        } catch (Exception e) {
            log.error("Error while searching for jobs by company name: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Failed to search jobs", null));
        }
    }

    // Search jobs by description
    @GetMapping("/search/by-description")
    public ResponseEntity<Response> findByJobDesc(@RequestParam String jobDesc) {
        try {
            List<Job> jobs = jobService.findByJobDesc(jobDesc);
            List<JobDTO> jobDTOs = jobs.stream()
                    .map(job -> new JobDTO(job.getId(), job.getJobDesc(), job.getJobName()))
                    .collect(Collectors.toList());
            return ResponseEntity.status(200).body(new Response(200, "Jobs retrieved successfully", jobDTOs));
        } catch (Exception e) {
            log.error("Error while searching for jobs by description: {}", e.getMessage());
            return ResponseEntity.status(500).body(new Response(500, "Failed to search jobs", null));
        }
    }
}
