package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Experience;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models.ExperienceModel;

import java.util.List;

@Controller
@RequestMapping("/experiences")
public class ExperienceController {

    @Autowired
    private ExperienceModel experienceModel;

    @GetMapping
    public ModelAndView listAllExperiences() {
        List<Experience> experiences = experienceModel.getAll();
        ModelAndView mav = new ModelAndView("experience-list");
        mav.addObject("experiences", experiences);
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView getExperienceById(@PathVariable("id") Long id) {
        Experience experience = experienceModel.getById(id);
        ModelAndView mav = new ModelAndView("experience-detail");
        mav.addObject("experience", experience);
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView showCreateExperienceForm() {
        ModelAndView mav = new ModelAndView("experience-create");
        mav.addObject("experience", new Experience());
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createExperience(@ModelAttribute Experience experience) {
        experienceModel.insert(experience);
        return new ModelAndView("redirect:/experiences");
    }

    @GetMapping("/{id}/edit")
    public ModelAndView showEditExperienceForm(@PathVariable("id") Long id) {
        Experience experience = experienceModel.getById(id);
        ModelAndView mav = new ModelAndView("experience-edit");
        mav.addObject("experience", experience);
        return mav;
    }

    @PostMapping("/{id}/edit")
    public ModelAndView editExperience(@PathVariable("id") Long id, @ModelAttribute Experience experience) {
        experienceModel.update(id, experience);
        return new ModelAndView("redirect:/experiences/" + id);
    }

    @PostMapping("/{id}/delete")
    public ModelAndView deleteExperience(@PathVariable("id") Long id) {
        experienceModel.delete(id);
        return new ModelAndView("redirect:/experiences");
    }
}
