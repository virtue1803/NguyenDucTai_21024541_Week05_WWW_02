package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Company;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.CompanyService;

import java.util.List;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    // Lấy tất cả các công ty
    @GetMapping
    public String getAllCompanies(Model model) {
        List<Company> companies = (List<Company>) companyService.getAll();
        model.addAttribute("companies", companies);
        return "companies"; // Tên view hiển thị danh sách công ty, ví dụ "companies.jsp"
    }

    // Lấy công ty theo ID và hiển thị
    @GetMapping("/{id}")
    public String getCompanyById(@PathVariable Long id, Model model) {
        try {
            Company company = companyService.getById(id).orElseThrow(() -> new EntityIdNotFoundException("Company not found"));
            model.addAttribute("company", company);
            return "companyDetail"; // Tên view chi tiết công ty, ví dụ "companyDetail.jsp"
        } catch (EntityIdNotFoundException e) {
            return "error"; // Trả về view lỗi nếu không tìm thấy
        }
    }

    // Thêm công ty mới
    @PostMapping
    public String addCompany(@ModelAttribute Company company) {
        companyService.add(company);
        return "redirect:/companies"; // Sau khi thêm, redirect về trang danh sách công ty
    }

    // Cập nhật thông tin công ty
    @PutMapping
    public String updateCompany(@ModelAttribute Company company) {
        try {
            companyService.update(company);
            return "redirect:/companies"; // Sau khi cập nhật, redirect về trang danh sách công ty
        } catch (EntityIdNotFoundException e) {
            return "error"; // Trả về view lỗi nếu không tìm thấy công ty
        }
    }

    // Xóa công ty theo ID
    @DeleteMapping("/{id}")
    public String deleteCompany(@PathVariable Long id) {
        try {
            companyService.delete(id);
            return "redirect:/companies"; // Sau khi xóa, redirect về trang danh sách công ty
        } catch (EntityIdNotFoundException e) {
            return "error"; // Trả về view lỗi nếu không tìm thấy công ty
        }
    }

    // Tìm kiếm công ty theo tên
    @GetMapping("/search")
    public String searchCompanyByName(@RequestParam String name, Model model) {
        List<Company> companies = companyService.findByCompName(name);
        model.addAttribute("companies", companies);
        return "companies"; // Tên view hiển thị danh sách công ty theo tên
    }

    // Tìm kiếm công ty theo email
    @GetMapping("/search/email")
    public String searchCompanyByEmail(@RequestParam String email, Model model) {
        Company company = companyService.findByEmail(email).orElse(null);
        model.addAttribute("company", company);
        return company != null ? "companyDetail" : "error"; // Trả về chi tiết công ty hoặc lỗi nếu không tìm thấy
    }
}
