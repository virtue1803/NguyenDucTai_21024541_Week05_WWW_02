package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Company;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models.CompanyModel;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyModel companyModel;

    @Autowired
    public CompanyController(CompanyModel companyModel) {
        this.companyModel = companyModel;
    }

    // Endpoint to add a single Company
    @PostMapping("/insert")
    public String insertCompany(@ModelAttribute Company company, Model model) {
        Response response = companyModel.insert(company);
        model.addAttribute("response", response);
        return "company_response"; // View to display the result
    }

    // Endpoint to add multiple Companies
    @PostMapping("/insertAll")
    public String insertAllCompanies(@ModelAttribute List<Company> companies, Model model) {
        Response response = companyModel.insertAll(companies);
        model.addAttribute("response", response);
        return "company_response"; // View to display the result
    }

    // Endpoint to update a Company
    @PutMapping("/update")
    public String updateCompany(@RequestParam Long id, @ModelAttribute Company company, Model model) {
        Response response = companyModel.update(id, company);
        model.addAttribute("response", response);
        return "company_response"; // View to display the result
    }

    // Endpoint to delete a Company
    @DeleteMapping("/delete")
    public String deleteCompany(@RequestParam Long id, Model model) {
        companyModel.delete(id);
        model.addAttribute("message", "Company deleted successfully");
        return "company_response"; // View to display the result
    }

    // Endpoint to get Company by ID
    @GetMapping("/getById")
    public String getCompanyById(@RequestParam Long id, Model model) {
        Optional<Company> company = companyModel.getById(id);
        model.addAttribute("company", company.orElse(null));
        return "company_details"; // View to display company details
    }

    // Endpoint to get all Companies
    @GetMapping("/getAll")
    public String getAllCompanies(Model model) {
        List<Company> companies = companyModel.getAll();
        model.addAttribute("companies", companies);
        return "companies_list"; // View to display all companies
    }

    // Endpoint to find Company by name
    @GetMapping("/findByName")
    public String findCompanyByName(@RequestParam String name, Model model) {
        List<Company> companies = companyModel.findByCompName(name);
        model.addAttribute("companies", companies);
        return "companies_list"; // View to display companies by name
    }

    // Endpoint to find Company by email
    @GetMapping("/findByEmail")
    public String findCompanyByEmail(@RequestParam String email, Model model) {
        Optional<Company> company = companyModel.findByEmail(email);
        model.addAttribute("company", company.orElse(null));
        return "company_details"; // View to display company by email
    }
}
