package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.enums.CompanyStatus;
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

}
