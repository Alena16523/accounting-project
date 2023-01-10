package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.client.CurrencyClient;
import com.cydeo.javahedgehogsproject.service.DashboardService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {
    private final CurrencyClient currencyClient;
    private final DashboardService dashboardService;

    public DashboardController(CurrencyClient currencyClient, DashboardService dashboardService) {
        this.currencyClient = currencyClient;
        this.dashboardService = dashboardService;
    }


    @GetMapping("/dashboard")
    public String navigateToDashboard(Model model) throws Exception {
        model.addAttribute("currency", dashboardService.getCurrency());
        return "dashboard";
    }

}
