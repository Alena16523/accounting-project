package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;
import com.cydeo.javahedgehogsproject.service.ClientVendorService;
import com.cydeo.javahedgehogsproject.service.InvoiceService;
import com.cydeo.javahedgehogsproject.dto.InvoiceProductDto;
import com.cydeo.javahedgehogsproject.service.InvoiceProductService;
import com.cydeo.javahedgehogsproject.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/salesInvoices")
public class SalesInvoiceController {

    private InvoiceService invoiceService;
    private InvoiceProductService invoiceProductService;
    private ProductService productService;
    private ClientVendorService clientVendorService;

    public SalesInvoiceController(InvoiceService invoiceService, InvoiceProductService invoiceProductService, ProductService productService, ClientVendorService clientVendorService) {
        this.invoiceService = invoiceService;
        this.invoiceProductService = invoiceProductService;
        this.productService = productService;
        this.clientVendorService = clientVendorService;
    }

    @GetMapping("/list")
    public String listAllSalesInvoice(Model model){
       model.addAttribute("invoices",invoiceService.findAllInvoice(InvoiceType.SALES));
        return "/invoice/sales-invoice-list";
    }

    @GetMapping("/create")
    public String createSalesInvoice(Model model){
        model.addAttribute("newSalesInvoice",new InvoiceDto());
        model.addAttribute("clients",clientVendorService.findAllClients());
        return "/invoice/sales-invoice-create";
    }


    @GetMapping("/update/{id}")
    public String editSalesInvoice(@PathVariable Long id, Model model) throws Exception {

        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("newInvoiceProduct", new InvoiceProductDto());
        model.addAttribute("invoiceProducts", invoiceProductService.findAllByInvoice(id));
        model.addAttribute("products", productService.findAll());
        model.addAttribute("clients", clientVendorService.findAllClients());

        return "invoice/sales-invoice-update";
    }
}
