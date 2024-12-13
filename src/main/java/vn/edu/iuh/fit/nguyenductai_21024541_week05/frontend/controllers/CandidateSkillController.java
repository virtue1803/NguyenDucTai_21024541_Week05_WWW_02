package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillLevel;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.CandidateSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.CandidateSkill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models.CandidateSkillModel;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/candidate-skills")
public class CandidateSkillController {

    private final CandidateSkillModel candidateSkillModel;

    @Autowired
    public CandidateSkillController(CandidateSkillModel candidateSkillModel) {
        this.candidateSkillModel = candidateSkillModel;
    }

    // Endpoint to add a single CandidateSkill
    @PostMapping("/insert-candidateskill")
    public String insertCandidateSkill(@ModelAttribute CandidateSkill candidateSkill, Model model) {
        Response response = candidateSkillModel.insert(candidateSkill);
        model.addAttribute("response", response);
        return "candidate_skill_response"; // View to display the result
    }

    // Endpoint to add multiple CandidateSkills
    @PostMapping("/insertAll-candidateskill")
    public String insertAllCandidateSkills(@ModelAttribute List<CandidateSkill> candidateSkills, Model model) {
        Response response = candidateSkillModel.insertAll(candidateSkills);
        model.addAttribute("response", response);
        return "candidate_skill_response"; // View to display the result
    }

    // Endpoint to update a CandidateSkill
    @PutMapping("/update-candidateskill")
    public String updateCandidateSkill(@RequestParam CandidateSkillId id, @ModelAttribute CandidateSkill candidateSkill, Model model) {
        Response response = candidateSkillModel.update(id, candidateSkill);
        model.addAttribute("response", response);
        return "candidate_skill_response"; // View to display the result
    }

    // Endpoint to delete a CandidateSkill
    @DeleteMapping("/delete-candidateskill")
    public String deleteCandidateSkill(@RequestParam CandidateSkillId id, Model model) {
        candidateSkillModel.delete(id);
        model.addAttribute("message", "Candidate skill deleted successfully");
        return "candidate_skill_response"; // View to display the result
    }

    // Endpoint to get CandidateSkill by ID
    @GetMapping("/getById-candidateskill")
    public String getCandidateSkillById(@RequestParam CandidateSkillId id, Model model) {
        Optional<CandidateSkill> candidateSkill = candidateSkillModel.getById(id);
        model.addAttribute("candidateSkill", candidateSkill.orElse(null));
        return "candidate_skill_details"; // View to display candidate skill details
    }

    // Endpoint to get all CandidateSkills
    @GetMapping("/getAll-candidateskill")
    public String getAllCandidateSkills(Model model) {
        List<CandidateSkill> candidateSkills = candidateSkillModel.getAll();
        model.addAttribute("candidateSkills", candidateSkills);
        return "candidate_skills_list"; // View to display all candidate skills
    }

    // Endpoint to get CandidateSkills by CandidateId
    @GetMapping("/getSkillsByCandidateId-candidateskill")
    public String getSkillsByCandidateId(@RequestParam Long candidateId, Model model) {
        List<CandidateSkill> candidateSkills = candidateSkillModel.getSkillsByCandidateId(candidateId);
        model.addAttribute("candidateSkills", candidateSkills);
        return "candidate_skills_list"; // View to display candidate skills by candidate ID
    }

    // Endpoint to get CandidateSkills by SkillLevel
    @GetMapping("/getSkillsByLevel-candidateskill")
    public String getSkillsByLevel(@RequestParam SkillLevel skillLevel, Model model) {
        List<CandidateSkill> candidateSkills = candidateSkillModel.getSkillsByLevel(skillLevel);
        model.addAttribute("candidateSkills", candidateSkills);
        return "candidate_skills_list"; // View to display candidate skills by skill level
    }
}
