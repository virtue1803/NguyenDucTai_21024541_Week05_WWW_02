package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.JobSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.JobSkill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models.JobSkillModel;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillLevel;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/job-skills")
public class JobSkillController {

    private final JobSkillModel jobSkillModel;

    @Autowired
    public JobSkillController(JobSkillModel jobSkillModel) {
        this.jobSkillModel = jobSkillModel;
    }

    // Endpoint to add a single JobSkill
    @PostMapping("/insert-jobskill")
    public String insertJobSkill(@ModelAttribute JobSkill jobSkill, Model model) {
        Response response = jobSkillModel.insert(jobSkill);
        model.addAttribute("response", response);
        return "job_skill_response"; // View to display the result
    }

    // Endpoint to add multiple JobSkills
    @PostMapping("/insertAll-jobskill")
    public String insertAllJobSkills(@ModelAttribute List<JobSkill> jobSkills, Model model) {
        Response response = jobSkillModel.insertAll(jobSkills);
        model.addAttribute("response", response);
        return "job_skill_response"; // View to display the result
    }

    // Endpoint to update a JobSkill
    @PutMapping("/update-jobskill")
    public String updateJobSkill(@ModelAttribute JobSkillId id, @ModelAttribute JobSkill jobSkill, Model model) {
        Response response = jobSkillModel.update(id, jobSkill);
        model.addAttribute("response", response);
        return "job_skill_response"; // View to display the result
    }

    // Endpoint to delete a JobSkill
    @DeleteMapping("/delete-jobskill")
    public String deleteJobSkill(@ModelAttribute JobSkillId id, Model model) {
        jobSkillModel.delete(id);
        model.addAttribute("message", "Job Skill deleted successfully");
        return "job_skill_response"; // View to display the result
    }

    // Endpoint to get a JobSkill by ID
    @GetMapping("/getById-jobskill")
    public String getJobSkillById(@ModelAttribute JobSkillId id, Model model) {
        Optional<JobSkill> jobSkill = jobSkillModel.getById(id);
        model.addAttribute("jobSkill", jobSkill.orElse(null));
        return "job_skill_details"; // View to display job skill details
    }

    // Endpoint to get all JobSkills
    @GetMapping("/getAll-jobskill")
    public String getAllJobSkills(Model model) {
        List<JobSkill> jobSkills = jobSkillModel.getAll();
        model.addAttribute("jobSkills", jobSkills);
        return "job_skills_list"; // View to display all job skills
    }

    // Endpoint to get JobSkills by Skill ID
    @GetMapping("/getBySkillId-jobskill")
    public String getJobSkillsBySkillId(@RequestParam Long skillId, Model model) {
        List<JobSkill> jobSkills = jobSkillModel.getBySkillId(skillId);
        model.addAttribute("jobSkills", jobSkills);
        return "job_skills_list"; // View to display job skills by skill ID
    }

    // Endpoint to get JobSkills by Job ID
    @GetMapping("/getByJobId-jobskill")
    public String getJobSkillsByJobId(@RequestParam Long jobId, Model model) {
        List<JobSkill> jobSkills = jobSkillModel.getByJobId(jobId);
        model.addAttribute("jobSkills", jobSkills);
        return "job_skills_list"; // View to display job skills by job ID
    }

    // Endpoint to get JobSkills by Skill Level
    @GetMapping("/getBySkillLevel-jobskill")
    public String getJobSkillsBySkillLevel(@RequestParam SkillLevel skillLevel, Model model) {
        List<JobSkill> jobSkills = jobSkillModel.getBySkillLevel(skillLevel);
        model.addAttribute("jobSkills", jobSkills);
        return "job_skills_list"; // View to display job skills by skill level
    }
}
