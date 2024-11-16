package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.JobSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Job;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.JobSkill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Skill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models.JobModel;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models.JobSkillModel;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models.SkillModel;

import java.util.List;

@Controller
@RequestMapping("/job-skills")
public class JobSkillController {

    @Autowired
    private JobSkillModel jobSkillModel;

    @Autowired
    private JobModel jobModel;

    @Autowired
    private SkillModel skillModel;

    @GetMapping
    public ModelAndView listAllJobSkills() {
        List<JobSkill> jobSkills = jobSkillModel.getAll();
        ModelAndView mav = new ModelAndView("job-skill-list");
        mav.addObject("jobSkills", jobSkills);
        return mav;
    }

    @GetMapping("/{jobId}/{skillId}")
    public ModelAndView getJobSkillById(
            @PathVariable("jobId") Long jobId,
            @PathVariable("skillId") Long skillId
    ) {
        // Fetch Job and Skill objects from their respective models
        Job job = jobModel.getById(jobId);
        Skill skill = skillModel.getById(skillId);

        // Create JobSkillId using Job and Skill objects
        JobSkillId jobSkillId = new JobSkillId(job, skill);

        // Fetch JobSkill
        JobSkill jobSkill = jobSkillModel.getById(jobSkillId);

        ModelAndView mav = new ModelAndView("job-skill-detail");
        mav.addObject("jobSkill", jobSkill);
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView showCreateJobSkillForm() {
        ModelAndView mav = new ModelAndView("job-skill-create");
        mav.addObject("jobSkill", new JobSkill());
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createJobSkill(@ModelAttribute JobSkill jobSkill) {
        jobSkillModel.insert(jobSkill);
        return new ModelAndView("redirect:/job-skills");
    }

    @GetMapping("/{jobId}/{skillId}/edit")
    public ModelAndView showEditJobSkillForm(
            @PathVariable("jobId") Long jobId,
            @PathVariable("skillId") Long skillId
    ) {
        // Fetch Job and Skill objects from their respective models
        Job job = jobModel.getById(jobId);
        Skill skill = skillModel.getById(skillId);

        // Create JobSkillId using Job and Skill objects
        JobSkillId jobSkillId = new JobSkillId(job, skill);

        // Fetch JobSkill
        JobSkill jobSkill = jobSkillModel.getById(jobSkillId);

        ModelAndView mav = new ModelAndView("job-skill-edit");
        mav.addObject("jobSkill", jobSkill);
        return mav;
    }

    @PostMapping("/{jobId}/{skillId}/edit")
    public ModelAndView editJobSkill(
            @PathVariable("jobId") Long jobId,
            @PathVariable("skillId") Long skillId,
            @ModelAttribute JobSkill jobSkill
    ) {
        // Fetch Job and Skill objects from their respective models
        Job job = jobModel.getById(jobId);
        Skill skill = skillModel.getById(skillId);

        // Create JobSkillId using Job and Skill objects
        JobSkillId jobSkillId = new JobSkillId(job, skill);

        // Update JobSkill
        jobSkillModel.update(jobSkillId, jobSkill);

        return new ModelAndView("redirect:/job-skills/" + jobId + "/" + skillId);
    }

    @PostMapping("/{jobId}/{skillId}/delete")
    public ModelAndView deleteJobSkill(
            @PathVariable("jobId") Long jobId,
            @PathVariable("skillId") Long skillId
    ) {
        // Fetch Job and Skill objects from their respective models
        Job job = jobModel.getById(jobId);
        Skill skill = skillModel.getById(skillId);

        // Create JobSkillId using Job and Skill objects
        JobSkillId jobSkillId = new JobSkillId(job, skill);

        // Delete JobSkill
        jobSkillModel.delete(jobSkillId);

        return new ModelAndView("redirect:/job-skills");
    }

    @GetMapping("/jobs/{skillId}")
    public ModelAndView getAllJobsBySkill(@PathVariable("skillId") Long skillId) {
        List<?> jobs = jobSkillModel.getAllJobsBySkill(skillId);
        ModelAndView mav = new ModelAndView("jobs-by-skill");
        mav.addObject("jobs", jobs);
        return mav;
    }
}
