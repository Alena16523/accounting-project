package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;

import java.math.BigDecimal;

public interface InvoiceProductService {
    BigDecimal totalPriceWithoutTax(String invoiceNo);

    void deleteByInvoice(InvoiceType invoiceType, InvoiceDto invoiceDto);
}
