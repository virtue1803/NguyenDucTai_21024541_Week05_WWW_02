package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillLevel;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.CandidateSkill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.CandidateSkillService;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.CandidateSkillId;

import java.util.List;

@Controller
@RequestMapping("/candidate-skills")
public class CandidateSkillController {

    @Autowired
    private CandidateSkillService candidateSkillService;

    // Lấy tất cả kỹ năng và hiển thị
    @GetMapping
    public String getAllCandidateSkills(Model model) {
        List<CandidateSkill> candidateSkills = (List<CandidateSkill>) candidateSkillService.getAll();
        model.addAttribute("candidateSkills", candidateSkills);
        return "candidateSkills"; // Tên view hiển thị danh sách kỹ năng, ví dụ "candidateSkills.jsp"
    }

    // Lấy kỹ năng theo ID và hiển thị
    @GetMapping("/{candidateSkillId}")
    public String getCandidateSkillById(@PathVariable CandidateSkillId candidateSkillId, Model model) {
        try {
            CandidateSkill candidateSkill = candidateSkillService.getById(candidateSkillId)
                    .orElseThrow(() -> new EntityIdNotFoundException("CandidateSkill not found"));
            model.addAttribute("candidateSkill", candidateSkill);
            return "candidateSkillDetail"; // View chi tiết kỹ năng, ví dụ "candidateSkillDetail.jsp"
        } catch (EntityIdNotFoundException e) {
            return "error"; // Trả về view lỗi nếu không tìm thấy
        }
    }

    // Thêm kỹ năng mới
    @PostMapping
    public String addCandidateSkill(@ModelAttribute CandidateSkill candidateSkill) {
        candidateSkillService.add(candidateSkill);
        return "redirect:/candidate-skills"; // Sau khi thêm, redirect về trang danh sách
    }

    // Cập nhật kỹ năng
    @PutMapping
    public String updateCandidateSkill(@ModelAttribute CandidateSkill candidateSkill) {
        try {
            candidateSkillService.update(candidateSkill);
            return "redirect:/candidate-skills"; // Sau khi cập nhật, redirect về trang danh sách
        } catch (EntityIdNotFoundException e) {
            return "error"; // Trả về view lỗi nếu không tìm thấy kỹ năng
        }
    }

    // Xóa kỹ năng
    @DeleteMapping("/{candidateSkillId}")
    public String deleteCandidateSkill(@PathVariable CandidateSkillId candidateSkillId) {
        try {
            candidateSkillService.delete(candidateSkillId);
            return "redirect:/candidate-skills"; // Sau khi xóa, redirect về trang danh sách
        } catch (EntityIdNotFoundException e) {
            return "error"; // Trả về view lỗi nếu không tìm thấy
        }
    }

    // Tìm kỹ năng của ứng viên theo ID ứng viên
    @GetMapping("/candidate/{candidateId}")
    public String findSkillsByCandidateId(@PathVariable Long candidateId, Model model) {
        List<CandidateSkill> skills = candidateSkillService.findByCandidateId(candidateId);
        model.addAttribute("skills", skills);
        return "candidateSkills"; // Tên view hiển thị danh sách kỹ năng của ứng viên
    }

    // Tìm kỹ năng theo trình độ
    @GetMapping("/skill-level/{skillLevel}")
    public String findSkillsBySkillLevel(@PathVariable SkillLevel skillLevel, Model model) {
        List<CandidateSkill> skills = candidateSkillService.findBySkillLevel(skillLevel);
        model.addAttribute("skills", skills);
        return "candidateSkills"; // Tên view hiển thị danh sách kỹ năng theo trình độ
    }
}
