package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Job;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models.JobModel;

import java.util.List;

@Controller
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobModel jobModel;

    @GetMapping
    public ModelAndView listAllJobs() {
        List<Job> jobs = jobModel.getAll();
        ModelAndView mav = new ModelAndView("job-list");
        mav.addObject("jobs", jobs);
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView getJobById(@PathVariable("id") Long id) {
        Job job = jobModel.getById(id);
        ModelAndView mav = new ModelAndView("job-detail");
        mav.addObject("job", job);
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView showCreateJobForm() {
        ModelAndView mav = new ModelAndView("job-create");
        mav.addObject("job", new Job());
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createJob(@ModelAttribute Job job) {
        jobModel.insert(job);
        return new ModelAndView("redirect:/jobs");
    }

    @GetMapping("/{id}/edit")
    public ModelAndView showEditJobForm(@PathVariable("id") Long id) {
        Job job = jobModel.getById(id);
        ModelAndView mav = new ModelAndView("job-edit");
        mav.addObject("job", job);
        return mav;
    }

    @PostMapping("/{id}/edit")
    public ModelAndView editJob(@PathVariable("id") Long id, @ModelAttribute Job job) {
        jobModel.update(id, job);
        return new ModelAndView("redirect:/jobs/" + id);
    }

    @PostMapping("/{id}/delete")
    public ModelAndView deleteJob(@PathVariable("id") Long id) {
        jobModel.delete(id);
        return new ModelAndView("redirect:/jobs");
    }
}
