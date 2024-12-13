package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Job;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models.JobModel;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/jobs")
public class JobController {

    private final JobModel jobModel;

    @Autowired
    public JobController(JobModel jobModel) {
        this.jobModel = jobModel;
    }

    // Endpoint to add a single Job
    @PostMapping("/insert")
    public String insertJob(@ModelAttribute Job job, Model model) {
        Response response = jobModel.insert(job);
        model.addAttribute("response", response);
        return "job_response"; // View to display the result
    }

    // Endpoint to add multiple Jobs
    @PostMapping("/insertAll")
    public String insertAllJobs(@ModelAttribute List<Job> jobs, Model model) {
        Response response = jobModel.insertAll(jobs);
        model.addAttribute("response", response);
        return "job_response"; // View to display the result
    }

    // Endpoint to update a Job
    @PutMapping("/update")
    public String updateJob(@RequestParam Long id, @ModelAttribute Job job, Model model) {
        Response response = jobModel.update(id, job);
        model.addAttribute("response", response);
        return "job_response"; // View to display the result
    }

    // Endpoint to delete a Job
    @DeleteMapping("/delete")
    public String deleteJob(@RequestParam Long id, Model model) {
        jobModel.delete(id);
        model.addAttribute("message", "Job deleted successfully");
        return "job_response"; // View to display the result
    }

    // Endpoint to get Job by ID
    @GetMapping("/getById")
    public String getJobById(@RequestParam Long id, Model model) {
        Optional<Job> job = jobModel.getById(id);
        model.addAttribute("job", job.orElse(null));
        return "job_details"; // View to display job details
    }

    // Endpoint to get all Jobs
    @GetMapping("/getAll")
    public String getAllJobs(Model model) {
        List<Job> jobs = jobModel.getAll();
        model.addAttribute("jobs", jobs);
        return "jobs_list"; // View to display all jobs
    }

    // Endpoint to get Jobs by Job Name
    @GetMapping("/findByJobName")
    public String findByJobName(@RequestParam String jobName, Model model) {
        List<Job> jobs = jobModel.findByJobName(jobName);
        model.addAttribute("jobs", jobs);
        return "jobs_list"; // View to display jobs by job name
    }

    // Endpoint to get Jobs by Company Name
    @GetMapping("/findByCompanyName")
    public String findByCompanyName(@RequestParam String companyName, Model model) {
        List<Job> jobs = jobModel.findByCompanyName(companyName);
        model.addAttribute("jobs", jobs);
        return "jobs_list"; // View to display jobs by company name
    }

    // Endpoint to get Jobs by Job Description
    @GetMapping("/findByJobDesc")
    public String findByJobDesc(@RequestParam String jobDesc, Model model) {
        List<Job> jobs = jobModel.findByJobDesc(jobDesc);
        model.addAttribute("jobs", jobs);
        return "jobs_list"; // View to display jobs by job description
    }
}
