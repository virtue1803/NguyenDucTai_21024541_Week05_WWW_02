package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Experience;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models.ExperienceModel;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/experiences")
public class ExperienceController {

    private final ExperienceModel experienceModel;

    @Autowired
    public ExperienceController(ExperienceModel experienceModel) {
        this.experienceModel = experienceModel;
    }

    // Endpoint to add a single Experience
    @PostMapping("/insert-experience")
    public String insertExperience(@ModelAttribute Experience experience, Model model) {
        Response response = experienceModel.insert(experience);
        model.addAttribute("response", response);
        return "experience_response"; // View to display the result
    }

    // Endpoint to add multiple Experiences
    @PostMapping("/insertAll-experience")
    public String insertAllExperiences(@ModelAttribute List<Experience> experiences, Model model) {
        Response response = experienceModel.insertAll(experiences);
        model.addAttribute("response", response);
        return "experience_response"; // View to display the result
    }

    // Endpoint to update an Experience
    @PutMapping("/update-experience")
    public String updateExperience(@RequestParam Long id, @ModelAttribute Experience experience, Model model) {
        Response response = experienceModel.update(id, experience);
        model.addAttribute("response", response);
        return "experience_response"; // View to display the result
    }

    // Endpoint to delete an Experience
    @DeleteMapping("/delete-experience")
    public String deleteExperience(@RequestParam Long id, Model model) {
        experienceModel.delete(id);
        model.addAttribute("message", "Experience deleted successfully");
        return "experience_response"; // View to display the result
    }

    // Endpoint to get Experience by ID
    @GetMapping("/getById-experience")
    public String getExperienceById(@RequestParam Long id, Model model) {
        Optional<Experience> experience = experienceModel.getById(id);
        model.addAttribute("experience", experience.orElse(null));
        return "experience_details"; // View to display experience details
    }

    // Endpoint to get all Experiences
    @GetMapping("/getAll-experience")
    public String getAllExperiences(Model model) {
        List<Experience> experiences = experienceModel.getAll();
        model.addAttribute("experiences", experiences);
        return "experiences_list"; // View to display all experiences
    }

    // Endpoint to get Experiences by Candidate ID
    @GetMapping("/getByCandidateId-experience")
    public String getByCandidateId(@RequestParam Long candidateId, Model model) {
        List<Experience> experiences = experienceModel.getByCandidateId(candidateId);
        model.addAttribute("experiences", experiences);
        return "experiences_list"; // View to display experiences by candidate ID
    }

    // Endpoint to get Experiences by Company Name
    @GetMapping("/getByCompanyName-experience")
    public String getByCompanyName(@RequestParam String companyName, Model model) {
        List<Experience> experiences = experienceModel.getByCompanyName(companyName);
        model.addAttribute("experiences", experiences);
        return "experiences_list"; // View to display experiences by company name
    }

    // Endpoint to get Experiences by Role
    @GetMapping("/getByRole-experience")
    public String getByRole(@RequestParam String role, Model model) {
        List<Experience> experiences = experienceModel.getByRole(role);
        model.addAttribute("experiences", experiences);
        return "experiences_list"; // View to display experiences by role
    }

    // Endpoint to get Experiences by Company and Role
    @GetMapping("/getByCompanyAndRole-experience")
    public String getByCompanyAndRole(@RequestParam String companyName, @RequestParam String role, Model model) {
        List<Experience> experiences = experienceModel.getByCompanyAndRole(companyName, role);
        model.addAttribute("experiences", experiences);
        return "experiences_list"; // View to display experiences by company and role
    }
}
