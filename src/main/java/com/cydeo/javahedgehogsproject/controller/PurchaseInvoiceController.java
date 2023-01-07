package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.dto.ClientVendorDto;
import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.entity.Invoice;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.InvoiceRepository;
import com.cydeo.javahedgehogsproject.service.ClientVendorService;
import com.cydeo.javahedgehogsproject.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchaseInvoiceController {

    private final InvoiceService invoiceService;
    private final ClientVendorService clientVendorService;
    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;

    public PurchaseInvoiceController(InvoiceService invoiceService, ClientVendorService clientVendorService,
                                     InvoiceRepository invoiceRepository, MapperUtil mapperUtil) {
            this.invoiceService = invoiceService;
            this.clientVendorService = clientVendorService;
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
    }


    @GetMapping("/create")
    public String createInvoicePurchase(Model model) {

        model.addAttribute("newPurchaseInvoice", invoiceService.getNewInvoice(InvoiceType.PURCHASE));
        model.addAttribute("vendors", clientVendorService.findAllVendors().stream()
                .sorted(Comparator.comparing(ClientVendorDto::getClientVendorName))
                .collect(Collectors.toList()));
        return "/invoice/purchase-invoice-create";
    }

    @PostMapping("/create")
    public String createNewPurchaseInvoice(@ModelAttribute("newPurchaseInvoice") InvoiceDto newPurchaseInvoice) {
        Invoice purchaseInvoice=mapperUtil.convert(newPurchaseInvoice, new Invoice());

        //do we need to set nay fields before saving invoice?

            invoiceRepository.save(purchaseInvoice);
        return "redirect:/purchaseInvoices/update/";
    }

}
