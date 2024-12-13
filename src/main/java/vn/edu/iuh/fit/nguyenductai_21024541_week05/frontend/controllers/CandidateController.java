package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.CandidateRole;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Candidate;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models.CandidateModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/candidates")
public class CandidateController {

    private final CandidateModel candidateModel;

    @Autowired
    public CandidateController(CandidateModel candidateModel) {
        this.candidateModel = candidateModel;
    }

    // Phương thức thêm một Candidate
    @GetMapping("/insert-candidate")
    public String insertCandidate(Candidate candidate, Model model) {
        Response response = candidateModel.insert(candidate);
        model.addAttribute("response", response);
        return "candidate_response"; // View để hiển thị thông báo kết quả
    }

    // Phương thức thêm nhiều Candidate
    @GetMapping("/insertAll-candidate")
    public String insertAllCandidates(List<Candidate> candidates, Model model) {
        Response response = candidateModel.insertAll(candidates);
        model.addAttribute("response", response);
        return "candidate_response"; // View để hiển thị thông báo kết quả
    }

    // Phương thức cập nhật một Candidate
    @GetMapping("/update-candidate")
    public String updateCandidate(@RequestParam Long id, Candidate candidate, Model model) {
        Response response = candidateModel.update(id, candidate);
        model.addAttribute("response", response);
        return "candidate_response"; // View để hiển thị thông báo kết quả
    }

    // Phương thức xóa một Candidate
    @GetMapping("/delete-candidate")
    public String deleteCandidate(@RequestParam Long id, Model model) {
        candidateModel.delete(id);
        model.addAttribute("message", "Candidate deleted successfully");
        return "candidate_response"; // View để hiển thị thông báo kết quả
    }

    // Phương thức lấy Candidate theo ID
    @GetMapping("/getById-candidate")
    public String getCandidateById(@RequestParam Long id, Model model) {
        Optional<Candidate> candidate = candidateModel.getById(id);
        model.addAttribute("candidate", candidate.orElse(null));
        return "candidate_details"; // View để hiển thị thông tin Candidate
    }

    // Phương thức lấy tất cả Candidate
    @GetMapping("/getAll-candidate")
    public String getAllCandidates(Model model) {
        List<Candidate> candidates = candidateModel.getAll();
        model.addAttribute("candidates", candidates);
        return "candidates_list"; // View để hiển thị danh sách Candidate
    }

    // Tìm kiếm Candidate theo email và password
    @GetMapping("/search/login-candidate")
    public String findByEmailAndPassword(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {
        Optional<Candidate> candidate = candidateModel.findByEmailAndPassword(email, password);
        if (candidate.isPresent()) {
            model.addAttribute("candidate", candidate.get());
            return "candidate_details"; // Hiển thị thông tin Candidate đã đăng nhập
        }
        model.addAttribute("message", "Invalid email or password");
        return "login_form"; // Trả về form đăng nhập nếu không hợp lệ
    }

    // Tìm kiếm Candidate theo vai trò
    @GetMapping("/findByRole-candidate")
    public String findByRole(@RequestParam CandidateRole role, Model model) {
        List<Candidate> candidates = candidateModel.findByRole(role);
        model.addAttribute("candidates", candidates);
        return "candidates_list"; // Hiển thị danh sách Candidate theo vai trò
    }

    // Tìm kiếm Candidate theo tên
    @GetMapping("/findByName-candidate")
    public String findByNameContainingIgnoreCase(@RequestParam String name, Model model) {
        List<Candidate> candidates = candidateModel.findByNameContainingIgnoreCase(name);
        model.addAttribute("candidates", candidates);
        return "candidates_list"; // Hiển thị danh sách Candidate theo tên
    }

    // Tìm kiếm Candidate theo ngày sinh
    @GetMapping("/findByDobBetween-candidate")
    public String findByDobBetween(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            Model model) {
        List<Candidate> candidates = candidateModel.findByDobBetween(startDate, endDate);
        model.addAttribute("candidates", candidates);
        return "candidates_list"; // Hiển thị danh sách Candidate theo ngày sinh
    }
}
