package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.enums.InvoiceType;
import com.cydeo.javahedgehogsproject.service.InvoiceProductService;
import com.cydeo.javahedgehogsproject.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/salesInvoices")
public class SalesInvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceProductService invoiceProductService;
    public SalesInvoiceController(InvoiceService invoiceService, InvoiceProductService invoiceProductService) {
        this.invoiceService = invoiceService;
        this.invoiceProductService = invoiceProductService;
    }

    @GetMapping("/list")
    public String listAllSalesInvoices(Model model){
      model.addAttribute("invoices",invoiceService.findAllInvoice(InvoiceType.SALES));
        return "/invoice/sales-invoice-list";
    }

//    @GetMapping("/list")
//    public String listAllSalesInvoices(Model model){
//      model.addAttribute("invoices",invoiceService.findAllInvoicesByInvoiceType(InvoiceType.SALE));
//        return "/invoice/sales-invoice-list";
//    }

    @GetMapping("/delete/{id}")
    public String deleteSalesInvoices(@PathVariable("id")Long id) {


        invoiceService.delete(id);
        return "redirect:/salesInvoices/list";

    }
}
