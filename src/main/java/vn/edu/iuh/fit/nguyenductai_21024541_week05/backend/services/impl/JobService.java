package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Job;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories.JobRepository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.IServices;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class JobService implements IServices<Job, Long> {

    @Autowired
    private JobRepository jobRepository;

    // Add a new job
    @Override
    public Job add(Job job) {
        return jobRepository.save(job);
    }

    // Add multiple jobs
    @Override
    public List<Job> addMany(List<Job> jobs) {
        return jobRepository.saveAll(jobs);
    }

    // Update a job
    @Override
    public Job update(Job job) throws EntityIdNotFoundException {
        if (jobRepository.existsById(job.getId())) {
            return jobRepository.save(job);
        } else {
            throw new EntityIdNotFoundException("Job not found with id: " + job.getId());
        }
    }

    // Delete a job by its id
    @Override
    public void delete(Long jobId) throws EntityIdNotFoundException {
        if (jobRepository.existsById(jobId)) {
            jobRepository.deleteById(jobId);
        } else {
            throw new EntityIdNotFoundException("Job not found with id: " + jobId);
        }
    }

    // Get job by id
    @Override
    public Optional<Job> getById(Long jobId) throws EntityIdNotFoundException {
        Optional<Job> job = jobRepository.findById(jobId);
        if (job.isPresent()) {
            return job;
        } else {
            throw new EntityIdNotFoundException("Job not found with id: " + jobId);
        }
    }

    // Get all jobs
    @Override
    public Iterator<Job> getAll() {
        return jobRepository.findAll().iterator();
    }

    // Tìm công việc theo tên công việc
    public List<Job> findByJobName(String jobName) {
        return jobRepository.findByJobNameContainingIgnoreCase(jobName);
    }

    // Tìm công việc theo tên công ty
    public List<Job> findByCompanyName(String companyName) {
        return jobRepository.findByCompany_CompName(companyName);
    }

    // Tìm công việc theo mô tả
    public List<Job> findByJobDesc(String jobDesc) {
        return jobRepository.findByJobDescContainingIgnoreCase(jobDesc);
    }
}
