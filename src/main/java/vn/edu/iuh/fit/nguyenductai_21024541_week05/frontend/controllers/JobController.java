package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Job;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.JobService;

import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    // Lấy tất cả các công việc
    // Giữ Iterator
    @GetMapping
    public String getAllJobs(Model model) {
        Iterator<Job> jobs = jobService.getAll(); // Đây vẫn sẽ là Iterator
        model.addAttribute("jobs", jobs); // Truyền Iterator vào model
        return "jobs"; // Tên view hiển thị danh sách công việc
    }


    // Lấy công việc theo ID
    @GetMapping("/{id}")
    public String getJobById(@PathVariable Long id, Model model) {
        try {
            Job job = jobService.getById(id).orElseThrow(() -> new EntityIdNotFoundException("Job not found"));
            model.addAttribute("job", job);
            return "jobDetail"; // Tên view hiển thị chi tiết công việc
        } catch (EntityIdNotFoundException e) {
            return "error"; // Nếu không tìm thấy công việc, hiển thị lỗi
        }
    }

    // Thêm một công việc mới
    @PostMapping
    public String addJob(@ModelAttribute Job job) {
        jobService.add(job);
        return "redirect:/jobs"; // Sau khi thêm, chuyển hướng đến trang danh sách công việc
    }

    // Cập nhật thông tin công việc
    @PutMapping
    public String updateJob(@ModelAttribute Job job) {
        try {
            jobService.update(job);
            return "redirect:/jobs"; // Sau khi cập nhật, chuyển hướng đến trang danh sách
        } catch (EntityIdNotFoundException e) {
            return "error"; // Nếu không tìm thấy công việc, hiển thị lỗi
        }
    }

    // Xóa công việc theo ID
    @DeleteMapping("/{id}")
    public String deleteJob(@PathVariable Long id) {
        try {
            jobService.delete(id);
            return "redirect:/jobs"; // Sau khi xóa, chuyển hướng đến trang danh sách
        } catch (EntityIdNotFoundException e) {
            return "error"; // Nếu không tìm thấy công việc, hiển thị lỗi
        }
    }

    // Tìm kiếm công việc theo tên công việc
    @GetMapping("/search/jobname")
    public String searchByJobName(@RequestParam String jobName, Model model) {
        List<Job> jobs = jobService.findByJobName(jobName);
        model.addAttribute("jobs", jobs);
        return "jobs"; // Hiển thị kết quả tìm kiếm theo tên công việc
    }

    // Tìm kiếm công việc theo tên công ty
    @GetMapping("/search/company")
    public String searchByCompanyName(@RequestParam String companyName, Model model) {
        List<Job> jobs = jobService.findByCompanyName(companyName);
        model.addAttribute("jobs", jobs);
        return "jobs"; // Hiển thị kết quả tìm kiếm theo tên công ty
    }

    // Tìm kiếm công việc theo mô tả công việc
    @GetMapping("/search/jobdesc")
    public String searchByJobDesc(@RequestParam String jobDesc, Model model) {
        List<Job> jobs = jobService.findByJobDesc(jobDesc);
        model.addAttribute("jobs", jobs);
        return "jobs"; // Hiển thị kết quả tìm kiếm theo mô tả công việc
    }
}
