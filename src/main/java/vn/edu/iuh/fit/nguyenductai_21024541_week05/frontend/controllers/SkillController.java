package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Skill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models.SkillModel;

import java.util.List;

@Controller
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    private SkillModel skillModel;

    @GetMapping
    public ModelAndView listAllSkills() {
        List<Skill> skills = skillModel.getAll();
        ModelAndView mav = new ModelAndView("skill-list");
        mav.addObject("skills", skills);
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView getSkillById(@PathVariable("id") Long id) {
        Skill skill = skillModel.getById(id);
        ModelAndView mav = new ModelAndView("skill-detail");
        mav.addObject("skill", skill);
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView showCreateSkillForm() {
        ModelAndView mav = new ModelAndView("skill-create");
        mav.addObject("skill", new Skill());
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createSkill(@ModelAttribute Skill skill) {
        skillModel.insert(skill);
        return new ModelAndView("redirect:/skills");
    }

    @GetMapping("/{id}/edit")
    public ModelAndView showEditSkillForm(@PathVariable("id") Long id) {
        Skill skill = skillModel.getById(id);
        ModelAndView mav = new ModelAndView("skill-edit");
        mav.addObject("skill", skill);
        return mav;
    }

    @PostMapping("/{id}/edit")
    public ModelAndView editSkill(@PathVariable("id") Long id, @ModelAttribute Skill skill) {
        skillModel.update(id, skill);
        return new ModelAndView("redirect:/skills/" + id);
    }

    @PostMapping("/{id}/delete")
    public ModelAndView deleteSkill(@PathVariable("id") Long id) {
        skillModel.delete(id);
        return new ModelAndView("redirect:/skills");
    }
}
