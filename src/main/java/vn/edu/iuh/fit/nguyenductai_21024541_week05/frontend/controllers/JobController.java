package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/insert-job")
    public String insertJob(@ModelAttribute Job job, Model model) {
        try {
            Response response = jobModel.insert(job);
            model.addAttribute("response", response);
            return "job_response"; // View to display the result
        } catch (Exception e) {
            model.addAttribute("error", "Error occurred while adding the job: " + e.getMessage());
            return "error"; // Error view
        }
    }

    // Endpoint to add multiple Jobs
    @PostMapping("/insertAll-job")
    public String insertAllJobs(@ModelAttribute List<Job> jobs, Model model) {
        try {
            Response response = jobModel.insertAll(jobs);
            model.addAttribute("response", response);
            return "job_response"; // View to display the result
        } catch (Exception e) {
            model.addAttribute("error", "Error occurred while adding jobs: " + e.getMessage());
            return "error"; // Error view
        }
    }

    // Endpoint to update a Job
    @PutMapping("/update-job")
    public String updateJob(@RequestParam Long id, @ModelAttribute Job job, Model model) {
        try {
            Response response = jobModel.update(id, job);
            model.addAttribute("response", response);
            return "job_response"; // View to display the result
        } catch (Exception e) {
            model.addAttribute("error", "Error occurred while updating the job: " + e.getMessage());
            return "error"; // Error view
        }
    }

    // Endpoint to delete a Job
    @DeleteMapping("/delete-job")
    public String deleteJob(@RequestParam Long id, Model model) {
        try {
            jobModel.delete(id);
            model.addAttribute("message", "Job deleted successfully");
            return "job_response"; // View to display the result
        } catch (Exception e) {
            model.addAttribute("error", "Error occurred while deleting the job: " + e.getMessage());
            return "error"; // Error view
        }
    }

    // Endpoint to get Job by ID
    @GetMapping("/getById-job")
    public String getJobById(@RequestParam Long id, Model model) {
        try {
            Optional<Job> job = jobModel.getById(id);
            if (job.isPresent()) {
                model.addAttribute("job", job.get());
                return "job_details"; // View to display job details
            } else {
                model.addAttribute("error", "Job not found with ID: " + id);
                return "error"; // Error view
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error occurred while retrieving the job: " + e.getMessage());
            return "error"; // Error view
        }
    }

    // Endpoint to get all Jobs
    @GetMapping("/getAll-job")
    public String getAllJobs(Model model) {
        try {
            List<Job> jobs = jobModel.getAll();
            model.addAttribute("jobs", jobs);
            return "jobs_list"; // View to display all jobs
        } catch (Exception e) {
            model.addAttribute("error", "Error occurred while fetching jobs: " + e.getMessage());
            return "error"; // Error view
        }
    }

    // Endpoint to get Jobs by Job Name
    @GetMapping("/findByJobName-job")
    public String findByJobName(@RequestParam String jobName, Model model) {
        try {
            List<Job> jobs = jobModel.findByJobName(jobName);
            model.addAttribute("jobs", jobs);
            return "jobs_list"; // View to display jobs by job name
        } catch (Exception e) {
            model.addAttribute("error", "Error occurred while searching jobs by name: " + e.getMessage());
            return "error"; // Error view
        }
    }

    // Endpoint to get Jobs by Company Name
    @GetMapping("/findByCompanyName-job")
    public String findByCompanyName(@RequestParam String companyName, Model model) {
        try {
            List<Job> jobs = jobModel.findByCompanyName(companyName);
            model.addAttribute("jobs", jobs);
            return "jobs_list"; // View to display jobs by company name
        } catch (Exception e) {
            model.addAttribute("error", "Error occurred while searching jobs by company name: " + e.getMessage());
            return "error"; // Error view
        }
    }

    // Endpoint to get Jobs by Job Description
    @GetMapping("/findByJobDesc-job")
    public String findByJobDesc(@RequestParam String jobDesc, Model model) {
        try {
            List<Job> jobs = jobModel.findByJobDesc(jobDesc);
            model.addAttribute("jobs", jobs);
            return "jobs_list"; // View to display jobs by job description
        } catch (Exception e) {
            model.addAttribute("error", "Error occurred while searching jobs by description: " + e.getMessage());
            return "error"; // Error view
        }
    }
}
