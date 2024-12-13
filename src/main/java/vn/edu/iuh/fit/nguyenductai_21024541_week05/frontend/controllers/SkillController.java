package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Skill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models.SkillModel;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillType;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/skills")
public class SkillController {

    private final SkillModel skillModel;

    @Autowired
    public SkillController(SkillModel skillModel) {
        this.skillModel = skillModel;
    }

    // Endpoint to add a single Skill
    @PostMapping("/insert")
    public String insertSkill(@ModelAttribute Skill skill, Model model) {
        Response response = skillModel.insert(skill);
        model.addAttribute("response", response);
        return "skill_response"; // View to display the result
    }

    // Endpoint to add multiple Skills
    @PostMapping("/insertAll")
    public String insertAllSkills(@ModelAttribute List<Skill> skills, Model model) {
        Response response = skillModel.insertAll(skills);
        model.addAttribute("response", response);
        return "skill_response"; // View to display the result
    }

    // Endpoint to update a Skill
    @PutMapping("/update")
    public String updateSkill(@RequestParam Long id, @ModelAttribute Skill skill, Model model) {
        Response response = skillModel.update(id, skill);
        model.addAttribute("response", response);
        return "skill_response"; // View to display the result
    }

    // Endpoint to delete a Skill
    @DeleteMapping("/delete")
    public String deleteSkill(@RequestParam Long id, Model model) {
        skillModel.delete(id);
        model.addAttribute("message", "Skill deleted successfully");
        return "skill_response"; // View to display the result
    }

    // Endpoint to get a Skill by ID
    @GetMapping("/getById")
    public String getSkillById(@RequestParam Long id, Model model) {
        Optional<Skill> skill = skillModel.getById(id);
        model.addAttribute("skill", skill.orElse(null));
        return "skill_details"; // View to display skill details
    }

    // Endpoint to get all Skills
    @GetMapping("/getAll")
    public String getAllSkills(Model model) {
        List<Skill> skills = skillModel.getAll();
        model.addAttribute("skills", skills);
        return "skills_list"; // View to display all skills
    }

    // Endpoint to search Skills by name (case insensitive)
    @GetMapping("/searchByName")
    public String getSkillsByName(@RequestParam String skillName, Model model) {
        List<Skill> skills = skillModel.getSkillsByName(skillName);
        model.addAttribute("skills", skills);
        return "skills_list"; // View to display skills by name
    }

    // Endpoint to search Skills by type
    @GetMapping("/searchByType")
    public String getSkillsByType(@RequestParam SkillType skillType, Model model) {
        List<Skill> skills = skillModel.getSkillsByType(skillType);
        model.addAttribute("skills", skills);
        return "skills_list"; // View to display skills by type
    }
}
