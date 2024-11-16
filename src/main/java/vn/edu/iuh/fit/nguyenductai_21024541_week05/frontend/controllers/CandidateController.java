package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.dto.CandidateAccountDTO;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Candidate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models.CandidateModel;

import java.util.List;

@Controller
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateModel candidateModel;

    @GetMapping
    public ModelAndView listAllCandidates() {
        List<Candidate> candidates = candidateModel.getAll();
        ModelAndView mav = new ModelAndView("candidate-list");
        mav.addObject("candidates", candidates);
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView getCandidateById(@PathVariable("id") Long id) {
        Candidate candidate = candidateModel.getById(id);
        ModelAndView mav = new ModelAndView("candidate-detail");
        mav.addObject("candidate", candidate);
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView showCreateCandidateForm() {
        ModelAndView mav = new ModelAndView("candidate-create");
        mav.addObject("candidate", new Candidate());
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createCandidate(@ModelAttribute Candidate candidate) {
        candidateModel.insert(candidate);
        return new ModelAndView("redirect:/candidates");
    }

    @GetMapping("/{id}/edit")
    public ModelAndView showEditCandidateForm(@PathVariable("id") Long id) {
        Candidate candidate = candidateModel.getById(id);
        ModelAndView mav = new ModelAndView("candidate-edit");
        mav.addObject("candidate", candidate);
        return mav;
    }

    @PostMapping("/{id}/edit")
    public ModelAndView editCandidate(@PathVariable("id") Long id, @ModelAttribute Candidate candidate) {
        candidateModel.update(id, candidate);
        return new ModelAndView("redirect:/candidates/" + id);
    }

    @PostMapping("/{id}/delete")
    public ModelAndView deleteCandidate(@PathVariable("id") Long id) {
        candidateModel.delete(id);
        return new ModelAndView("redirect:/candidates");
    }

    @PostMapping("/login")
    public ModelAndView loginCandidate(@ModelAttribute CandidateAccountDTO accountDTO) {
        Candidate candidate = candidateModel.checkLoginAccount(accountDTO);
        ModelAndView mav = new ModelAndView("candidate-login");
        if (candidate != null) {
            mav.addObject("candidate", candidate);
            mav.addObject("message", "Login successful");
        } else {
            mav.addObject("error", "Invalid email or password");
        }
        return mav;
    }

    @GetMapping("/page/{page}")
    public ModelAndView listCandidatesByPage(@PathVariable("page") int page) {
        List<Candidate> candidates = candidateModel.getAllPaginated(page);
        ModelAndView mav = new ModelAndView("candidate-paginated-list");
        mav.addObject("candidates", candidates);
        mav.addObject("currentPage", page);
        return mav;
    }
}