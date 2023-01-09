package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.dto.InvoiceProductDto;
import com.cydeo.javahedgehogsproject.entity.Invoice;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.InvoiceRepository;
import com.cydeo.javahedgehogsproject.service.ClientVendorService;
import com.cydeo.javahedgehogsproject.service.InvoiceProductService;
import com.cydeo.javahedgehogsproject.service.InvoiceService;
import com.cydeo.javahedgehogsproject.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchaseInvoiceController {


    private final InvoiceService invoiceService;
    private final ClientVendorService clientVendorService;
    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;
    private final ProductService productService;
    private final InvoiceProductService invoiceProductService;

    public PurchaseInvoiceController(InvoiceService invoiceService, ClientVendorService clientVendorService, InvoiceRepository invoiceRepository, MapperUtil mapperUtil, ProductService productService, InvoiceProductService invoiceProductService) {
        this.invoiceService = invoiceService;
        this.clientVendorService = clientVendorService;
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
        this.productService = productService;
        this.invoiceProductService = invoiceProductService;
    }

    @GetMapping("/list")
    public String listPurchaseInvoices(Model model) {

        model.addAttribute("invoices", invoiceService.findAllInvoice(InvoiceType.PURCHASE));

        return "invoice/purchase-invoice-list";
    }


    @GetMapping("/create")
    public String createInvoicePurchase(Model model) {

        model.addAttribute("newPurchaseInvoice", invoiceService.getNewInvoice(InvoiceType.PURCHASE));
        model.addAttribute("vendors", clientVendorService.findAllVendors());
        return "/invoice/purchase-invoice-create";
    }

    @PostMapping("/create")
    public String createNewPurchaseInvoice(@ModelAttribute("newPurchaseInvoice") InvoiceDto newPurchaseInvoice) {

        invoiceService.savePurchaseInvoice(newPurchaseInvoice);

        return "redirect:/purchaseInvoices/update/"+newPurchaseInvoice.getId();
    }

    @GetMapping("/update/{invoiceId}")
    public String editInvoice(@PathVariable("invoiceId") Long id, Model model) {

        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("vendors", clientVendorService.findAllVendors());

        model.addAttribute("newInvoiceProduct", new InvoiceProductDto());
        model.addAttribute("products", productService.listAllProducts());

        model.addAttribute("invoiceProducts", invoiceProductService.findAllById(id));

        return "invoice/purchase-invoice-update";
    }

    @PostMapping("/update/{id}")
    public String updateInvoice(@ModelAttribute("invoice") InvoiceDto invoice, Model model) {
        model.addAttribute("vendors", clientVendorService.findAllVendors());
        invoiceService.update(invoice);
        return "redirect:/purchaseInvoices/list";
    }

    @PostMapping("/addInvoiceProduct/{invoiceId}")
    public String createInvoiceProduct(@PathVariable("invoiceId") Long invoiceId, @ModelAttribute("newInvoiceProduct") InvoiceProductDto invoiceProduct, Model model) {
        model.addAttribute("products", productService.listAllProducts());
        invoiceProductService.saveByInvoiceId(invoiceProduct, invoiceId);
        return "redirect:/purchaseInvoices/update/" + invoiceId;
    }

    @GetMapping("/removeInvoiceProduct/{invoiceId}/{productId}")
    public String removeProduct(@PathVariable Long productId, @PathVariable Long invoiceId) {
        invoiceProductService.delete(productId);
        return "redirect:/purchaseInvoices/update/" + invoiceId;
    }

    @GetMapping("/approve/{id}")
    public String approvePurchaseInvoice(@PathVariable("id") Long purchaseInvoiceId){

       invoiceService.approvePurchaseInvoice(purchaseInvoiceId);

       return "redirect:/purchaseInvoices/list";
    }


}
