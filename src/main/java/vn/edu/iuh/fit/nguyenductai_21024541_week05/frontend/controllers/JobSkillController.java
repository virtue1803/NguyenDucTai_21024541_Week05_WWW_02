package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillLevel;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.JobSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.JobSkill;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.JobSkillService;

import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/job-skills")
public class JobSkillController {

    @Autowired
    private JobSkillService jobSkillService;

    // Lấy tất cả kỹ năng yêu cầu cho công việc
    @GetMapping
    public String getAllJobSkills(Model model) {
        // Directly pass the Iterator as an Iterable
        Iterator<JobSkill> jobSkills = jobSkillService.getAll();
        model.addAttribute("jobSkills", jobSkills);
        return "jobSkills"; // View hiển thị danh sách kỹ năng yêu cầu
    }


    // Lấy kỹ năng yêu cầu cho công việc theo ID
    @GetMapping("/{id}")
    public String getJobSkillById(@PathVariable JobSkillId id, Model model) {
        try {
            JobSkill jobSkill = jobSkillService.getById(id).orElseThrow(() -> new EntityIdNotFoundException("JobSkill not found"));
            model.addAttribute("jobSkill", jobSkill);
            return "jobSkillDetail"; // View hiển thị chi tiết kỹ năng yêu cầu
        } catch (EntityIdNotFoundException e) {
            return "error"; // Nếu không tìm thấy kỹ năng yêu cầu, hiển thị lỗi
        }
    }

    // Thêm một kỹ năng yêu cầu cho công việc mới
    @PostMapping
    public String addJobSkill(@ModelAttribute JobSkill jobSkill) {
        jobSkillService.add(jobSkill);
        return "redirect:/job-skills"; // Sau khi thêm, chuyển hướng đến danh sách kỹ năng yêu cầu
    }

    // Cập nhật kỹ năng yêu cầu cho công việc
    @PutMapping
    public String updateJobSkill(@ModelAttribute JobSkill jobSkill) {
        try {
            jobSkillService.update(jobSkill);
            return "redirect:/job-skills"; // Sau khi cập nhật, chuyển hướng đến danh sách
        } catch (EntityIdNotFoundException e) {
            return "error"; // Nếu không tìm thấy kỹ năng yêu cầu, hiển thị lỗi
        }
    }

    // Xóa kỹ năng yêu cầu theo ID
    @DeleteMapping("/{id}")
    public String deleteJobSkill(@PathVariable JobSkillId id) {
        try {
            jobSkillService.delete(id);
            return "redirect:/job-skills"; // Sau khi xóa, chuyển hướng đến danh sách
        } catch (EntityIdNotFoundException e) {
            return "error"; // Nếu không tìm thấy kỹ năng yêu cầu, hiển thị lỗi
        }
    }

    // Tìm kiếm kỹ năng yêu cầu cho công việc theo SkillId
    @GetMapping("/search/skill")
    public String searchBySkillId(@RequestParam Long skillId, Model model) {
        List<JobSkill> jobSkills = jobSkillService.findBySkillId(skillId);
        model.addAttribute("jobSkills", jobSkills);
        return "jobSkills"; // Hiển thị kết quả tìm kiếm theo SkillId
    }

    // Tìm kiếm kỹ năng yêu cầu cho công việc theo SkillLevel
    @GetMapping("/search/skill-level")
    public String searchBySkillLevel(@RequestParam SkillLevel skillLevel, Model model) {
        List<JobSkill> jobSkills = jobSkillService.findBySkillLevel(skillLevel);
        model.addAttribute("jobSkills", jobSkills);
        return "jobSkills"; // Hiển thị kết quả tìm kiếm theo SkillLevel
    }

    // Tìm kiếm kỹ năng yêu cầu cho công việc theo JobId
    @GetMapping("/search/job")
    public String searchByJobId(@RequestParam Long jobId, Model model) {
        List<JobSkill> jobSkills = jobSkillService.findByJobId(jobId);
        model.addAttribute("jobSkills", jobSkills);
        return "jobSkills"; // Hiển thị kết quả tìm kiếm theo JobId
    }
}
