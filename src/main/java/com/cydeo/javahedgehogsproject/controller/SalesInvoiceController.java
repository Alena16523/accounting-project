package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.dto.InvoiceProductDto;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;
import com.cydeo.javahedgehogsproject.service.ClientVendorService;
import com.cydeo.javahedgehogsproject.service.InvoiceProductService;
import com.cydeo.javahedgehogsproject.service.InvoiceService;
import com.cydeo.javahedgehogsproject.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/salesInvoices")
public class SalesInvoiceController {

    private final InvoiceService invoiceService;
    private final ClientVendorService clientVendorService;
    private final InvoiceProductService invoiceProductService;
    private final ProductService productService;

    public SalesInvoiceController(InvoiceService invoiceService, ClientVendorService clientVendorService, InvoiceProductService invoiceProductService, ProductService productService) {
        this.invoiceService = invoiceService;
        this.clientVendorService = clientVendorService;
        this.invoiceProductService = invoiceProductService;
        this.productService = productService;
    }


    @GetMapping("/list")
    public String listAllSalesInvoice(Model model) {
        model.addAttribute("invoices", invoiceService.findAllInvoice(InvoiceType.SALES));
        return "/invoice/sales-invoice-list";
    }

    @GetMapping("/create")
    public String createSalesInvoice(Model model) {
        model.addAttribute("newSalesInvoice", invoiceService.getNewSalesInvoice(InvoiceType.SALES));
        model.addAttribute("clients", clientVendorService.findAllClients());
        return "/invoice/sales-invoice-create";
    }


    @PostMapping("/create")
    public String saveSalesInvoice(@ModelAttribute("newSalesInvoice") InvoiceDto invoiceDto) {

        InvoiceDto invoiceDtoId = invoiceService.save(invoiceDto);

        return "redirect:/salesInvoices/update/" + invoiceDtoId.getId();

    }


    @PostMapping("/addInvoiceProduct/{id}")
    public String savedInvoiceProduct(@PathVariable("id") Long id, @ModelAttribute("newInvoiceProduct") InvoiceProductDto invoiceProductDto, Model model) {

        model.addAttribute("products", productService.listAllProducts());
        invoiceProductService.saveProduct(invoiceProductDto, id);

        return "redirect:/salesInvoices/update/" + id;

    }


}











