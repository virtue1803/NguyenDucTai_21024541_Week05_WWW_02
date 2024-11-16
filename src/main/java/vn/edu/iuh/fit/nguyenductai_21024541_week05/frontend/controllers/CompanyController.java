package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Company;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models.CompanyModel;

import java.util.List;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyModel companyModel;

    @GetMapping
    public ModelAndView listAllCompanies() {
        List<Company> companies = companyModel.getAll();
        ModelAndView mav = new ModelAndView("company-list");
        mav.addObject("companies", companies);
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView getCompanyById(@PathVariable("id") Long id) {
        Company company = companyModel.getById(id);
        ModelAndView mav = new ModelAndView("company-detail");
        mav.addObject("company", company);
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView showCreateCompanyForm() {
        ModelAndView mav = new ModelAndView("company-create");
        mav.addObject("company", new Company());
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createCompany(@ModelAttribute Company company) {
        companyModel.insert(company);
        return new ModelAndView("redirect:/companies");
    }

    @GetMapping("/{id}/edit")
    public ModelAndView showEditCompanyForm(@PathVariable("id") Long id) {
        Company company = companyModel.getById(id);
        ModelAndView mav = new ModelAndView("company-edit");
        mav.addObject("company", company);
        return mav;
    }

    @PostMapping("/{id}/edit")
    public ModelAndView editCompany(@PathVariable("id") Long id, @ModelAttribute Company company) {
        companyModel.update(id, company);
        return new ModelAndView("redirect:/companies/" + id);
    }

    @PostMapping("/{id}/delete")
    public ModelAndView deleteCompany(@PathVariable("id") Long id) {
        companyModel.delete(id);
        return new ModelAndView("redirect:/companies");
    }
}
