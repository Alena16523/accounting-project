package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.dto.CompanyDto;
import com.cydeo.javahedgehogsproject.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String getCompanyList(Model model) {
        model.addAttribute("companies", companyService.findAll());
        return "/company/company-list";
    }

    @GetMapping("/create")
    public String createCompany(Model model) {
        model.addAttribute("newCompany", new CompanyDto());
        return "/company/company-create";
    }

    @PostMapping("/create")
    public String insertCompany(@ModelAttribute("newCompany") CompanyDto company) {
        companyService.create(company);
        return "redirect:/companies/list";
    }

    @GetMapping("/update/{id}")
    public String updateCompany(@PathVariable("id") Long id, Model model) {
        model.addAttribute("company", companyService.findById(id));
        return "/company/company-update";
    }

    @PostMapping("/update/{id}")
    public String editCompany(@ModelAttribute("company") CompanyDto company) {
        companyService.update(company);
        return "redirect:/companies/list";
    }

    @GetMapping("/activate/{id}")
    public String activateCompany(@PathVariable("id") Long id) {
        companyService.activate(id);
        return "redirect:/companies/list";
    }

    @GetMapping("/deactivate/{id}")
    public String deactivateCompany(@PathVariable("id") Long id) {
        companyService.deactivate(id);
        return "redirect:/companies/list";
    }

}
