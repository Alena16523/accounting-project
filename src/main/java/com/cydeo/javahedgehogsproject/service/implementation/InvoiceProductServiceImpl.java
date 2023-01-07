package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.entity.InvoiceProduct;
import com.cydeo.javahedgehogsproject.repository.InvoiceProductRepository;
import com.cydeo.javahedgehogsproject.service.InvoiceProductService;
import com.cydeo.javahedgehogsproject.service.SecurityService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository) {
        this.invoiceProductRepository = invoiceProductRepository;
    }

    @Override
    public BigDecimal totalTax(Long invoiceId) {
        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findAllByInvoiceId(invoiceId);//from entity

        BigDecimal totalTax = BigDecimal.valueOf(0);
        for (InvoiceProduct each : invoiceProductList) {
            totalTax = totalTax.add(BigDecimal.valueOf(each.getQuantity()).multiply(each.getTax()).divide(BigDecimal.valueOf(100)).multiply(each.getPrice()));
        }
        return totalTax.setScale(2, RoundingMode.CEILING);
    }

    @Override
    public BigDecimal totalPriceWithoutTax(Long invoiceId) {
        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findAllByInvoiceId(invoiceId);//from entity

        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for (InvoiceProduct each : invoiceProductList) {
            totalPrice = totalPrice.add(each.getPrice().multiply(BigDecimal.valueOf(each.getQuantity())));
        }
        return totalPrice.setScale(2, RoundingMode.CEILING);
    }

}
