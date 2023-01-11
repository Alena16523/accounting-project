package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.dto.InvoiceProductDto;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;
import com.cydeo.javahedgehogsproject.service.ClientVendorService;
import com.cydeo.javahedgehogsproject.service.InvoiceService;
import com.cydeo.javahedgehogsproject.service.InvoiceProductService;
import com.cydeo.javahedgehogsproject.service.ProductService;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

@Controller
@RequestMapping("/salesInvoices")
public class SalesInvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceProductService invoiceProductService;
    private final ProductService productService;
    private final ClientVendorService clientVendorService;

    public SalesInvoiceController(InvoiceService invoiceService, InvoiceProductService invoiceProductService, ProductService productService, ClientVendorService clientVendorService) {
        this.invoiceService = invoiceService;
        this.invoiceProductService = invoiceProductService;
        this.productService = productService;
        this.clientVendorService = clientVendorService;
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
    public String saveSalesInvoice(@Valid @ModelAttribute("newSalesInvoice") InvoiceDto invoiceDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("clients", clientVendorService.findAllClients());
            return "/invoice/sales-invoice-create";
        }
        InvoiceDto invoiceDtoId = invoiceService.save(invoiceDto);

        return "redirect:/salesInvoices/update/" + invoiceDtoId.getId();
    }

    @PostMapping("/addInvoiceProduct/{id}")
    public String savedInvoiceProduct(@PathVariable("id") Long id, @Valid @ModelAttribute("newInvoiceProduct") InvoiceProductDto invoiceProductDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("invoice", invoiceService.findById(id));
            model.addAttribute("clients", clientVendorService.findAllClients());
            model.addAttribute("products", productService.listAllProducts());
            model.addAttribute("invoiceProducts", invoiceProductService.findAllById(id));

            return "/invoice/sales-invoice-update";
        }
        invoiceProductService.saveProduct(invoiceProductDto, id);

        return "redirect:/salesInvoices/update/" + id;
    }

    @GetMapping("/removeInvoiceProduct/{invoiceId}/{invoiceProductId}")
    public String removeInvoiceProductFromProductList(@PathVariable("invoiceId") Long invoiceId, @PathVariable("invoiceProductId") Long invoiceProductId) {
        invoiceProductService.deleteSalesInvoiceProduct(invoiceProductId);

        return "redirect:/salesInvoices/update/" + invoiceId;
    }

    @GetMapping("/update/{id}")
    public String editSalesInvoice(@PathVariable("id") Long id, Model model) {

        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("clients", clientVendorService.findAllClients());
        model.addAttribute("newInvoiceProduct", new InvoiceProductDto());
        model.addAttribute("products", productService.findAll());
        model.addAttribute("invoiceProducts", invoiceProductService.findAllInvoiceProducts(id));


        return "invoice/sales-invoice-update";

    }
    @PostMapping("/update/{id}")
    public String updateSalesInvoice(@ModelAttribute("invoice")InvoiceDto invoiceDto){

        invoiceService.update(invoiceDto);
        return "redirect:/salesInvoices/list";
    }


    @GetMapping("/approve/{id}")
    public String approveSalesInvoice(@PathVariable("id") Long invoiceId) {
        invoiceService.approveSalesInvoice(invoiceId);
        return "redirect:/salesInvoices/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteSalesInvoices(@PathVariable("id") Long id) {
        invoiceService.delete(id);
        return "redirect:/salesInvoices/list";
    }

}











