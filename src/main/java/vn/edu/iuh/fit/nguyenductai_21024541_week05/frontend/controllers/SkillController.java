package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Skill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.SkillService;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillType;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    // Endpoint to add a single skill
    @PostMapping("/add")
    public String addSkill(@ModelAttribute Skill skill, Model model) {
        Skill savedSkill = skillService.add(skill);
        model.addAttribute("skill", savedSkill);
        model.addAttribute("message", "Skill added successfully");
        return "skill_details"; // View to show skill details
    }

    // Endpoint to add multiple skills
    @PostMapping("/addAll")
    public String addSkills(@ModelAttribute List<Skill> skills, Model model) {
        List<Skill> savedSkills = skillService.addMany(skills);
        model.addAttribute("skills", savedSkills);
        model.addAttribute("message", "Skills added successfully");
        return "skills_list"; // View to show all skills
    }

    // Endpoint to update a skill
    @PutMapping("/update")
    public String updateSkill(@RequestParam Long id, @ModelAttribute Skill skill, Model model) {
        try {
            skill.setId(id); // Set the skill ID for update
            Skill updatedSkill = skillService.update(skill);
            model.addAttribute("skill", updatedSkill);
            model.addAttribute("message", "Skill updated successfully");
        } catch (EntityIdNotFoundException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "skill_details"; // View to show updated skill details
    }

    // Endpoint to delete a skill
    @DeleteMapping("/delete")
    public String deleteSkill(@RequestParam Long id, Model model) {
        try {
            skillService.delete(id);
            model.addAttribute("message", "Skill deleted successfully");
        } catch (EntityIdNotFoundException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "skill_response"; // View to show deletion result
    }

    // Endpoint to get a skill by ID
    @GetMapping("/getById")
    public String getSkillById(@RequestParam Long id, Model model) {
        try {
            Optional<Skill> skill = skillService.getById(id);
            model.addAttribute("skill", skill.orElse(null));
            return "skill_details"; // View to show skill details
        } catch (EntityIdNotFoundException e) {
            model.addAttribute("message", e.getMessage());
            return "skill_response"; // View to show error message
        }
    }

    // Endpoint to get all skills
    @GetMapping("/getAll")
    public String getAllSkills(Model model) {
        Iterator<Skill> skills = skillService.getAll();
        model.addAttribute("skills", skills);
        return "skills_list"; // View to show all skills
    }

    // Endpoint to search skills by name
    @GetMapping("/searchByName")
    public String getSkillsByName(@RequestParam String skillName, Model model) {
        List<Skill> skills = skillService.findBySkillName(skillName);
        model.addAttribute("skills", skills);
        return "skills_list"; // View to show skills matching the name
    }

    // Endpoint to search skills by type
    @GetMapping("/searchByType")
    public String getSkillsByType(@RequestParam SkillType skillType, Model model) {
        List<Skill> skills = skillService.findBySkillType(skillType);
        model.addAttribute("skills", skills);
        return "skills_list"; // View to show skills matching the type
    }
}
