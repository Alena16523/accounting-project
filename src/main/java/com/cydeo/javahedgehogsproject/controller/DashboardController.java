package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.enums.InvoiceStatus;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;
import com.cydeo.javahedgehogsproject.service.InvoiceService;
import org.springframework.stereotype.Controller;
import com.cydeo.javahedgehogsproject.client.CurrencyClient;
import com.cydeo.javahedgehogsproject.service.DashboardService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {

        this.dashboardService = dashboardService;
    }


    private final InvoiceService invoiceService;

    public DashboardController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    @GetMapping("/dashboard")
    public String navigateToDashboard(Model model) throws Exception {

        model.addAttribute("invoices", invoiceService.findAllApprovedInvoice(InvoiceStatus.APPROVED));

        model.addAttribute("currency", dashboardService.getCurrency());
        return "dashboard";
    }

}
