package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.enums.InvoiceStatus;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;
import com.cydeo.javahedgehogsproject.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final InvoiceService invoiceService;

    public DashboardController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    @GetMapping("/dashboard")
    public String navigateToDashboard(Model model) throws Exception {

        model.addAttribute("invoices", invoiceService.findAllApprovedInvoice(InvoiceStatus.APPROVED));

        return "dashboard";
    }


}
