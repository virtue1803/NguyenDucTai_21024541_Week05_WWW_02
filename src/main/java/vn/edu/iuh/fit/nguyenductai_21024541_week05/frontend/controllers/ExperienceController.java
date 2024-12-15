package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Experience;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.ExperienceService;

import java.util.List;

@Controller
@RequestMapping("/experiences")
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    // Lấy tất cả các kinh nghiệm
    @GetMapping
    public String getAllExperiences(Model model) {
        List<Experience> experiences = (List<Experience>) experienceService.getAll();
        model.addAttribute("experiences", experiences);
        return "experiences"; // Tên view hiển thị danh sách kinh nghiệm, ví dụ "experiences.jsp"
    }

    // Lấy kinh nghiệm theo ID
    @GetMapping("/{id}")
    public String getExperienceById(@PathVariable Long id, Model model) {
        try {
            Experience experience = experienceService.getById(id).orElseThrow(() -> new EntityIdNotFoundException("Experience not found"));
            model.addAttribute("experience", experience);
            return "experienceDetail"; // Tên view hiển thị chi tiết kinh nghiệm
        } catch (EntityIdNotFoundException e) {
            return "error"; // Nếu không tìm thấy kinh nghiệm, hiển thị lỗi
        }
    }

    // Thêm một kinh nghiệm mới
    @PostMapping
    public String addExperience(@ModelAttribute Experience experience) {
        experienceService.add(experience);
        return "redirect:/experiences"; // Sau khi thêm, chuyển hướng đến trang danh sách kinh nghiệm
    }

    // Cập nhật thông tin kinh nghiệm
    @PutMapping
    public String updateExperience(@ModelAttribute Experience experience) {
        try {
            experienceService.update(experience);
            return "redirect:/experiences"; // Sau khi cập nhật, chuyển hướng đến trang danh sách
        } catch (EntityIdNotFoundException e) {
            return "error"; // Nếu không tìm thấy kinh nghiệm, hiển thị lỗi
        }
    }

    // Xóa kinh nghiệm theo ID
    @DeleteMapping("/{id}")
    public String deleteExperience(@PathVariable Long id) {
        try {
            experienceService.delete(id);
            return "redirect:/experiences"; // Sau khi xóa, chuyển hướng đến trang danh sách
        } catch (EntityIdNotFoundException e) {
            return "error"; // Nếu không tìm thấy kinh nghiệm, hiển thị lỗi
        }
    }

    // Tìm kiếm kinh nghiệm theo ID ứng viên
    @GetMapping("/search/candidate")
    public String searchByCandidateId(@RequestParam Long candidateId, Model model) {
        List<Experience> experiences = experienceService.findByCandidateId(candidateId);
        model.addAttribute("experiences", experiences);
        return "experiences"; // Hiển thị kết quả tìm kiếm theo ID ứng viên
    }

    // Tìm kiếm kinh nghiệm theo tên công ty
    @GetMapping("/search/company")
    public String searchByCompanyName(@RequestParam String companyName, Model model) {
        List<Experience> experiences = experienceService.findByCompanyName(companyName);
        model.addAttribute("experiences", experiences);
        return "experiences"; // Hiển thị kết quả tìm kiếm theo tên công ty
    }

    // Tìm kiếm kinh nghiệm theo vai trò công việc
    @GetMapping("/search/role")
    public String searchByRole(@RequestParam String role, Model model) {
        List<Experience> experiences = experienceService.findByRole(role);
        model.addAttribute("experiences", experiences);
        return "experiences"; // Hiển thị kết quả tìm kiếm theo vai trò công việc
    }

    // Tìm kiếm kinh nghiệm theo tên công ty và vai trò
    @GetMapping("/search/company-role")
    public String searchByCompanyAndRole(@RequestParam String companyName, @RequestParam String role, Model model) {
        List<Experience> experiences = experienceService.findByCompanyNameAndRole(companyName, role);
        model.addAttribute("experiences", experiences);
        return "experiences"; // Hiển thị kết quả tìm kiếm theo cả tên công ty và vai trò công việc
    }
}
