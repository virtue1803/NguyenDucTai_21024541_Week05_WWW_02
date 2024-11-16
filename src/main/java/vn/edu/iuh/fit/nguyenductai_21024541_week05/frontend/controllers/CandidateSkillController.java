package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.CandidateSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Candidate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.CandidateSkill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Skill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models.CandidateModel;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models.CandidateSkillModel;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models.SkillModel;

import java.util.List;

@Controller
@RequestMapping("/candidate-skills")
public class CandidateSkillController {

    @Autowired
    private CandidateSkillModel candidateSkillModel;

    @Autowired
    private CandidateModel candidateModel;

    @Autowired
    private SkillModel skillModel;

    @GetMapping
    public ModelAndView listAllCandidateSkills() {
        List<CandidateSkill> candidateSkills = candidateSkillModel.getAll();
        ModelAndView mav = new ModelAndView("candidate-skill-list");
        mav.addObject("candidateSkills", candidateSkills);
        return mav;
    }

    @GetMapping("/{candidateId}/{skillId}")
    public ModelAndView getCandidateSkillById(
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("skillId") Long skillId
    ) {
        // Fetch Candidate and Skill from their respective models
        Candidate candidate = candidateModel.getById(candidateId);
        Skill skill = skillModel.getById(skillId);

        // Create CandidateSkillId using Candidate and Skill objects
        CandidateSkillId candidateSkillId = new CandidateSkillId(candidate, skill);

        // Fetch CandidateSkill
        CandidateSkill candidateSkill = candidateSkillModel.getById(candidateSkillId);

        ModelAndView mav = new ModelAndView("candidate-skill-detail");
        mav.addObject("candidateSkill", candidateSkill);
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView showCreateCandidateSkillForm() {
        ModelAndView mav = new ModelAndView("candidate-skill-create");
        mav.addObject("candidateSkill", new CandidateSkill());
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createCandidateSkill(@ModelAttribute CandidateSkill candidateSkill) {
        candidateSkillModel.insert(candidateSkill);
        return new ModelAndView("redirect:/candidate-skills");
    }

    @GetMapping("/{candidateId}/{skillId}/edit")
    public ModelAndView showEditCandidateSkillForm(
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("skillId") Long skillId
    ) {
        // Fetch Candidate and Skill from their respective models
        Candidate candidate = candidateModel.getById(candidateId);
        Skill skill = skillModel.getById(skillId);

        // Create CandidateSkillId using Candidate and Skill objects
        CandidateSkillId candidateSkillId = new CandidateSkillId(candidate, skill);

        // Fetch CandidateSkill
        CandidateSkill candidateSkill = candidateSkillModel.getById(candidateSkillId);

        ModelAndView mav = new ModelAndView("candidate-skill-edit");
        mav.addObject("candidateSkill", candidateSkill);
        return mav;
    }

    @PostMapping("/{candidateId}/{skillId}/edit")
    public ModelAndView editCandidateSkill(
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("skillId") Long skillId,
            @ModelAttribute CandidateSkill candidateSkill
    ) {
        // Fetch Candidate and Skill from their respective models
        Candidate candidate = candidateModel.getById(candidateId);
        Skill skill = skillModel.getById(skillId);

        // Create CandidateSkillId using Candidate and Skill objects
        CandidateSkillId candidateSkillId = new CandidateSkillId(candidate, skill);

        // Update CandidateSkill
        candidateSkillModel.update(candidateSkillId, candidateSkill);

        return new ModelAndView("redirect:/candidate-skills/" + candidateId + "/" + skillId);
    }

    @PostMapping("/{candidateId}/{skillId}/delete")
    public ModelAndView deleteCandidateSkill(
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("skillId") Long skillId
    ) {
        // Fetch Candidate and Skill from their respective models
        Candidate candidate = candidateModel.getById(candidateId);
        Skill skill = skillModel.getById(skillId);

        // Create CandidateSkillId using Candidate and Skill objects
        CandidateSkillId candidateSkillId = new CandidateSkillId(candidate, skill);

        // Delete CandidateSkill
        candidateSkillModel.delete(candidateSkillId);

        return new ModelAndView("redirect:/candidate-skills");
    }
}
